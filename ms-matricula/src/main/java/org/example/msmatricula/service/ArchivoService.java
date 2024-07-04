package org.example.msmatricula.service;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivoService {
    public String saveFile(MultipartFile file);

    public Resource loadFile(String filename);
}