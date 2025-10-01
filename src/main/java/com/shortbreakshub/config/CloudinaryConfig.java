package com.shortbreakshub.config;


import com.cloudinary.Cloudinary;
import com.shortbreakshub.dto.CloudinaryProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(CloudinaryProps props) {
        Map<String,Object> config = new HashMap<>();
        config.put("cloud_name",props.cloudName());
        config.put("api_key",props.apiKey());
        config.put("api_secret",props.apiSecret());
        return new Cloudinary(config);
    }
}

