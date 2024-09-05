package com.github.prakasit.springredisstrategies.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.prakasit.springredisstrategies.model.db.CountryEntity;
import com.github.prakasit.springredisstrategies.model.dto.CountryDto;
import com.github.prakasit.springredisstrategies.repository.CountryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class CountryService {


    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final CountryRepository countryRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "country:";
    private static final String ALL_BOOKS_CACHE_KEY = "all_country";

    public CountryService(CountryRepository countryRepository, RedisTemplate<String, Object> redisTemplate) {
        this.countryRepository = countryRepository;
        this.redisTemplate = redisTemplate;
    }

    /***************************************************
     * Cache-Aside Strategy && Read-through Caching
     **************************************************/
    public CountryEntity getCountryById(Integer id) {
        String cacheKey = CACHE_KEY_PREFIX + id;

        CountryEntity country = MAPPER.convertValue(redisTemplate.opsForValue().get(cacheKey), CountryEntity.class);
        if (country != null) {
            return country;
        }

        Optional<CountryEntity> countryFromDb = countryRepository.findById(id);
        if (countryFromDb.isPresent()) {
            redisTemplate.opsForValue().set(cacheKey, countryFromDb.get(), 5, TimeUnit.MINUTES);
            return countryFromDb.get();
        }

        return null;
    }

    public CountryEntity saveCountry(CountryDto country) {
        CountryEntity countryEntity = null;
        if (StringUtils.hasText(country.getCountryId()))
            countryEntity = countryRepository.findById(Integer.parseInt(country.getCountryId())).orElse(new CountryEntity());
        countryEntity = new CountryEntity();
        countryEntity.setCountryName(country.getCountryName());
        countryEntity.setCountryAbbName(country.getCountryAbbName());
        countryEntity.setStatus(country.getStatus());
        CountryEntity saveCountry = countryRepository.save(countryEntity);

        String cacheKey = CACHE_KEY_PREFIX + country.getCountryId();
        redisTemplate.opsForValue().set(cacheKey, saveCountry, 5, TimeUnit.MINUTES);

        redisTemplate.delete(ALL_BOOKS_CACHE_KEY);

        return saveCountry;
    }

    public void deleteCountry(Integer id) {
        countryRepository.deleteById(id);

        String cacheKey = CACHE_KEY_PREFIX + id;
        redisTemplate.delete(cacheKey);

        redisTemplate.delete(ALL_BOOKS_CACHE_KEY);
    }

    public List<CountryEntity> getAllCountry() {
        List<CountryEntity> countrys = (List<CountryEntity>) redisTemplate.opsForValue().get(ALL_BOOKS_CACHE_KEY);
        if (countrys != null && !countrys.isEmpty()) {
            return countrys;
        }

        countrys = countryRepository.findAll();
        if (!countrys.isEmpty()) {
            redisTemplate.opsForValue().set(ALL_BOOKS_CACHE_KEY, countrys, 5, TimeUnit.MINUTES);
        }
        return countrys;
    }
}
