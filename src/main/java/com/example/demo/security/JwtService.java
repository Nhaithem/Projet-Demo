package com.example.demo.security;


import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;



@Service
public class JwtService {

    //seceret key for now it's here
    private static final String SECRET_KEY ="8C925B61623B0E5F53248E871A4B36B28C0FE5153530139E6E8AEE6A29F1960F";

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public String extractId(String token) {
        return extractClaim(token,Claims::getSubject);
    }


    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setClaims(null)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    public String generateTokenUsingId(User user){
        return Jwts.builder()
                .setClaims(null)
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    ///validate token
    public  boolean isTokenValid(String token ,UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) &&  !isTokenExpired(token));
    }


    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date()) ;
    }


    private Claims extractAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
