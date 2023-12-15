package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    String Q_VALID=" SELECT t.* FROM Token t INNER JOIN USER u ON t.user_id=u.id WHERE u.id=:user_id " +
            "   AND (t.expired=false OR t.revoked=false) ";

    @Query(value = Q_VALID,nativeQuery = true)
    List<Token> findAllValidTokensByUser(@Param("user_id") long user_id);

    Optional<Token> findByToken(String token);
}
