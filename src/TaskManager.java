import java.util.List;

public class TaskManager {
    private List<Task> taskList;
    private int lastId = 0;

    public List<Task> getTaskList() {
        return taskList;
    }

    public Task addTask(String description) {
        Task newTask = new Task(++lastId, description);
        taskList.add(newTask);
        return newTask;
    }



}
