package com.example.MailManagementApi.service;

import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.model.Favorite;
import com.example.MailManagementApi.helper_classes.FavoritePK;
import com.example.MailManagementApi.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService{

    @Autowired
    FavoriteRepository favoriteRepository;

    @Override
    public void insert(FavoritePK favorite) {

        Favorite favorite1=new Favorite(favorite,true,false);
        favoriteRepository.save(favorite1);
    }

    @Override
    public void insertWhenClick(FavoritePK favorite) {
        Favorite favorite1=new Favorite(favorite,true,true);
        favoriteRepository.save(favorite1);
    }

    @Override
    public void update(FavoritePK favorite) {
        favoriteRepository.findById(favorite)
                .map(f->{
                    f.setFavorite(!f.getFavorite());
                    return favoriteRepository.save(f);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));

    }
}
