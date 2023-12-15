package com.example.MailManagementApi.config;

import com.example.MailManagementApi.model.Token;
import com.example.MailManagementApi.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userService;
    @Autowired
    TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String header=request.getHeader("Authorization");
        //check jwt token
        if (header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=header.substring(7);
        //extract the user email
        String email= jwtService.extractUserEmail(token);

        // SecurityContextHolder.getContext().getAuthentication()  to verify if the user is authenticated or no
        // if null means the first time
        if (email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            //get user from the dataBase
            UserDetails userDetails = userService.loadUserByUsername(email);
            // check if the token is valid
            var isTokenValid=tokenRepository.findByToken(token)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);


            // update the security context
            if (jwtService.isTokenValid(token,userDetails) && isTokenValid){
                //update the security context
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                // set the jwt token as expired and revoked because maybe it's just expired or jus revoked
                Optional<Token> token1=tokenRepository.findByToken(token);
                if (token1.isPresent()){
                    token1.get().setExpired(true);
                    token1.get().setRevoked(true);
                    tokenRepository.save(token1.get());
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}
