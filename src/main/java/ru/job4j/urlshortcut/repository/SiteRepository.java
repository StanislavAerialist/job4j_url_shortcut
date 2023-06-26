package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends CrudRepository<Site, Integer> {
    List<Site> findAll();

    Optional<Site> findByLogin(String login);

    boolean existsByLogin(String login);
}