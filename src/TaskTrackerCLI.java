import core.Task;
import service.TaskManager;

import java.io.IOException;
import java.util.Scanner;

public class TaskTrackerCLI {
    public static void main(String[] args) throws IOException {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        String command = args[0];
        Task task;

        switch(command) {
            case "add":
                if (args.length != 2) {
                    System.err.println("Invalid arguments. Usage: add <title>");
                }

                task = taskManager.addTask(args[1]);
                System.out.println("core.Task added successfully: " + task);
                break;

            case "update":
                if (args.length != 3) {
                    System.err.println("Invalid arguments. Usage: update <id> <description>");
                }

                task = taskManager.updateTask(Integer.parseInt(args[1]), args[2]);
                System.out.println("core.Task updated successfully: " + task);
                break;

            case "delete":
                if (args.length != 2) {
                    System.err.println("Invalid arguments. Usage: delete <id>");
                }

                task = taskManager.searchTask(Integer.parseInt(args[1]));

                if(task != null) {
                    System.out.println("Are you sure you want to delete this task? (y/n)");
                    System.out.println(task);
                    String response = scanner.nextLine();

                    if (response.equalsIgnoreCase("y")) {
                        taskManager.deleteTask(task);
                    }
                }

                break;

            case "mark-in-progress":
                if (args.length != 2) {
                    System.err.println("Invalid arguments. Usage: mark-in-progress <id>");
                }

                taskManager.markTaskInProgress(Integer.parseInt(args[1]));

                break;

            case "mark-done":
                if (args.length != 2) {
                    System.err.println("Invalid arguments. Usage: mark-done <id>");
                }

                taskManager.markTaskDone(Integer.parseInt(args[1]));

                break;

            case "list":
                    if (args.length == 1) {
                        taskManager.listTasks();
                        break;
                    }

                    if (args[1].equals("done")) {
                        taskManager.listDoneTasks();
                        break;
                    }

                    if (args[1].equals("in-progress")) {
                        taskManager.listInProgressTasks();
                        break;
                    }

                    break;

            case "help":
                taskManager.showCommands();
                break;

            default:
                System.out.println("Command not found. Type 'help' for available commands.");
        }
    }
}
