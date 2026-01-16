package dev.vinion.jtools.services.managerfile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ManagerFile {
    @Getter
    private final Path tempFolderPath = Paths.get("temp");

    private void createTempFolder() {
        try {
            Files.createDirectories(this.tempFolderPath);
        } catch (java.io.IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public ManagerFile() {
        this.createTempFolder();
    }


}
