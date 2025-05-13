package org.parking.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class LogViewer {

    /* POLYMORPHISM — each subclass resolves its own file */
    protected abstract Path logFile();

    public List<String> viewLogs() throws IOException {
        if (Files.notExists(logFile())) return List.of();
        return Files.readAllLines(logFile());
    }
}
