package robertovisconti.be_capstone_msartballet.tools;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Component
public class CloudinaryUploaderService {

    private final Cloudinary cloudinary;

    public CloudinaryUploaderService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String caricaImmagine(MultipartFile file) {
        valida(file, "image/");
        return carica(file);
    }

    public String caricaMedia(MultipartFile file) {
        valida(file, "image/", "video/");
        return carica(file);
    }

    private String carica(MultipartFile file) {
        File fileTemporaneo = null;
        try {
            fileTemporaneo = File.createTempFile("upload-", "-" + file.getOriginalFilename());
            file.transferTo(fileTemporaneo);
            Map<?, ?> risultato = cloudinary.uploader().upload(fileTemporaneo, ObjectUtils.asMap("resource_type", "auto"));
            return (String) risultato.get("secure_url");
        } catch (IOException e) {
            throw new BadRequestException("Errore durante il caricamento del file: " + e.getMessage());
        } finally {
            if (fileTemporaneo != null) {
                fileTemporaneo.delete();
            }
        }
    }

    private void valida(MultipartFile file, String... prefissiConsentiti) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Il file è vuoto!");
        }
        String contentType = file.getContentType();
        boolean valido = contentType != null && Arrays.stream(prefissiConsentiti).anyMatch(contentType::startsWith);
        if (!valido) {
            throw new BadRequestException("Formato file non supportato: " + contentType);
        }
    }
}
