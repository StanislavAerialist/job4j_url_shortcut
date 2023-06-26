package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.UrlRepository;
import ru.job4j.urlshortcut.util.KeyAndLoginGenerator;

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
}