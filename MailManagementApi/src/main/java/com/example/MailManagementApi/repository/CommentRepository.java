package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Comment;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    String Q="SELECT c.id,c.comment,c.date,u.name,u.first_name,u.path,u.id as user_id FROM COMMENT c JOIN USER u ON c.user_id=u.id WHERE pdf_id=:id";

    @Query(value = Q,nativeQuery = true)
    List<Tuple> getCommentById(@Param("id") long id);



}
