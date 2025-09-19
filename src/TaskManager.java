import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> taskList;
    private int lastId = 0;
    private final Path FILE = Path.of("tasks.json");

    public TaskManager() throws IOException {
        taskList = new ArrayList<>();
        if(!Files.exists(FILE)) {
            Files.createFile(FILE);
        }
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
        StringBuilder sb = new StringBuilder();
        StringBuilder taskString = new StringBuilder("{");

        for (Task task : taskList) {
            taskString
                    .append(System.lineSeparator()).append("\"").append("id ").append("\"").append(":").append("\"").append(task.getId()).append("\",")
                    .append(System.lineSeparator()).append("\"").append("descrption ").append("\"").append(":").append("\"").append(task.getDescription()).append("\",")
                    .append(System.lineSeparator()).append("\"").append("status ").append("\"").append(":").append("\"").append(task.getStatus()).append("\",")
                    .append(System.lineSeparator()).append("\"").append("createdAt ").append("\"").append(":").append("\"").append(task.getCreatedAt()).append("\",")
                    .append(System.lineSeparator()).append("\"").append("updatedAt ").append("\"").append(":").append("\"").append(task.getUpdatedAt()).append("\"")
                    .append(System.lineSeparator()).append("}");

            if(!task.equals(taskList.getLast())) {
               taskString.append(",");
            }

        }

        Files.writeString(FILE, taskString);
    }



}
