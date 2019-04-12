package me.duncte123.menuDocs.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class ConfigLoader {

    String load(File file) throws IOException {
        return new String(
                Files.readAllBytes(file.toPath())
        );
    }

}
