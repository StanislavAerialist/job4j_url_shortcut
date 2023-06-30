package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.util.KeyAndLoginGenerator;
import ru.job4j.urlshortcut.util.PassEncoderHandler;
import ru.job4j.urlshortcut.util.PassGenerator;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class SimpleSiteService implements SiteService, UserDetailsService {
    private final SiteRepository siteRepository;
    private final PassGenerator passwordGenerator;
    private final KeyAndLoginGenerator keyAndLoginGenerator;
    private final PassEncoderHandler encoder;

    @Override
    public SiteDto create(Site site) {
        var rsl = new SiteDto();
        var password = passwordGenerator.generatePassword();
        site.setPassword(encoder.passwordEncoder().encode(password));
        String login;
        do {
            login = keyAndLoginGenerator.generateLogin(site.getName());
        } while (siteRepository.existsByLogin(login));
        site.setLogin(login);
        siteRepository.save(site);
        rsl.setRegStatus(true);
        rsl.setGeneratedLogin(site.getLogin());
        rsl.setGeneratedPassword(password);
        return rsl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Site> user = siteRepository.findByLogin(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.get().getLogin(), user.get().getPassword(), emptyList());
    }
}