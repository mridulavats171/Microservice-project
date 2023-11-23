package com.micro.userservice.Config;

import com.micro.userservice.Client.HotelClient;
import com.micro.userservice.Client.RatingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HotelWebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
    @Bean
    public WebClient hotelWebClient(){
        return WebClient.builder()
                .baseUrl("http://hotel-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public HotelClient hotelClient(){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(hotelWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(HotelClient.class);
    }

}
