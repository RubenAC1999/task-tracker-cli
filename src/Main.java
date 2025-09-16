public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        String command = args[0];

        switch(command) {
            case "add":
                if (args.length != 2) {
                    System.err.println("Invalid arguments. Usage: add <title> <status>");
                }

                Task task = taskManager.addTask(args[1]);
                System.out.println("Task added successfully: " + task);
        }
    }
}
