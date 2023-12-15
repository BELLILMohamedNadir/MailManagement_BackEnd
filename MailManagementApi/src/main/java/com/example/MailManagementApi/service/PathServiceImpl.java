package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Path;
import com.example.MailManagementApi.repository.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathServiceImpl implements PathService{

    @Autowired
    PathRepository pathRepository;

    @Override
    public void insert(Path path) {
        pathRepository.save(path);
    }

    @Override
    public void updateLatest(long mail_id, List<String> path) {

        for (String s : path) {
            pathRepository.updateLatest(mail_id, s);
        }
    }
}
