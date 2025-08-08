import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> taskList;
    private int currentIdAvailable;

    public TaskManager() {
        taskList = new HashMap<>();
        currentIdAvailable = 1;
    }

    //Получение списка всех задач
    public HashMap<Integer, Task> getTaskList() {
        return  taskList;
    }

    //Удаление всех задач
    public void clearTaskList() {
        taskList.clear();
        currentIdAvailable = 1;
    }

    //Создание задачи
    public void addTask(Task task) {
        task.setId(currentIdAvailable);
        taskList.put(task.getId(), task);
        currentIdAvailable++;
    }

    //Обновление задачи
    public boolean updateTask(Task task, int id) {
        if (!taskList.containsKey(id)) {
            return false;
        }
        taskList.replace(id, task);
        return true;
    }

    //Удаление задачи
    public void deleteTask(int id) {
        taskList.remove(id);
    }
}
