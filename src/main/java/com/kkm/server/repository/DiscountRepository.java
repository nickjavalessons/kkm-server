package com.kkm.server.repository;

import com.kkm.server.entity.Discount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiscountRepository extends CrudRepository<Discount, Integer> {
    List<Discount> findByName(String name);

}
