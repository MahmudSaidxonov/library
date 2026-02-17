package uz.lib.library.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class AppConfig {
    @Autowired
    LocalDateTimeDeserializer localDateTimeDeserializer;
    @Autowired
    LocalDateTimeSerializer localDateTimeSerializer;

    @Bean
    public Gson gson(){
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(LocalDateTime.class, localDateTimeSerializer);
        builder.registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer);

        return builder.create();
    }
}
