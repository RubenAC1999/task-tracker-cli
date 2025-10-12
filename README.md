# Task Tracker CLI

A simple **command-line task manager** written in **Java** - create, add, update, delete and track your tasks right from your terminal.

![Task Tracker CLI Demo](assets/demo.gif)


---

## Features

- Add and update tasks.

- Mark tasks as **done** or **in-progress**.

- Delete tasks using its ID and with confirmation.

- List all tasks by status (done, todo or in-progress).

- Persistent store using JSON file.

- Clean and readable output.

---

## Tech stack

- **Java 21**

- **Manual JSON persistence** (no libraries)

- **Layered architecture**
  
  -`core`-> Core entities and enums.
  
  -`service`-> Business logic.
  
  -`storage`-> Data persistence.
  
  -`exception`-> Custom exception.

---

## Run the app

### 1. Clone the repository

```bash
git clone https://github.com/RubenAC1999/task-tracker-cli
cd task-tracker-cli
```

### 2. Run using the .jar

```bash
java -jar build/task-tracker-cli.jar <command> [args]
```

---

## Command reference

| Command                 | Description                |
| ----------------------- | -------------------------- |
| `add <title>`           | Add a new task             |
| `update <id> <title>`   | Update a task description  |
| `delete <id>`           | Delete a task              |
| `mark-in-progress <id>` | Mark a task as in progress |
| `mark-done <id>`        | Mark a task as done        |
| `list`                  | List all tasks             |
| `list done`             | List all done tasks        |
| `list in-progress`      | List all in-progress tasks |
| `help`                  | Show all commands          |

---

## Usage examples

```bash
# Add a new task
java -jar build/task-tracker-cli.jar add "Buy groceries"

# Update a task
java -jar build/task-tracker-cli.jar update 1 "Buy bread and milk"

# Mark as in progress
java -jar build/task-tracker-cli.jar mark-in-progress 1

# Mark as done
java -jar build/task-tracker-cli.jar mark-done 1

# Delete a task
java -jar build/task-tracker-cli.jar delete 1

# List all tasks
java -jar build/task-tracker-cli.jar list

# List only done tasks
java -jar build/task-tracker-cli.jar list done

# List only in-progress tasks
java -jar build/task-tracker-cli.jar list in-progress
```

---

## License

This project is released under the [MIT License.](https://opensource.org/license/mit)



    
