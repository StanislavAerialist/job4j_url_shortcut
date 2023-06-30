package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.service.UrlService;

@RestController
@RequestMapping("/site")
@AllArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/convert")
    public ResponseEntity<UrlDto> convert(@RequestBody Url url) {
        var rsl = urlService.create(url);
        return new ResponseEntity<>(rsl, HttpStatus.CREATED);
    }

    @GetMapping("/redirect/{key}")
    public ResponseEntity<String> convert(@PathVariable String key) {
        var rsl = urlService.findByKey(key);
        if (rsl.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).build();
        }
        var body = rsl.get().getUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Status", "HTTP CODE - 302 REDIRECT URL")
                .body(body);
    }

    @GetMapping("/statistic")
    public ResponseEntity<String> getStatistic() {
        var body = urlService.findAllUrlAndCountPerEach();
        return ResponseEntity.status(HttpStatus.OK)
                .header("Statistic", "Successful")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(body.length())
                .body(body);
    }
}
