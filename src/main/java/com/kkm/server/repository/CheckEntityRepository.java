package com.kkm.server.repository;

import com.kkm.server.entity.CheckEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CheckEntityRepository extends CrudRepository<CheckEntity, Integer> {
    List<CheckEntity> findByDateS(String dateS);
}
