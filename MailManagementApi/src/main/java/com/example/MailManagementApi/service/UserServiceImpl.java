package com.example.MailManagementApi.service;

import com.example.MailManagementApi.config.JwtService;
import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.helper_classes.UserRequest;
import com.example.MailManagementApi.helper_classes.UserResponse;
import com.example.MailManagementApi.model.*;
import com.example.MailManagementApi.repository.UserRepository;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService tokenUtil;

    @Autowired
    EmailService emailService;


    @Override
    public void createUser(UserRequest userRequest) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        Date beginDate=null;
        if (userRequest.getBeginDate()!=null && !userRequest.getBeginDate().isEmpty())
            beginDate=simpleDateFormat.parse(userRequest.getBeginDate());
        Date endDate=null;
        if (userRequest.getEndDate()!=null && !userRequest.getEndDate().isEmpty())
            endDate=simpleDateFormat.parse(userRequest.getEndDate());

        String password=generatePassword();
        boolean notification= !userRequest.getRole().equals("admin");

        User user=new User(userRequest.getId(),userRequest.getStructure(),userRequest.getName(),userRequest.getFirstName()
        ,userRequest.getFun(),userRequest.getEmail(),userRequest.getRole(),beginDate,endDate,"change password"
        ,passwordEncoder.encode(password),userRequest.getJob(),null,null,notification);

        userRepository.save(user);
        emailService.sendEmail(user.getEmail(),"Mot de passe",password);

    }



    private String generatePassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return RandomStringUtils.random(30, characters );
    }


    @Override
    public List<UserResponse> userInfo() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        List<Tuple> resultList= userRepository.getUsers();
        List<UserResponse> list=new ArrayList<>();
        for (Tuple row : resultList) {
            Long id = (Long) row.get("id");
            Long structure_id = (Long) row.get("structure_id");
            String structureDesignation=(String) row.get("designation_struct");
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
            String role = (String) row.get("role");
            String job = (String) row.get("job");
            String path=(String) row.get("path");
            String structureCode=(String) row.get("code_struct");
            String firebaseToken=(String) row.get("firebase_token");
            boolean notification=(boolean) row.get("notification");
            byte[] bytes=null;
            if (path!=null && !path.isEmpty())
                bytes=getPhoto(path);

            list.add(new UserResponse(id,structure_id,structureDesignation,structureCode,name,firstName,fun,email,beginDate,endDate,status,
                    role,job,firebaseToken,notification,bytes));
        }
        return list;
    }

    @Override
    public UserResponse getUserById(long id) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        Tuple row= userRepository.getUserById(id);
        Long ids = (Long) row.get("id");
        Long structure_id = (Long) row.get("structure_id");
        String structureDesignation=(String) row.get("designation_struct");
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
        String role = (String) row.get("role");
        String job = (String) row.get("job");
        String path=(String) row.get("path");
        String structureCode=(String) row.get("code_struct");
        String firebaseToken=(String) row.get("firebase_token");
        boolean notification=(boolean) row.get("notification");
        byte[] bytes=null;
        if (path!=null && !path.isEmpty())
            bytes=getPhoto(path);
        return new UserResponse(ids,structure_id,structureDesignation,structureCode,name,firstName,fun,email,beginDate,endDate,status,
                role,job,firebaseToken,notification,bytes);
    }

    @Override
    public void updateUserPhoto(long id, MultipartFile multipartFile) {

        userRepository.findById(id)
                .map(u->{
                    u.setPath(createPhoto(multipartFile));
                    return userRepository.save(u);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }

    @Override
    public void updateToken(long id,String token) {
        // we received the token inside quotes
        String token1=token.replace("\"","");
        userRepository.findById(id)
                .map(u->{
                    u.setFirebaseToken(token1);
                    return userRepository.save(u);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));

    }

    @Override
    public List<String> getTokens(String struct) {
        List<Tuple> list=userRepository.getTokens(struct);
        List<String> tokens=new ArrayList<>();
        for (Tuple row : list)
            tokens.add((String) row.get("firebase_token"));
        return tokens;
    }

    @Override
    public void generatePassword(long id) {
        String password=generatePassword();
        userRepository.findById(id)
                .map(u->{
                    u.setPassword(passwordEncoder.encode(password));
                    u.setStatus("change password");
                    emailService.sendEmail(u.getEmail(),"Mot de passe",password);
                    return userRepository.save(u);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }

    @Override
    public void updatePassword(long id, User user) {
        userRepository.findById(id)
                .map(u->{
                    u.setStatus("active");
                    u.setPassword(passwordEncoder.encode(user.getPassword()));
                    return userRepository.save(u);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }

    @Override
    public void updateNotification(long id) {
        userRepository.findById(id)
                .map(u->{
                    u.setNotification(!u.isNotification());
                    return userRepository.save(u);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }

    @Override
    public void updateUserInfo(long id,UserRequest user) {
        userRepository.findById(id)
                .map(u->{
                    u.setName(user.getName());
                    u.setFirstName(user.getFirstName());
                    u.setStructure(user.getStructure());
                    u.setFun(user.getFun());
                    if (user.getEndDate()!=null && !user.getEndDate().isEmpty()){
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                        try {
                            u.setEndDate(simpleDateFormat.parse(user.getEndDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    return userRepository.save(u);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }


    public static byte[] getPhoto(String p){
        if (p!=null)
            try {
                File file = new File(p);
                Path path = file.toPath();
                return Files.readAllBytes(path);
            } catch (IOException e) {
                // handle exception
            }
        return null;

    }

    String createPhoto(MultipartFile file){
        String path="";
        try {
            if (file.getOriginalFilename()!=null) {
                // Save the file to disk
                File newFile = new File(file.getOriginalFilename());
                FileOutputStream fos = new FileOutputStream(newFile);
                fos.write(file.getBytes());
                fos.close();
                path=newFile.getPath();
            }

        } catch(IOException e){
            e.printStackTrace();
        }
        return path;
    }


}

