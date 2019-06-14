package com.kkm.server.repository;

import com.kkm.server.entity.Tovar;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TovarRepository extends CrudRepository<Tovar, Integer> {
    List<Tovar> findByArticle(String article);
}
