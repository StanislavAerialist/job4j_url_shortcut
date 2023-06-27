package ru.job4j.urlshortcut.service;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.UrlRepository;
import ru.job4j.urlshortcut.util.KeyAndLoginGenerator;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUrlService implements UrlService {
    private final UrlRepository urlRepository;
    private final KeyAndLoginGenerator keyGen;

    @Override
    public UrlDto create(Url url) {
        String key;
        do {
            key = keyGen.generateKey();
        } while (urlRepository.existsByKey(key));
        url.setKey(key);
        urlRepository.save(url);
        var urlDto = new UrlDto();
        urlDto.setGeneratedKey(key);
        return urlDto;
    }

    @Transactional
    @Override
    public Optional<Url> findByKey(String key) {
        var rsl = urlRepository.findByKey(key);
        if (rsl.isPresent()) {
            urlRepository.incrementByCode(key);
            return rsl;
        }
        return rsl;


    }

    private <T> String toJson(T object) {
        var gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public String findAllUrlAndCountPerEach() {
        var map = new HashMap<>();
        urlRepository.findAll().forEach(p -> map.put(p.getUrl(), p.getCount()));
        return toJson(map);
    }
}