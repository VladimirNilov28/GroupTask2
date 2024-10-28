package sprint;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ShowNotes {
    private int number;
    private NotesTool notesTool;

    public ShowNotes(NotesTool notesTool) {
        this.notesTool = notesTool;
    }

    public void showNotes() {
        number = 0; // Сброс счётчика
        Path dir = notesTool.getPath();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            boolean isEmpty = true;
            for (Path entry : stream) {
                BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                if (attrs.isDirectory()) {
                    System.out.println("[Directory] " + entry.getFileName());
                } else if (attrs.isRegularFile()) {
                    System.out.println(number + ". " + entry.getFileName());
                }
                number++;
                isEmpty = false;
            }
            if (isEmpty) {
                System.out.println("\nNo notes found in the collection.");
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
