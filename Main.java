import sprint.NotesTool;

public class Main {
    public static void main(String[] args) {

        NotesTool tool = new NotesTool();

        tool.welcomeMessage();

        tool.selectCollection();
        if (tool.isExitCommandEntered()) {
            tool.closeScanner();
            System.out.println("Goodbye!");
            return;
        }

        while (true) {
            tool.selectOperation();
            if (tool.isExitCommandEntered()) break;

            tool.noteManager();
            if (tool.isExitCommandEntered()) break;

            System.out.println("\n(Type 'exit' to quit or press Enter to continue)");
            String input = tool.getScanner().nextLine();
            if ("exit".equalsIgnoreCase(input.trim())) {
                break;
            }
        }

        System.out.println("Goodbye!");
        tool.closeScanner();
    }
}
