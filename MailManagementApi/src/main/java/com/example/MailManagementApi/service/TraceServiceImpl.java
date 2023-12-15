package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.TraceResponse;
import com.example.MailManagementApi.repository.TraceRepository;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class TraceServiceImpl implements TraceService{


    @Autowired
    TraceRepository traceRepository;

    public List<TraceResponse> get() {
        List<TraceResponse> traces=new ArrayList<>();
        List<Tuple> tuples=traceRepository.getTraces();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        for (Tuple row : tuples){

            Long id=(Long) row.get("id");
            Date time=null;
            String timeToShow="";
            if (row.get("time")!=null) {
                time = (Date) row.get("time");
                timeToShow=simpleDateFormat.format(time);
            }
            Date updateTime=null;
            String updateTimeToShow="";
            if (row.get("update_time")!=null) {
                updateTime = (Date) row.get("update_time");
                updateTimeToShow=simpleDateFormat.format(updateTime);
            }
            String action=(String) row.get("action");
            String reference=(String) row.get("reference");
            String name=(String) row.get("name");
            String firstName=(String) row.get("first_name");
            String job=(String) row.get("job");
            String email=(String) row.get("email");
            String photoPath=(String) row.get("path");

            byte[] bytes=null;
            if (photoPath!=null && !photoPath.isEmpty())
                bytes=getPhoto(photoPath);
            traces.add(new TraceResponse(id,bytes,time,updateTime,timeToShow,updateTimeToShow,action,reference,name,firstName,job,email));

        }

        return traces;
    }
    public static byte[] getPhoto(String p){
        if (p!=null)
            try {
                File file = new File(p);
                Path path = file.toPath();
                return Files.readAllBytes(path);
            } catch (IOException e) {
                // handle exception
            }
        return null;

    }


}
