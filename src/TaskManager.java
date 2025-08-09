import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private HashMap<Integer, Task> taskList;
    private HashMap<Integer, Epic> epicList;
    private HashMap<Integer, Subtask> subtaskList;

    private int nextId;

    public TaskManager() {
        taskList = new HashMap<>();
        epicList = new HashMap<>();
        subtaskList = new HashMap<>();

        nextId = 0;
    }

    //Создание задачи
    public void addTask(Task task) {
        task.setId(nextId);
        taskList.put(task.getId(), task);
        nextId++;
    }

    public void addEpic(Epic epic) {
        epic.setId(nextId);
        epicList.put(epic.getId(), epic);
        nextId++;
    }

    public boolean addSubtask(Subtask subtask) {
        Epic epic = epicList.get(subtask.getEpicId());
        if (epic == null) {
            return false;
        }
        subtask.setId(nextId);
        subtaskList.put(subtask.getId(), subtask);
        epic.addSubtaskToEpic(subtask);
        nextId++;
        return true;
    }

    //Обновление задачи
    public boolean updateTask(Task task, int id) {
        if (!taskList.containsKey(id)) {
            return false;
        }
        task.setId(id);
        taskList.replace(task.getId(), task);
        return true;
    }

    public boolean updateEpic(Epic epic, int id) {
        if (!epicList.containsKey(id)) {
            return false;
        }
        epic.setId(id);
        epicList.replace(epic.getId(), epic);
        return true;
    }

    public boolean updateSubtask(Subtask subtask, int id) {
        if (!subtaskList.containsKey(id)) {
            return false;
        }
        subtask.setId(id);
        subtaskList.replace(subtask.getId(), subtask);
        Epic epic = epicList.get(subtask.getEpicId());
        epic.updateSubtaskInEpic(subtask);
        return true;
    }

    //Удаление задачи
    public boolean deleteTask(int id) {
        if (!taskList.containsKey(id)) {
            return false;
        }
        taskList.remove(id);
        return true;
    }

    public boolean deleteEpic(int id) {
        if (!epicList.containsKey(id)) {
            return false;
        }
        for (Integer subtaskId : new ArrayList<>(subtaskList.keySet())) {
            Subtask subtask = subtaskList.get(subtaskId);
            if (subtask.getEpicId() == id) {
                subtaskList.remove(subtask.getId());
            }
        }
        epicList.remove(id);
        return true;
    }

    public boolean deleteSubtask(int id) {
        if (!subtaskList.containsKey(id)) {
            return false;
        }
        Subtask subtask = subtaskList.get(id);
        Epic epic = epicList.get(subtask.getEpicId());
        epic.removeSubtaskFromEpic(subtask);
        subtaskList.remove(id);
        return true;
    }

    //Получение списка всех задач
    public HashMap<Integer, Task> getTaskList() {
        return new HashMap<>(taskList);
    }

    public HashMap<Integer, Epic> getEpicList() {
        return new HashMap<>(epicList);
    }

    public HashMap<Integer, Subtask> getSubtaskList() {
        return new HashMap<>(subtaskList);
    }

    //Удаление всех задач
    public void clearTaskList() {
        taskList.clear();
    }

    public void clearEpicList() {
        epicList.clear();
    }

    public void clearSubtaskList() {
        subtaskList.clear();
    }

    //Получение по индификатору
    public Task getTaskById(int id) {
        return taskList.get(id);
    }

    public Epic getEpicById(int id) {
        return epicList.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtaskList.get(id);
    }

    //Получение всех подзадач определённого эпика
    public ArrayList<Subtask> getEpicSubtasksById(int id) {
        Epic epic = epicList.get(id);
        if (epic == null) {
            return null;
        }
        return epic.getEpicSubtasks();
    }
}