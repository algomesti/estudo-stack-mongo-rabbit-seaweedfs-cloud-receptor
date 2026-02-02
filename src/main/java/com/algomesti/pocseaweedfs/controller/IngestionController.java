package com.algomesti.pocminiocloud.controller;

import me.desair.tus.server.TusFileUploadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.upload.UploadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*") // Abre caminho para evitar bloqueios de CORS se houver
public class IngestionController {

    @Autowired
    private TusFileUploadService tusService;

    // Mapeamento ultra-abrangente para garantir que nada escape
    @RequestMapping(value = {"/alerts/sync", "/alerts/sync/**"}, method = {
            RequestMethod.POST, RequestMethod.PATCH, RequestMethod.HEAD,
            RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.GET
    })
    public void handleTus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        String offset = request.getHeader("Upload-Offset");

        if ("PATCH".equalsIgnoreCase(method)) {
            log.info(">>> [NUVEM] Recebendo pedaço do arquivo. Offset atual: {} bytes", offset);
        }

        tusService.process(request, response);

        // Após o processamento do Service, podemos checar se terminou
        UploadInfo info = tusService.getUploadInfo(request.getRequestURI());
        if (info != null && !info.isUploadInProgress()) {
            log.info(">>> [NUVEM] Entrega concluída! Total: {} bytes", info.getLength());
            // Aqui entra sua lógica de mover para o MinIO Cloud...
        }
    }
}