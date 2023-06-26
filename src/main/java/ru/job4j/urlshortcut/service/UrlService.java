package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.model.Url;

public interface UrlService {
    UrlDto create(Url url);
}