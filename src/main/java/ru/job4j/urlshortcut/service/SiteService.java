package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.model.Site;

public interface SiteService {
    SiteDto create(Site site);
}