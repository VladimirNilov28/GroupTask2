package sprint;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class AddNote {
    private NotesTool notesTool;

    // Конструктор принимает экземпляр NotesTool
    public AddNote(NotesTool notesTool) {
        this.notesTool = notesTool;
    }

    // Метод для добавления новой заметки в текущую коллекцию
    public void addNote() {
        if (notesTool.getCollection() == null || notesTool.getCollection().isEmpty()) {
            System.out.println("No collection selected. Please select a collection first.");
            return;
        }

        Scanner scanner = notesTool.getScanner();
        System.out.print("\nEnter a note: ");
        String noteName = scanner.nextLine().trim();

        if (noteName.isEmpty()) {
            System.out.println("Note name cannot be empty.");
            return;
        }

        Path collectionPath = notesTool.getPath();
        File noteFile = new File(collectionPath.toFile(), noteName);

        try {
            if (noteFile.createNewFile()) {
                System.out.println("\nNote '" + noteName + "' has been successfully created.");
            } else {
                System.out.println("\nA note with the name '" + noteName + "' already exists.");
            }
        } catch (IOException e) {
            System.err.println("\nError while creating the note: " + e.getMessage());
        }
    }
}
