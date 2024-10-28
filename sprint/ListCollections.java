package sprint;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ListCollections {
    private NotesTool notesTool;

    // Конструктор принимает экземпляр NotesTool
    public ListCollections(NotesTool notesTool) {
        this.notesTool = notesTool;
    }

    // Метод для вывода всех коллекций
    public void listCollections() {
        File mainDir = new File(notesTool.getMaindir().getPath());
        if (!mainDir.exists() || !mainDir.isDirectory()) {
            System.out.println("\nNo collections found.");
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(mainDir.toPath())) {
            System.out.println("\nAvailable collections:");
            boolean hasCollections = false;
            for (Path entry : stream) {
                BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                if (attrs.isDirectory()) {
                    // Проверяем, содержит ли директория файлы с расширением .class
                    try (DirectoryStream<Path> classStream = Files.newDirectoryStream(entry, "*.class")) {
                        if (classStream.iterator().hasNext()) {
                            continue; // Пропускаем директории, содержащие файлы с расширением .class
                        }
                    }
                    System.out.println("- " + entry.getFileName());
                    hasCollections = true;
                }
            }
            if (!hasCollections) {
                System.out.println("\nNo collections found.");
            }
        } catch (IOException e) {
            System.err.println("Error while listing collections: " + e.getMessage());
        }
    }
}
