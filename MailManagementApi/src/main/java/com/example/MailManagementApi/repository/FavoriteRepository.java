package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Favorite;
import com.example.MailManagementApi.helper_classes.FavoritePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoritePK> {
}
