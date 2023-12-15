package com.example.MailManagementApi.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final  static String TOKEN_SECRET="73357638792F423F4528482B4D6251655368566D597133743677397A24432646";

    // without extraClaims
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    // with extraClaims
    public String generateToken(Map<String,Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSingInKey(),SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUserEmail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email= extractUserEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date exp= extractClaim(token,Claims::getExpiration);
        return exp.before(new Date());
    }

    //extract claims

    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction){
        final Claims claims=extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // the signIn key used to ensure that the message wasn't changed along the way
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSingInKey() {
        byte[] bytes= Decoders.BASE64.decode(TOKEN_SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }
}
