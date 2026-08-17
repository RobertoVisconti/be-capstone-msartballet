package robertovisconti.be_capstone_msartballet.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.payloadsDTO.uploadDTO.ImmagineRespDTO;
import robertovisconti.be_capstone_msartballet.tools.CloudinaryUploaderService;

@RestController
@RequestMapping("/uploads")
public class UploadController {

    private final CloudinaryUploaderService cloudinaryUploaderService;

    public UploadController(CloudinaryUploaderService cloudinaryUploaderService) {
        this.cloudinaryUploaderService = cloudinaryUploaderService;
    }

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ImmagineRespDTO carica(@RequestParam("file") MultipartFile file) {
        String url = cloudinaryUploaderService.caricaMedia(file);
        return new ImmagineRespDTO(url);
    }
}
