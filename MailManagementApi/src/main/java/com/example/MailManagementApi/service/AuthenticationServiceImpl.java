package com.example.MailManagementApi.service;

import com.example.MailManagementApi.config.JwtService;
import com.example.MailManagementApi.helper_classes.AuthRequest;
import com.example.MailManagementApi.helper_classes.JwtResponse;
import com.example.MailManagementApi.helper_classes.UserRequest;
import com.example.MailManagementApi.helper_classes.UserResponse;
import com.example.MailManagementApi.model.*;
import com.example.MailManagementApi.repository.TokenRepository;
import com.example.MailManagementApi.repository.UserRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.MailManagementApi.service.UserServiceImpl.getPhoto;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService tokenUtil;

    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse createUser(UserRequest userRequest) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.getDefault());
        Date beginDate=null;
        if (userRequest.getBeginDate()!=null && !userRequest.getBeginDate().isEmpty())
            beginDate=simpleDateFormat.parse(userRequest.getBeginDate());
        Date endDate=null;
        if (userRequest.getEndDate()!=null && !userRequest.getEndDate().isEmpty())
            endDate=simpleDateFormat.parse(userRequest.getEndDate());
        var user1=User.builder()
                .name(userRequest.getName())
                .firstName(userRequest.getFirstName())
                .structure(userRequest.getStructure())
                .beginDate(beginDate)
                .endDate(endDate)
                .fun(userRequest.getFun())
                .job(userRequest.getJob())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .build();
        userRepository.save(user1);
        var jwtToken= tokenUtil.generateToken(user1);
        saveToken(user1, jwtToken);
        return JwtResponse.builder()
                .token(jwtToken)
                .build();

    }


    @Override
    public JwtResponse authenticateUser(AuthRequest signIn) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signIn.getEmail()
                        ,signIn.getPassword()
                )
        );
        var user=userRepository.findByEmail(signIn.getEmail())
                .orElseThrow();

        var jwtToken= tokenUtil.generateToken(user);

        revokeAllUserTokens(user);

        saveToken(user,jwtToken);

        Tuple row=userRepository.findByEmail_(signIn.getEmail());
        UserResponse userResponse;
        if (row!=null) {
            long id = (Long) row.get("id");
            long structure_id = (Long) row.get("structure_id");
            String name = (String) row.get("name");
            String firstName = (String) row.get("first_name");
            String fun = (String) row.get("fun");
            String email = (String) row.get("email");
            Date beginDate1 = (Date) row.get("begin_date");
            String beginDate="";
            if (beginDate1!=null)
                beginDate=simpleDateFormat.format(beginDate1);
            Date endDate1 = (Date) row.get("end_date");
            String endDate="";
            if (endDate1!=null)
                endDate=simpleDateFormat.format(endDate1);
            String status = (String) row.get("status");
            String firebaseToken = (String) row.get("firebase_token");
            String role = (String) row.get("role");
            String job = (String) row.get("job");
            String path=(String) row.get("path");
            String structureCode=(String) row.get("code_struct");
            String structureDesignation=(String) row.get("designation_struct");
            boolean notification=(boolean) row.get("notification");
            byte[] bytes=null;
            if (path!=null && !path.isEmpty())
                bytes=getPhoto(path);

            userResponse=new UserResponse(id,structure_id,structureDesignation,structureCode,name,firstName,fun,email,beginDate,endDate,status,
                    role,job,firebaseToken,notification,bytes);
        }else{
            // the first admin before create structures
            userResponse=new UserResponse(user.getId(),1,"Direction génerale","DG",user.getName(),user.getFirstName(),user.getFun(),user.getEmail(),
                    "","", user.getStatus(),user.getRole(),"admin",null,true,null);
        }

        return JwtResponse.builder()
                .token(jwtToken)
                .userResponse(userResponse)
                .build();
    }

    private void revokeAllUserTokens(User user){
        var validTokens=tokenRepository.findAllValidTokensByUser(user.getId());
        if (validTokens.isEmpty())
            return;
        validTokens.forEach(t ->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    private void saveToken(User user1, String jwtToken) {
        var token=Token.builder()
                .user(user1)
                .token(jwtToken)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    @Scheduled(cron = "0 30 0 * * *")
    public void verifyEligibility(){
        userRepository.verifyEligibility();
    }
}
