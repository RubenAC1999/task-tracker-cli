import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static volatile TaskManager instance;
    private final List<Task> taskList;
    private int lastId;
    private final Path FILE = Path.of("tasks.json");

    private TaskManager() throws IOException {
        taskList = new ArrayList<>();
        if(!Files.exists(FILE)) {
            Files.writeString(FILE, "[]");

        } else {
            loadTasks();
        }
    }

    public static TaskManager getInstance() throws IOException {
        if (instance == null) {
            synchronized(TaskManager.class) {
                if (instance == null) {
                    instance = new TaskManager();
                }
            }
        }

        return instance;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public Task addTask(String description) {
        Task newTask = new Task(++lastId, description);
        taskList.add(newTask);
        return newTask;
    }

    public void writeTasks() throws IOException {
        StringBuilder taskString = new StringBuilder("[");

        for (Task task : taskList) {
            taskString
                    .append(System.lineSeparator()).append("{")
                    .append(System.lineSeparator()).append("\"id\": ").append(task.getId()).append(",")
                    .append(System.lineSeparator()).append("\"description\": ").append("\"").append(escapeJson(task.getDescription())).append("\"").append(",")
                    .append(System.lineSeparator()).append("\"status\": ").append("\"").append(escapeJson(String.valueOf(task.getStatus()))).append("\"").append(",")
                    .append(System.lineSeparator()).append("\"createdAt\": ").append("\"").append(escapeJson(task.getCreatedAt().toString())).append("\"").append(",")
                    .append(System.lineSeparator()).append("\"updatedAt\": ").append("\"").append(escapeJson(task.getUpdatedAt().toString())).append("\"")
                    .append(System.lineSeparator()).append("}");

            if(!task.equals(taskList.getLast())) {
               taskString.append(",");
            }

        }

        taskString.append(System.lineSeparator()).append("]");

        Files.writeString(FILE, taskString);
    }

    private static String escapeJson(String string) {
        return string.replace("\\","\\\\")
                .replace("\"" , "");
    }

    public void loadTasks() throws IOException {
        List<String> lines = Files.readAllLines(FILE);
        int id = 0;
        String description = "";
        Status status = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        for (String line : lines) {
            int position = line.indexOf(":") + 2;
            String valor = "";

            if (position != 1) {
                valor = line.substring(position, (line.length() - 2));
            }

           if (line.startsWith("\"id\"")) {
               id = Integer.parseInt(line.substring(position, line.length() - 1));
           }

           if (line.startsWith("\"description\"")) {
               description = escapeJson(valor);
           }

           if (line.startsWith("\"status\"")) {
               status = Status.valueOf(escapeJson(valor));
           }

           if (line.startsWith("\"createdAt\"")) {
               createdAt = LocalDateTime.parse(escapeJson(valor));
           }

           if (line.startsWith("\"updatedAt\"")) {
               updatedAt = LocalDateTime.parse(escapeJson(line.substring(position, (line.length() - 1))));
               taskList.add(new Task(id, description, status, createdAt, updatedAt));
           }
        }
    }


}
