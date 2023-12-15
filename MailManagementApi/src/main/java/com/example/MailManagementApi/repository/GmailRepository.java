package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Gmail;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GmailRepository extends JpaRepository<Gmail,Long> {

    String Q=" SELECT g.id,g.recipient,g.subject,g.body,p.path,p.name FROM GMAIL g RIGHT JOIN (SELECT gmail_id, GROUP_CONCAT(path SEPARATOR ',') AS path, GROUP_CONCAT(name SEPARATOR ',') AS name\n" +
            "                            FROM path GROUP BY gmail_id) p ON g.id=p.gmail_id WHERE user_id=:id ";
    @Query(value = Q,nativeQuery = true)
    List<Tuple> findByUser(@Param("id") long id);

    String EMAILS="SELECT email FROM Contact";

    @Query(value = EMAILS ,nativeQuery = true)
    List<String> getEmails();

}
