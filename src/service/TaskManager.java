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
        System.out.println("Task deleted successfully.");
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
        writeTasks();
    }

    public void markTaskDone(int id) throws IOException {
        Task task = searchTask(id);

        task.setStatus(Status.DONE);
        task.setUpdatedAt(LocalDateTime.now());
        writeTasks();
    }

    public void listTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        System.out.println("\n────── TASKS ──────\n");
        taskList.forEach(task -> System.out.println(task.toCliFormat()));
    }

    public void listDoneTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        System.out.println("\n────── TASKS ──────\n");
        taskList.stream()
                .filter(task -> task.getStatus().equals(Status.DONE))
                .forEach(task -> System.out.println(task.toCliFormat()));
    }

    public void listInProgressTasks() {
        System.out.println("\n────── TASKS ──────\n");
        taskList.stream()
                .filter(task -> task.getStatus() == Status.IN_PROGRESS)
                .forEach(task -> System.out.println(task.toCliFormat()));
    }

    public void showCommands() {
        System.out.println("Task Tracker CLI it is a simple application to manage your tasks.\n\n");
        System.out.println("List of commands: \n\n");
        System.out.println("add <title>                     Add a new task to the list");
        System.out.println("update <id> <title>             Update a task's description.");
        System.out.println("delete <id>                     Delete a task from the list.");
        System.out.println("mark-in-progress <id>           Update a task's status to In-progress");
        System.out.println("mark-done <id>                  Update a task's status to Done");
        System.out.println("list                            List all the tasks in the list.");
        System.out.println("list done                       List all the done tasks in the list.");
        System.out.println("list in-progress/inprogress     List all the in-progress tasks in the list.");
    }


}
