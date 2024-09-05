package com.github.prakasit.springredisstrategies.controller;

import com.github.prakasit.springredisstrategies.model.db.CountryEntity;
import com.github.prakasit.springredisstrategies.model.dto.CountryDto;
import com.github.prakasit.springredisstrategies.service.CountryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getCountry() {
        List<CountryEntity> countryList = countryService.getAllCountry();
        return ResponseEntity.ok(countryList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCountryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @ApiResponse(responseCode = "200", description = "OK",
    content = {@Content(mediaType = "application/json",
    schema = @Schema(implementation = CountryEntity.class)) })
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity saveCountry(@Valid CountryDto country) {
        log.debug(country);
        return ResponseEntity.ok(countryService.saveCountry(country));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCountry(@PathVariable("id") Integer id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok().build();
    }


}
