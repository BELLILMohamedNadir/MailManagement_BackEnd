package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.*;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
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
public interface CategoryRepository extends JpaRepository<Category,Long> {


    @Query(value = "SELECT * FROM CATEGORY c JOIN STRUCTURE S ON c.structure_id=s.id",nativeQuery = true)
    List<Tuple> getCategories();

    @Query(value = "SELECT c.id,c.cpt,c.designation,c.code FROM category c JOIN structure s ON s.id=c.structure_id WHERE c.structure_id =:id AND c.type='Arriver' " ,nativeQuery = true)
    List<Tuple> getArrivedCategoryInfo(@Param("id") long id);

    @Query(value = "SELECT c.id,c.cpt,c.designation,c.code FROM category c JOIN structure s ON s.id=c.structure_id WHERE c.structure_id =:id AND c.type='Depart' " ,nativeQuery = true)
    List<Tuple> getSendCategoryInfo(@Param("id") long id);

    @Query(value = "SELECT * FROM category WHERE id=:category_id and structure_id=:struct_id",nativeQuery = true)
    Category getCpt(@Param("category_id") long category_id, @Param("struct_id") long struct_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE CATEGORY SET cpt=0",nativeQuery = true)
    int resetCounter();

    @Query(value = "SELECT designation FROM category where structure_id=:id",nativeQuery = true)
    List<String> getCategoryDesignation(@Param("id") long id);

    @Query(value = "SELECT designation FROM category where structure_id=:id AND type='Arriver' ",nativeQuery = true)
    List<String> getArrivedCategoryDesignation(@Param("id") long id);

}
