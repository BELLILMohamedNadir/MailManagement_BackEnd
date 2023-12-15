package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.*;
import com.example.MailManagementApi.model.*;
import jakarta.persistence.Tuple;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface MailService {

    void createMail(List<MultipartFile> file, MailDetails detail) throws ParseException;
    Mail updateMail(long id, List<MultipartFile> file, PathInfo path);
    List<MailResponse> allMails(long id, long userId, String reference);
    List<MailResponse> sendMails(long id, long userId);
    List<MailResponse> traitMails(String reference, long userId);
    List<MailResponse> toTraitMails(String reference, long userId);
    List<MailResponse> favoriteMails(long id, long userId);
    List<MailResponse> archiveMails(long id, long userId, String reference);
    List<String> references(long id);
    List<MailResponse> receivedMails(String reference, long userId);
    void updateReceivedMail(long id, ReceivedMail mail);
    List<MailFormat> mailInfo(List<Tuple> resultList);
}
