package dev.vinion.jtools.modules.fileModule.controllers;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping(path = "/file")
@RequiredArgsConstructor
public class FileController {
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@RequestParam("files") List<MultipartFile> files) {
        files.forEach((file) -> {
            System.out.printf("name %s\n", file.getOriginalFilename());
        });
    }
}
