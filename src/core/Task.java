package core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        status = Status.TODO;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Task(int id, String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toCliFormat() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String statusColored = String.valueOf(status);

        switch(statusColored) {
            case "TODO" -> statusColored = "\u001B[31m" + status + "\u001B[0m";
            case "IN_PROGRESS" -> statusColored = "\u001B[33m" + status + "\u001B[0m";
            case "DONE" -> statusColored = "\u001B[32m" + status + "\u001B[0m";
        }


        return String.format(
               "[ID:%-3d]  %-25s  |   %-30s  |   %-19s  |  %-19s" +
                       "\n--------------------------------------------------------------------------------------------------------------------",
                id,
                statusColored,
                description.length() > 30 ? description.substring(0, 27) + "..." : description,
                createdAt.format(formatter),
                updatedAt.format(formatter)
        );
    }

    @Override
    public String toString() {
        return "[" + id+ "] " + description + " - " + status;
    }
}
