package com.urlshortener.urlshortener.controller;

import com.urlshortener.urlshortener.dto.RequestDTO;
import com.urlshortener.urlshortener.dto.ResponseDTO;
import com.urlshortener.urlshortener.model.ShortUrl;
import com.urlshortener.urlshortener.repository.ShortenedUrl;
import com.urlshortener.urlshortener.service.ShortenedUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

    private final ShortenedUrlService shortenedUrlService;

    @PostMapping("/saveUrl")
    public ResponseEntity<ResponseDTO> saveUrl(@RequestBody RequestDTO requestDTO) {
        ShortUrl shortUrl = shortenedUrlService.createShortUrl(requestDTO.getUrl());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setOriginalUrl(shortUrl.getShortUrl());
        responseDTO.setShortCode(shortUrl.getRandomValue());
        responseDTO.setCreatedAt(shortUrl.getCreatedAt());
        responseDTO.setUpdatedAt(shortUrl.getUpdatedAt());
        responseDTO.setId(shortUrl.getId());
        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/getUrl")
    public ResponseEntity<ResponseDTO> getShortUrl(@RequestParam String shortCode) {

        return ResponseEntity.ok(shortenedUrlService.getCodeByShortUrlAndIncreaseAssesCount(shortCode));
    }

    @PutMapping("/{value}")
    public ResponseEntity<ShortUrl> updateShortUrl(@RequestBody String url,@PathVariable String value) {
         return ResponseEntity.ok(shortenedUrlService.updateShortUrl(url,value));

    }


}
