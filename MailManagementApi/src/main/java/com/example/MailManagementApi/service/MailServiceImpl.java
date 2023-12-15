package com.example.MailManagementApi.service;

import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.helper_classes.*;
import com.example.MailManagementApi.model.*;
import com.example.MailManagementApi.repository.*;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    @Autowired
    private MailRepository mailRepository;


    @Autowired
    private TraceRepository traceRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    StructureRepository structureRepository;


    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PathService pathService;

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    ArchiveService archiveService;

    @Autowired
    TraitService traitService;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Override
    public void createMail(List<MultipartFile> file, MailDetails detail) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date=Calendar.getInstance().getTime();
        Date departureDate=simpleDateFormat.parse(detail.getDepartureDate().trim());
        Date mailDate=simpleDateFormat.parse(detail.getMailDate().trim());

        // trait the concerned structure one by one
        for (int i=0;i<detail.getForStructure().size();i++) {
            Mail mail = new Mail(detail.getStructure(), detail.getCategory().get(i), detail.getForStructure().get(i), date
                    , departureDate, mailDate,detail.getInternalReference().get(i)
                    , detail.getObject(), detail.getRecipient(), detail.getPriority(), detail.getType()
                    , detail.getResponseOf(),detail.isResponse(), detail.isClassed());
            mailRepository.save(mail);


            //generate paths
            generatePath("add", mail.getId(), file, detail.getUser(), detail.getInternalReference().get(i),date, null);

            //send notifications
            sendNotification(detail,i);

            //update the category
            updateCategory(detail,i);

            //insert favorite,trait and archive
            FavoritePK favoritePK=new FavoritePK(mail.getId(),detail.getUser().getId());
            ArchivePK archivePK=new ArchivePK(mail.getId(),detail.getUser().getId());
            TraitPK traitPK=new TraitPK(mail.getId(),detail.getUser().getId());

            favoriteService.insert(favoritePK);
            archiveService.insert(archivePK);
            traitService.insert(traitPK);
        }

    }

    private void updateCategory(MailDetails detail, int i) {
        Category cpt = categoryRepository.getCpt(detail.getCategory().get(i).getId(), detail.getStructure().getId());
        cpt.setCpt(cpt.getCpt() + 1);
        categoryRepository.save(cpt);
    }


    private void sendNotification(MailDetails detail, int i) {
        Optional<Structure> structure = structureRepository.findById(detail.getStructure().getId());
        String s = "";
        if (structure.isPresent())
            s = structure.get().getDesignation();
        notificationService.sendNotificationByToken(new NotificationMessage(detail.getForStructure().get(i), s, detail.getInternalReference().get(i)));
    }


    @Scheduled(cron = "0 0 0 1 1 ?")
    public int resetCounter(){
        return categoryRepository.resetCounter();
    }

    @Override
    public Mail updateMail(long id, List<MultipartFile> file,PathInfo paths) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        List<String> pdfPathList = Arrays.asList(paths.getPath().split(","));
        return mailRepository.findById(id)
                .map(p->{
                    pathService.updateLatest(id,pdfPathList);
                    try {
                        generatePath("updated",id,file,paths.getUser(),paths.getInternalReference(),simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime())),p.getEntryDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return mailRepository.save(p);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));

    }


    @Override
    public List<MailResponse> allMails(long id, long userId, String reference) {
        return getPdfs(mailInfo(mailRepository.findAllMails(id,userId,reference)));
    }

    @Override
    public List<MailResponse> sendMails(long id, long userId) {
        return getPdfs(mailInfo(mailRepository.findSendMails(id,userId)));
    }

    @Override
    public List<MailResponse> traitMails(String reference, long userId) {
        if (mailRepository.findTraitMails(reference,userId).isPresent())
            return getPdfs(mailInfo(mailRepository.findTraitMails(reference,userId).get()));
        return new ArrayList<>();
    }

    @Override
    public List<MailResponse> toTraitMails(String reference, long userId) {
        if (mailRepository.findToTraitMails(reference,userId).isPresent())
            return getPdfs(mailInfo(mailRepository.findToTraitMails(reference,userId).get()));
        return new ArrayList<>();
    }

    @Override
    public List<MailResponse> favoriteMails(long id, long userId) {
        return getPdfs(mailInfo(mailRepository.findFavoriteMails(id,userId)));
    }

    @Override
    public List<MailResponse> archiveMails(long id, long userId, String reference) {
        return getPdfs(mailInfo(mailRepository.findArchiveMails(id,userId,reference)));
    }

    @Override
    public List<MailFormat> mailInfo(List<Tuple> resultList) {
        List<MailFormat> list=new ArrayList<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        for (Tuple row : resultList) {
            Long id = (Long) row.get("id");
            String type = (String) row.get("type");
            String category = (String) row.get("category");
            String forStructure=(String) row.get("for_structure");
            String sender=(String) row.get("sender");
            Date departure_date = (Date) row.get("departure_date");
            Date entry_date = (Date) row.get("entry_date");
            String entryDateToShow=simpleDateFormat.format(entry_date);
            String internal_reference = (String) row.get("internal_reference");
            Date mail_date = (Date) row.get("mail_date");

            String name = (String) row.get("name");
            String path = (String) row.get("path");
            String object = (String) row.get("object");
            String priority = (String) row.get("priority");
            String recipient = (String) row.get("recipient");
            String responseOf = (String) row.get("response_of");
            Date entryDateReceived = null;
            String entryDateReceivedToShow="";
            if (row.get("entry_date_received")!=null) {
                entryDateReceived = (Date) row.get("entry_date_received");
                entryDateReceivedToShow = simpleDateFormat.format(entryDateReceived);
            }
            String receivedCategory=null;
            if (row.get("received_category")!=null)
                receivedCategory=(String) row.get("received_category");
            String mailReference = (String) row.get("mail_reference");
            String objectReceived = (String) row.get("object_received");
            Boolean classed = (Boolean) row.get("classed");
            Boolean response = (Boolean) row.get("response");
            Boolean favorite = false;
            if (row.get("favorite")!=null)
                favorite=(Boolean) row.get("favorite");
            Boolean archive = false;
            if (row.get("archive")!=null)
                archive=(Boolean) row.get("archive");
            Boolean trait = false;
            if (row.get("trait")!=null)
                trait=(Boolean) row.get("trait");

            Boolean initializedFavorite = false;
            if (row.get("initialized_favorite")!=null)
                initializedFavorite=(Boolean) row.get("initialized_favorite");
            Boolean initializedArchive = false;
            if (row.get("initialized_archive")!=null)
                initializedArchive=(Boolean) row.get("initialized_archive");
            Boolean initializedTrait = false;
            if (row.get("initialized_trait")!=null)
                initializedTrait=(Boolean) row.get("initialized_trait");


            list.add(new MailFormat(id,forStructure,sender,type,category,receivedCategory,departure_date,entry_date,entryDateReceived,mail_date,entryDateToShow
                    ,entryDateReceivedToShow,internal_reference,mailReference,objectReceived,name,path,object,priority,recipient,responseOf,classed,favorite,response,trait,archive
                    ,initializedFavorite,initializedArchive,initializedTrait));
        }


        return list;
    }


    @Override
    public List<String> references(long id) {
        return mailRepository.getReferences(id);
    }

    @Override
    public List<MailResponse> receivedMails(String reference, long userId) {
        return getPdfs(mailInfo(mailRepository.findReceivedMails(reference,userId)));
    }

    @Override
    public void updateReceivedMail(long id,ReceivedMail mail) {

        mailRepository.findById(id)
                .map(p->{
                    p.setEntryDateReceived(Calendar.getInstance().getTime());
                    p.setReceivedCategory(mail.getReceivedCategory());
                    p.setMailReference(mail.getMailReference());
                    p.setObjectReceived(mail.getObjectReceived());
                    Category cpt=categoryRepository.getCpt(mail.getCategory().getId(),mail.getStructure().getId());
                    cpt.setCpt(cpt.getCpt()+1);
                    categoryRepository.save(cpt);
                    return mailRepository.save(p);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
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

    //extract pdfs from paths
    public List<MailResponse> getPdfs(List<MailFormat> list){
        List<MailResponse> list1=new ArrayList<>();
        MailFormat mail;
        int i=0;
        if (list!=null)
            while(i<list.size()){
                List<String> pdfPathList = Arrays.asList(list.get(i).getPath().split(","));
                List<String> pdfNameList = Arrays.asList(list.get(i).getName().split(","));
                mail=list.get(i);
                List<byte[]> bytes=new ArrayList<>();
                List<String> names=new ArrayList<>();
                List<String> paths=new ArrayList<>();
                for (int j=0;j<pdfPathList.size();j++){
                    bytes.add(getPdfFile(pdfPathList.get(j)));
                    names.add(pdfNameList.get(j));
                    paths.add(pdfPathList.get(j));
                }
                list1.add(new MailResponse(mail.getId(),names,paths,bytes,mail.getForStructure(),mail.getSender(),mail.getCategory(),mail.getReceivedCategory(),mail.getType()
                        ,mail.getDepartureDate(),mail.getEntryDate(),mail.getEntryDateReceived(),mail.getMailDate(),mail.getEntryDateToShow()
                        ,mail.getEntryDateReceivedToShow(),mail.getInternalReference(),mail.getMailReference(),mail.getObjectReceived()
                        ,mail.getObject(),mail.getPriority(),mail.getRecipient(),mail.getResponseOf()
                        ,mail.isClassed(),mail.isFavorite(),mail.isResponse(),mail.isTrait(),mail.isArchive()
                        ,mail.isInitializedFavorite(),mail.isInitializedArchive(),mail.isInitializedTrait()));
                i++;
            }
        return list1;
    }

    private void generatePath(String source,Long mail_id,List<MultipartFile> file,User user,String internalReference,Date time,Date updateTime){

        for (int i=0;i<file.size();i++) {
            try {
                String fileName = file.get(i).getOriginalFilename();
                byte[] bytes = file.get(i).getBytes();

                if (fileName != null) {
                    // Save the file to disk
                    File newFile = new File(fileName);
                    String path=newFile.getPath().replace(".pdf",mail_id+"mailManagementAppMobilisMail"+i)+".pdf";
                    FileOutputStream fos = new FileOutputStream(path);
                    fos.write(bytes);
                    fos.close();
                    //save trace
                    saveTrace(source,user,time,updateTime,internalReference,mail_id,path,fileName);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Transactional
    private void saveTrace(String source, User user, Date time,Date updateTime, String internalReference, long mail_id, String path, String fileName) {
        Trace trace;
        if (source.equals("add")) {
            trace = new Trace(user, time, "Ajouter un courrier", internalReference);
        } else {
            trace = new Trace(user, time,updateTime, "Modifier un courrier", internalReference);
        }
        traceRepository.save(trace);

        com.example.MailManagementApi.model.Path p = new com.example.MailManagementApi.model.Path(new Mail(mail_id), trace, path, fileName, true);

        pathService.insert(p);
    }

}
