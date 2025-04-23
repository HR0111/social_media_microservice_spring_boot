package org.hemant.linkedin.uploaderservice.controller;

import lombok.RequiredArgsConstructor;
import org.hemant.linkedin.uploaderservice.service.FileUploaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FIleUploadController {

    private final FileUploaderService fileUploaderService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {

        try {
            String url = fileUploaderService.upload(file);
            return ResponseEntity.ok(url);

        }catch (IOException e){
            throw new RuntimeException(e);
        }


    }

}
