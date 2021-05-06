package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.GalleryImages;
import com.fatesg.fashion_boot.repository.GalleryImagesRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryImagesService {

    final GalleryImagesRepository repository;

    public GalleryImages save(GalleryImages galleryImages) {
        galleryImages.setId(null);
        return repository.save(galleryImages);
    }

    public Page<GalleryImages> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<GalleryImages> listAll() {
        return repository.findAll();

    }


    public List<GalleryImages> listAllByProductId(Long id) {
        return repository.findAllByProductId(id);

    }


    public GalleryImages findByIdOrThrowRequestException(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Marca não localizada"));

    }


    public void delete(Long id) {
        findByIdOrThrowRequestException(id);
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é posssivel apagar, pois está associada a um produto");
        }
    }

    public void replace(GalleryImages objeto) {
        GalleryImages categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(objeto);

    }

}
