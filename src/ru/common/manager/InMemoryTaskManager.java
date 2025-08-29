package ru.common.manager;

import ru.common.model.Epic;
import ru.common.model.Subtask;
import ru.common.model.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> taskList;
    private HashMap<Integer, Epic> epicList;
    private HashMap<Integer, Subtask> subtaskList;

    private int nextId;

    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        taskList = new HashMap<>();
        epicList = new HashMap<>();
        subtaskList = new HashMap<>();
        this.historyManager = historyManager;

        nextId = 0;
    }

    //Создание задачи
    @Override
    public void addTask(Task task) {
        task.setId(nextId);
        taskList.put(task.getId(), task);
        updateNextId();
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(nextId);
        epicList.put(epic.getId(), epic);
        updateNextId();
    }

    @Override
    public boolean addSubtask(Subtask subtask) {
        Epic epic = epicList.get(subtask.getEpicId());

        if (epic == null) return false;

        subtask.setId(nextId);
        subtaskList.put(subtask.getId(), subtask);
        epic.addSubtaskToEpic(subtask);
        updateEpicStatus(epic);
        updateNextId();
        return true;
    }

    //Обновление задачи
    @Override
    public boolean updateTask(Task updatedTask) {
        Task task = taskList.get(updatedTask.getId());
        if (task == null) {
            return false;
        }
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        return true;
    }

    @Override
    public boolean updateEpic(Epic updatedEpic) {
        Epic existingEpic = epicList.get(updatedEpic.getId());
        if (existingEpic == null) {
            return false;
        }

        existingEpic.setName(updatedEpic.getName());
        existingEpic.setDescription(updatedEpic.getDescription());
        existingEpic.setStatus(updatedEpic.getStatus());

        return true;
    }

    @Override
    public boolean updateSubtask(Subtask updatedSubtask) {
        Subtask existingSubtask = subtaskList.get(updatedSubtask.getId());
        if (existingSubtask == null) {
            return false;
        }

        existingSubtask.setName(updatedSubtask.getName());
        existingSubtask.setDescription(updatedSubtask.getDescription());
        existingSubtask.setStatus(updatedSubtask.getStatus());
        existingSubtask.setEpicId(updatedSubtask.getEpicId());

        Epic epic = epicList.get(existingSubtask.getEpicId());
        if (epic != null) {
            epic.updateSubtaskInEpic(existingSubtask);
            updateEpicStatus(epic);
        }

        return true;
    }

    //Удаление задачи
    @Override
    public boolean deleteTask(int id) {
        if (!taskList.containsKey(id)) {
            return false;
        }
        taskList.remove(id);
        return true;
    }

    @Override
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

    @Override
    public boolean deleteSubtask(int id) {
        if (!subtaskList.containsKey(id)) {
            return false;
        }
        Subtask subtask = subtaskList.get(id);
        Epic epic = epicList.get(subtask.getEpicId());
        epic.removeSubtaskFromEpic(subtask);
        subtaskList.remove(id);
        updateEpicStatus(epic);
        return true;
    }

    //Получение списка всех задач
    @Override
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    @Override
    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    @Override
    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    //Удаление всех задач
    @Override
    public void clearTaskList() {
        taskList.clear();
    }

    @Override
    public void clearEpicList() {
        epicList.clear();
    }

    @Override
    public void clearSubtaskList() {
        subtaskList.clear();
    }

    //Получение по индификатору
    @Override
    public Task getTaskById(int id) {
        historyManager.addToHistory(taskList.get(id));
        return taskList.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        historyManager.addToHistory(epicList.get(id));
        return epicList.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.addToHistory(subtaskList.get(id));
        return subtaskList.get(id);
    }

    //Получение всех подзадач определённого эпика
    @Override
    public ArrayList<Subtask> getEpicSubtasksById(int id) {
        Epic epic = epicList.get(id);
        if (epic == null) {
            return null;
        }
        return epic.getEpicSubtasks();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //Обновление счётчика ID
    private void updateNextId() {
        nextId++;
    }
}