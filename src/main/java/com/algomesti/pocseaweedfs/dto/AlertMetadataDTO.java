package com.algomesti.pocminiocloud.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class AlertMetadataDTO {
    private String id;
    private String cameraId;
    private String type;
    private Instant timestamp;
}