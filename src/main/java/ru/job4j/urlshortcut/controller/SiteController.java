package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.service.SiteService;

@RestController
@RequestMapping("/site")
@AllArgsConstructor
public class SiteController {
    private final SiteService siteService;

    @PostMapping("/registration")
    public ResponseEntity<SiteDto> create(@RequestBody Site site) {
        var rsl = siteService.create(site);
        if (!rsl.isRegStatus()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "The site has already registered.");
        }
        return new ResponseEntity<>(rsl, HttpStatus.CREATED);
    }


}