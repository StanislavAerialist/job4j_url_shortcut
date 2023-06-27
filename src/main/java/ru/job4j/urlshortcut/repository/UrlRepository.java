package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Url;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {
    boolean existsByKey(String key);

    Optional<Url> findByKey(String key);

    List<Url> findAll();

    @Modifying
    @Query(value = """
            UPDATE Url u SET u.count = u.count + 1 WHERE u.key = ?1""")
    void incrementByCode(String key);
}