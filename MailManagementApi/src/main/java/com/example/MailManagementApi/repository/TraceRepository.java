package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Trace;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TraceRepository extends JpaRepository<Trace,Long> {

    String Q=" SELECT t.id,t.time,t.update_time,t.reference,t.action,t.user_id,u.first_name,u.name,u.path,u.email,u.job\n" +
            "             FROM (SELECT t.id,t.time,t.update_time,t.reference,t.action,t.user_id,p.path,p.name FROM Trace t RIGHT JOIN (SELECT trace_id, GROUP_CONCAT(path SEPARATOR ',') AS path, GROUP_CONCAT(name SEPARATOR ',') AS name\n" +
            "                        FROM path GROUP BY trace_id) p ON t.id=p.trace_id ) t JOIN User u WHERE t.user_id=u.id ";

    @Query(value = Q,nativeQuery = true)
    List<Tuple> getTraces();



}
