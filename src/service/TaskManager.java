package service;

import core.Status;
import core.Task;
import exception.TaskNotFoundException;
import storage.JsonStorage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> taskList;
    private final JsonStorage storage = new JsonStorage();
    private int lastId;

    public TaskManager() throws IOException {
        taskList = new ArrayList<>();
        loadTasks();
    }

    public Task addTask(String description) throws IOException {
        Task newTask = new Task(++lastId, description);
        taskList.add(newTask);
        writeTasks();
        return newTask;
    }

    public void writeTasks() throws IOException {
        storage.save(taskList);
    }

    public void loadTasks() throws IOException {
        storage.loadTasks(taskList);
        lastId = taskList.stream().mapToInt(Task::getId).max().orElse(0);
    }

    public Task searchTask(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }


    public void deleteTask(Task task) throws IOException {
        taskList.remove(task);
        System.out.println("core.Task deleted successfully.");

        writeTasks();
    }

    public Task updateTask(int id, String newDescription) throws IOException {
        Task taskUpdated = searchTask(id);
        taskUpdated.setDescription(newDescription);
        taskUpdated.setUpdatedAt(LocalDateTime.now());
        writeTasks();
        return taskUpdated;
    }

    public void markTaskInProgress(int id) throws IOException {
        Task task = searchTask(id);

        task.setStatus(Status.IN_PROGRESS);
        task.setUpdatedAt(LocalDateTime.now());

        System.out.println("core.Task updated successfully.");

        writeTasks();
    }

    public void markTaskDone(int id) throws IOException {
        Task task = searchTask(id);

        task.setStatus(Status.DONE);
        task.setUpdatedAt(LocalDateTime.now());

        System.out.println("core.Task updated successfully.");

        writeTasks();
    }

    public void listTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        System.out.println("\n────── TASKS ──────\n");
        taskList.stream().forEach(task -> System.out.println(task.toCliFormat()));
    }

    public void listDoneTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        System.out.println("-----TASKS------");
        taskList.stream().forEach(task -> System.out.println(task.toCliFormat()));
    }

    public void listInProgressTasks() {
        taskList.stream().filter(task -> task.getStatus() == Status.IN_PROGRESS).forEach(System.out::println);
    }

    public void showCommands() {
        System.out.println("add <title>");
        System.out.println("update <id> <title>");
        System.out.println("delete <id>");
        System.out.println("mark-in-progress <id>");
        System.out.println("mark-done <id>");
        System.out.println("list");
        System.out.println("list done");
        System.out.println("list in-progress");
    }


}
