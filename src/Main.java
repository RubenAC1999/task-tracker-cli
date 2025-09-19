import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TaskManager taskManager = new TaskManager();
        String command = args[0];

        switch(command) {
            case "add":
                if (args.length == 0) {
                    System.out.println("Invalid arguments.");
                }
                if (args.length != 2) {
                    System.err.println("Invalid arguments. Usage: add <title>");
                }

                Task task = taskManager.addTask(args[1]);
                System.out.println("Task added successfully: " + task);
                taskManager.writeTasks();
        }
    }
}
