package com.example.MailManagementApi.service;

import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.model.Archive;
import com.example.MailManagementApi.helper_classes.ArchivePK;
import com.example.MailManagementApi.repository.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    ArchiveRepository archiveRepository;

    @Override
    public void insert(ArchivePK archivePK) {
        Archive archive=new Archive(archivePK,true,false);
        archiveRepository.save(archive);
    }

    @Override
    public void insertWhenClick(ArchivePK archivePk) {
        Archive archive=new Archive(archivePk,true,true);
        archiveRepository.save(archive);
    }

    @Override
    public void update(ArchivePK archivePK) {
        archiveRepository.findById(archivePK)
                .map(a->{
                    a.setArchive(!a.getArchive());
                    return archiveRepository.save(a);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }
}
