package com.urlshortener.urlshortener.repository;


import com.urlshortener.urlshortener.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortenedUrl extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortUrl(String shortUrl);
    Optional<ShortUrl> findShortUrlByRandomValue(String code);
}
