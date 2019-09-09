package com.shevtsov.repository;

import com.shevtsov.domain.Characteristic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends CrudRepository<Characteristic, Long> {
}
