package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.GalleryImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryImagesRepository extends JpaRepository<GalleryImages, Long> {

 List<GalleryImages> findAllByProductId(Long id);
}
