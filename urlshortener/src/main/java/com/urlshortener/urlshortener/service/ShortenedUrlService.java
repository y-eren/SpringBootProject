package com.urlshortener.urlshortener.service;


import com.urlshortener.urlshortener.dto.ResponseDTO;
import com.urlshortener.urlshortener.model.ShortUrl;
import com.urlshortener.urlshortener.repository.ShortenedUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortenedUrlService {

    private final ShortenedUrl shortenedUrlRepository;


    public ShortUrl createShortUrl(String url) {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortUrl(url);
        shortUrl.setRandomValue(generatedShortCode());
        shortUrl.setCreatedAt(LocalDateTime.now());
        shortUrl.setUpdatedAt(LocalDateTime.now());
        shortUrl.setAccessCount(0);
        return shortenedUrlRepository.save(shortUrl);
    }



    public ResponseDTO getCodeByShortUrlAndIncreaseAssesCount(String value) {
       ShortUrl shortUrl = shortenedUrlRepository.findShortUrlByRandomValue(value).orElseThrow( () -> new RuntimeException("Random value bulunamadi"));
       shortUrl.setAccessCount(shortUrl.getAccessCount() + 1);
       shortenedUrlRepository.save(shortUrl);
        return new ResponseDTO(shortUrl.getShortUrl(), shortUrl.getRandomValue(),shortUrl.getId(), shortUrl.getUpdatedAt(), shortUrl.getCreatedAt() );
    }

    private String generatedShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public ShortUrl updateShortUrl(String url,String value) {
       ShortUrl shortURL = shortenedUrlRepository.findShortUrlByRandomValue(value).orElseThrow( () -> new RuntimeException("Random value bulunamadi"));
       shortURL.setShortUrl(url);
       return shortenedUrlRepository.save(shortURL);
    }
}
