package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.helper_classes.FavoritePK;
import com.example.MailManagementApi.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favorite")
@Slf4j
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/insert")
    public void insert(@RequestBody FavoritePK favorite){

        favoriteService.insertWhenClick(favorite);
    }
    @PutMapping("/update")
    public void update(@RequestBody FavoritePK favorite){
        favoriteService.update(favorite);
    }


}
