package com.example.lms.service.implementation;



import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.lms.service.EasterEggService;


@Service
public class EasterEggServiceImpl implements EasterEggService {

    private final WebClient webClient;

    public EasterEggServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://numbersapi.com").build();
    }

    @Override
    public String getNumberFact(int number) {
        return webClient.get()
                .uri("/{number}", number)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
