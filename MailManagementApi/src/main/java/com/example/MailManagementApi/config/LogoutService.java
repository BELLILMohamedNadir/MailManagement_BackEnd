package com.example.MailManagementApi.config;

import com.example.MailManagementApi.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String header=request.getHeader("Authorization");
        //check jwt token
        if (header==null || !header.startsWith("Bearer ")){
            return;
        }

        String token=header.substring(7);
        var storedToken=tokenRepository.findByToken(token)
                .orElse(null);
        if (storedToken!=null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }


    }
}
