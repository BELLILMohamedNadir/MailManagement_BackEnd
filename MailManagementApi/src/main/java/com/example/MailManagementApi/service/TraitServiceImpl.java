package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Trait;
import com.example.MailManagementApi.helper_classes.TraitPK;
import com.example.MailManagementApi.repository.TraitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TraitServiceImpl implements TraitService{

    @Autowired
    TraitRepository traitRepository;

    @Override
    public void insert(TraitPK traitPk) {
        Trait trait=new Trait(traitPk,true,true);
        traitRepository.save(trait);
    }

}
