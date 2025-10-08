package exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(int id) {
        System.err.println("Error: " + id + " not found in the list.");
    }
}
