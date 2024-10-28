package sprint;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class NotesTool implements NoteUI {

    private int operation;
    private boolean exitCommand = false;
    private Scanner scanner = new Scanner(System.in);
    private File maindir = new File(NotesTool.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    private String collection; // Изначально пусто, но не null

    @Override
    public void selectCollection() {
        boolean folderExist = false;
        ListCollections list = new ListCollections(this);

        while (!folderExist) {
            list.listCollections();
            System.out.print("\nCollection: ");
            collection = scanner.nextLine().trim();

            if (collection.equalsIgnoreCase("exit")) {
                exitCommand = true;
                return;
            }

            if (collection.isEmpty() || collection.matches("\\d+")) {
                System.out.println("\nInvalid input. Please enter the collection name correctly.\n(Type 'exit' to quit)");
            } else {
                File folder = new File(Paths.get(maindir.getPath(), collection).toString());
                if (folder.exists() && folder.isDirectory()) {
                    System.out.println("Collection has been chosen.\n");
                    folderExist = true;
                } else {
                    System.out.println("""
                            Collection not found.\n
                            Do you want to create it? (y/n)
                            (Type 'exit' to quit)
                            """);
                    String choice = scanner.nextLine().trim();

                    if (choice.equalsIgnoreCase("exit")) {
                        exitCommand = true;
                        return;
                    }

                    if (choice.equalsIgnoreCase("y")) {
                        try {
                            Path path = folder.toPath();
                            Files.createDirectories(path);
                            System.out.println("Collection has been successfully created!");
                            folderExist = true;
                        } catch (IOException e) {
                            System.err.println("Oops! Exception while creating collection: " + e.getMessage());
                        }
                    } else if (choice.equalsIgnoreCase("n")) {
                        System.out.println("Collection creation has been canceled.");
                    } else {
                        System.out.println("\nInvalid input. Please enter 'y' or 'n'.\n(Type 'exit' to quit)");
                    }
                }
            }
        }
    }

    @Override
    public void selectOperation() {
        boolean validInput = false;
        while (!validInput) {
            try {
                
                System.out.println("""
                        \nSelect operation:
                        1. Show notes
                        2. Add a note
                        3. Delete a note
                        4. Switch collection
                        (Type 'exit' to quit)\n""");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("exit")) {
                    exitCommand = true;
                    return;
                }

                operation = Integer.parseInt(input);

                if (operation >= 1 && operation <= 4) {
                    validInput = true;
                } else {
                    System.out.println("\nInvalid input. Please enter a number (1 - 4).");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number (1 - 4).");
            }
        }
    }

    @Override
    public void noteManager() {
        if (collection == null || collection.isEmpty()) {
            System.out.println("No collection selected. Please select a collection first.");
            return;
        }
    
        ShowNotes show = new ShowNotes(this);
        AddNote add = new AddNote(this);
        DeleteNote delete = new DeleteNote(this);
    
        switch (operation) {
            case 1 -> {
                System.out.println("Current collection in noteManager: " + collection + "\n");
                show.showNotes();
            }
            case 2 -> add.addNote();
            case 3 -> delete.deleteNote();
            case 4 -> selectCollection();
            default -> throw new AssertionError();
        }
    }

    //----------------------------------------------------------------

    public Path getPath() {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalStateException("Collection is not set. Please select a collection first.");
        }
        File folder = new File(Paths.get(maindir.getPath(), collection).toString());
        return folder.toPath();
    }

    public String getCollection() {
        return collection;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public int getOperation() {
        return operation;
    }

    @Override
    public boolean isExitCommandEntered() {
        return exitCommand;
    }

    public void closeScanner() {
        scanner.close();
    }

    @Override
    public void welcomeMessage() {
        System.out.println("Welcome to the NOTES!\n");
    }

    public File getMaindir() {
        return maindir;
    }
}
