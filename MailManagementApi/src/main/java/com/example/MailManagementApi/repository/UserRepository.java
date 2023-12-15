package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.User;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    String Q="SELECT u.id,u.begin_date,u.email,u.end_date,u.first_name,u.fun,u.job,u.firebase_token,u.notification,u.name,u.password,u.path,u.role,u.status\n" +
            " ,u.structure_id,s.code_struct,s.designation_struct,s.mother_structure FROM USER u JOIN STRUCTURE s ON u.structure_id=s.id ";
    String Q_EMAIL=Q+" WHERE email=:email";
    String Q_BY_ID=Q+" WHERE u.id=:id";
    String USER_BY_STRUCTURE=" SELECT u.id FROM USER u JOIN STRUCTURE s ON u.structure_id=s.id AND s.designation_struct:=structure";
    String Q_TOKEN="SELECT u.firebase_token FROM USER u JOIN STRUCTURE s ON u.structure_id=s.id WHERE s.designation_struct=:struct AND u.firebase_token IS NOT NULL AND u.notification=true";
    String Q_VERIFY="UPDATE USER SET status='blocked' WHERE DATE(CURRENT_DAY)>DATE(end_date) ";

    @Query(value = Q_EMAIL,nativeQuery = true)
    Tuple findByEmail_(String email);

    Optional<User> findByEmail(String email);

    @Query(value = Q,nativeQuery = true)
    List<Tuple> getUsers();

    @Query(value = Q_BY_ID,nativeQuery = true)
    Tuple getUserById(@Param("id") long id);

    @Query(value = Q_TOKEN,nativeQuery = true)
    List<Tuple> getTokens(@Param("struct") String struct);

    @Transactional
    @Modifying
    @Query(value = Q_VERIFY,nativeQuery = true)
    void verifyEligibility();
}
