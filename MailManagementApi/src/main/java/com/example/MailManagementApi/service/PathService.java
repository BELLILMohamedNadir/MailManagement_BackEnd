package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Path;

import java.util.List;

public interface PathService {

    void insert(Path path);
    void updateLatest(long mail_id, List<String> path);
}
