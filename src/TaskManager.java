import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static volatile TaskManager instance;
    private final List<Task> taskList;
    private int lastId = 0;
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
                    .append(System.lineSeparator()).append("\"").append("id").append("\"").append(":").append("\"").append(task.getId()).append("\",")
                    .append(System.lineSeparator()).append("\"").append("description").append("\"").append(":").append("\"").append(task.getDescription()).append("\",")
                    .append(System.lineSeparator()).append("\"").append("status").append("\"").append(":").append("\"").append(task.getStatus()).append("\",")
                    .append(System.lineSeparator()).append("\"").append("createdAt").append("\"").append(":").append("\"").append(task.getCreatedAt()).append("\",")
                    .append(System.lineSeparator()).append("\"").append("updatedAt").append("\"").append(":").append("\"").append(task.getUpdatedAt()).append("\"")
                    .append(System.lineSeparator()).append("}");

            if(!task.equals(taskList.getLast())) {
               taskString.append(",");
            }

        }

        taskString.append(System.lineSeparator()).append("]");

        Files.writeString(FILE, taskString);
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

           if (line.contains("\"id\"")) {
               id = Integer.parseInt(valor);
           }

           if (line.contains("\"description\"")) {
               description = valor;
           }

           if (line.contains("\"status\"")) {
               status = Status.valueOf(valor);
           }

           if (line.contains("\"createdAt\"")) {
               createdAt = LocalDateTime.parse(valor);
           }

           if (line.contains("\"updatedAt\"")) {
               updatedAt = LocalDateTime.parse(line.substring(position, line.length() - 1));
               taskList.add(new Task(id, description, status, createdAt, updatedAt));
           }
        }
    }


}
