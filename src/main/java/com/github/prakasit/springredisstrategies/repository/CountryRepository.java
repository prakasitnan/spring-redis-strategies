package com.github.prakasit.springredisstrategies.repository;

import com.github.prakasit.springredisstrategies.model.db.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Repository;

@EnableRedisRepositories
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

}
