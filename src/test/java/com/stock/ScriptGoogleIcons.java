package com.stock;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

class ScriptGoogleIcons {
    @Test
    void getIconsGoogle() throws IOException {
        Path dir = Paths.get("/Users/anymacstore/Learning/Code/GoogleIcons/material-design-icons-4.0.0/android");
        Path outputFile = Paths.get("/Users/anymacstore/WorkPlace/CV/CV_projects/StockArchive_App/server/src/main/resources/liquibase/infrastructure/google_icon.sql");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
             FileWriter writer = new FileWriter(outputFile.toString())) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    String category = entry.getFileName().toString();
                    try (DirectoryStream<Path> subStream = Files.newDirectoryStream(entry)) {
                        for (Path subEntry : subStream) {
                            if (Files.isDirectory(subEntry)) {
                                String icon = subEntry.getFileName().toString();
                                String result = "INSERT INTO google_icons (category_icon, icon) VALUES ('" + category + "', '"+ icon +"');\n";
                                writer.write(result);
                            }
                        }
                    }
                }
            }
        }
    }
}
