package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Trait;
import com.example.MailManagementApi.helper_classes.TraitPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraitRepository extends JpaRepository<Trait, TraitPK> {
}
