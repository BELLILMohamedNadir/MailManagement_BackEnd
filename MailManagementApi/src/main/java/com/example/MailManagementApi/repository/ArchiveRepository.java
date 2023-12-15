package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Archive;
import com.example.MailManagementApi.helper_classes.ArchivePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, ArchivePK> {
}
