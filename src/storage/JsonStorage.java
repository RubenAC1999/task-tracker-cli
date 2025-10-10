package storage;

import core.Status;
import core.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class JsonStorage {
    private static final Path FILE = Path.of("data", "tasks.json");

    public JsonStorage() throws IOException {
        if (!Files.exists(FILE)) {
            Files.writeString(FILE, "[]");
        }
    }

    private static String escapeJson(String string) {
        return string.replace("\\", "\\\\")
                .replace("\"", "");
    }

    public void save(List<Task> taskList) throws IOException {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            sb
                    .append(System.lineSeparator()).append("{")
                    .append(System.lineSeparator()).append("\"id\": ").append(task.getId()).append(",")
                    .append(System.lineSeparator()).append("\"description\": ").append("\"").append(escapeJson(task.getDescription())).append("\"").append(",")
                    .append(System.lineSeparator()).append("\"status\": ").append("\"").append(escapeJson(String.valueOf(task.getStatus()))).append("\"").append(",")
                    .append(System.lineSeparator()).append("\"createdAt\": ").append("\"").append(escapeJson(task.getCreatedAt().toString())).append("\"").append(",")
                    .append(System.lineSeparator()).append("\"updatedAt\": ").append("\"").append(escapeJson(task.getUpdatedAt().toString())).append("\"")
                    .append(System.lineSeparator()).append("}");

            if (i != (taskList.size() - 1)) {
                sb.append(",");
            }

        }

        sb.append(System.lineSeparator()).append("]");

        Files.writeString(FILE, sb.toString());
    }

    public void loadTasks(List<Task> taskList) throws IOException {
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
