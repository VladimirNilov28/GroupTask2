package sprint;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class DeleteNote {
    private NotesTool notesTool;

    // Конструктор принимает экземпляр NotesTool
    public DeleteNote(NotesTool notesTool) {
        this.notesTool = notesTool;
    }

    // Метод для удаления заметки из текущей коллекции
    public void deleteNote() {
        int number = 0;

        if (notesTool.getCollection() == null || notesTool.getCollection().isEmpty()) {
            System.out.println("No collection selected. Please select a collection first.");
            return;
        }

        // Вывод всех заметок в текущей коллекции
        Path collectionPath = notesTool.getPath();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(collectionPath)) {
            System.out.println("Notes in the collection:");
            boolean hasNotes = false;
            for (Path entry : stream) {
                BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                if (attrs.isRegularFile()) {
                    System.out.println(number + ". " + entry.getFileName());
                    hasNotes = true;
                }
                number++;
            }
            if (!hasNotes) {
                System.out.println("No notes found in the collection.");
                return;
            }
        } catch (IOException e) {
            System.err.println("Error while listing notes: " + e.getMessage());
            return;
        }

        Scanner scanner = notesTool.getScanner();
        System.out.print("Enter note name to delete: ");
        String noteName = scanner.nextLine().trim();

        if (noteName.isEmpty()) {
            System.out.println("Note name cannot be empty.");
            return;
        }

        File noteFile = new File(collectionPath.toFile(), noteName);

        try {
            if (noteFile.exists() && noteFile.isFile()) {
                if (noteFile.delete()) {
                    System.out.println("Note '" + noteName + "' has been successfully deleted.");
                } else {
                    System.err.println("Error while deleting the note.");
                }
            } else {
                System.out.println("No note found with the name '" + noteName + "'.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
