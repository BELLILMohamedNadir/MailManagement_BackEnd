package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.ArchivePK;

public interface ArchiveService {

    void insert(ArchivePK archivePk);
    void insertWhenClick(ArchivePK archivePk);
    void update(ArchivePK archivePk);
}
