package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Path;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepository extends JpaRepository<Path,Long> {

    @Modifying
    @Transactional
    @Query(value = " UPDATE PATH SET latest=false WHERE mail_id=:mail_id AND path=:path ",nativeQuery = true)
    void updateLatest(@Param("mail_id") long mail_id,@Param("path") String path);
}
