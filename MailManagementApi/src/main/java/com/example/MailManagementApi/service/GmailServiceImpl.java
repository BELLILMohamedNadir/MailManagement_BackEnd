package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.GmailFormat;
import com.example.MailManagementApi.helper_classes.GmailDetails;
import com.example.MailManagementApi.helper_classes.GmailResponse;
import com.example.MailManagementApi.model.*;
import com.example.MailManagementApi.repository.GmailRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GmailServiceImpl implements GmailService {


    @Autowired
    private GmailRepository gmailRepository;

    @Autowired
    private PathService pathService;

    @Autowired
    private EmailService emailService;

    @Override
    public void uploadPdfGmail(List<MultipartFile> file, GmailDetails detail) {
        Gmail gmailDetail=new Gmail(detail.getUser(),detail.getRecipient(),detail.getSubject(),detail.getBody());
        gmailRepository.save(gmailDetail);
        try {
            generatePath(file,gmailDetail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<GmailResponse> getGmail(long id) {
        return getPdfs(findGmail(gmailRepository.findByUser(id)));
    }

    public byte[] getPdfFile(String p) {
        try {
            File file = new File(p);
            Path path = file.toPath();
            byte[] pdfBytes = Files.readAllBytes(path);
            return pdfBytes;
        } catch (IOException e) {
            // handle exception
        }
        return null;
    }


    private List<GmailFormat> findGmail(List<Tuple> resultList){
        List<GmailFormat> list=new ArrayList<>();
        for (Tuple row : resultList) {
            Long id = (Long) row.get("id");
            String recipient = (String) row.get("recipient");
            String subject = (String) row.get("subject");
            String body=(String) row.get("body");
            String path = (String) row.get("path");
            String name = (String) row.get("name");
            list.add(new GmailFormat(id,recipient,subject,body,path,name));
        }
        return list;
    }

    public List<GmailResponse> getPdfs(List<GmailFormat> list){
        List<GmailResponse> list1=new ArrayList<>();
        GmailFormat aGmail;
        int i=0;
        if (list!=null)
            while(i<list.size()){
                List<String> pdfPathList = Arrays.asList(list.get(i).getPath().split(","));
                List<String> pdfNameList = Arrays.asList(list.get(i).getName().split(","));
                aGmail=list.get(i);
                List<byte[]> bytes=new ArrayList<>();
                List<String> names=new ArrayList<>();
                for (int j=0;j<pdfPathList.size();j++){
                    bytes.add(getPdfFile(pdfPathList.get(j)));
                    names.add(pdfNameList.get(j));
                }
                list1.add(new GmailResponse(aGmail.getId(),aGmail.getRecipient(),aGmail.getSubject(),aGmail.getBody(),names,bytes));
                i++;
            }
        return list1;
    }

    private void generatePath(List<MultipartFile> file,Gmail gmail) throws MessagingException {

        List<String> paths=new ArrayList<>();
        List<String> names=new ArrayList<>();
        for (int i=0;i<file.size();i++) {
            try {
                String fileName = file.get(i).getOriginalFilename();
                byte[] bytes = file.get(i).getBytes();

                if (fileName != null) {
                    // Save the file to disk
                    File newFile = new File(fileName);
                    String path=newFile.getPath().replace(".pdf",gmail.getId()+"mailManagementAppMobilisEmail"+i)+".pdf";
                    paths.add(path);
                    names.add(fileName);
                    FileOutputStream fos = new FileOutputStream(path);
                    fos.write(bytes);
                    fos.close();
                    saveTrace(fileName,path,gmail.getId());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        emailService.sendEmail(gmail.getRecipient(),gmail.getSubject(),gmail.getBody(),paths,names);
    }



    @Override
    public List<String> getEmails() {
        return gmailRepository.getEmails();
    }

    @Transactional
    private void saveTrace(String fileName,String path,long gmail_id) {
        com.example.MailManagementApi.model.Path p = new com.example.MailManagementApi.model.Path(new Gmail(gmail_id),path, fileName);
        pathService.insert(p);
    }
}
