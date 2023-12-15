package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.FavoritePK;

public interface FavoriteService {

    void insert(FavoritePK favorite);
    void insertWhenClick(FavoritePK favorite);
    void update(FavoritePK favorite);
}
