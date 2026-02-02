package com.algomesti.pocseaweedfs.config;

import me.desair.tus.server.TusFileUploadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class TusConfig {

    @Bean
    public TusFileUploadService tusFileUploadService() throws IOException {
        String storagePath = "/tmp/tus-uploads";
        Files.createDirectories(Paths.get(storagePath));

        return new TusFileUploadService()
                .withStoragePath(storagePath)
                .withUploadUri("/alerts/sync") // <--- ADICIONE ESTA LINHA EXATAMENTE ASSIM
                .withDownloadFeature();
    }
}