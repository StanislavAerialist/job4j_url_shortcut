package ru.job4j.urlshortcut.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KeyAndLoginGenerator {
    private static final int LEFT_LIMIT = 48;
    private static final int RIGHT_LIMIT = 122;
    private static final int TARGET_STRING_LENGTH = 5;

    public String generateKey() {
        return generateString();
    }

    public String generateLogin(String siteName) {
        return siteName + generateString();
    }

    private String generateString() {
        Random random = new Random();
        return random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(TARGET_STRING_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}