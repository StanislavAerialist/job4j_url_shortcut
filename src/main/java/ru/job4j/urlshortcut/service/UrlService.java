package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.model.Url;

import java.util.Optional;

public interface UrlService {
    UrlDto create(Url url);
    Optional<Url> findByKey(String key);

    String findAllUrlAndCountPerEach();
}