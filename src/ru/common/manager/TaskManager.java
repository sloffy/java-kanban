package ru.common.manager;

import ru.common.model.Epic;
import ru.common.model.Status;
import ru.common.model.Subtask;
import ru.common.model.Task;

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
        updateNextId();
    }

    public void addEpic(Epic epic) {
        epic.setId(nextId);
        epicList.put(epic.getId(), epic);
        updateNextId();
    }

    public boolean addSubtask(Subtask subtask) {
        Epic epic = epicList.get(subtask.getEpicId());
        if (epic == null) {
            return false;
        }
        subtask.setId(nextId);
        subtaskList.put(subtask.getId(), subtask);
        epic.addSubtaskToEpic(subtask);
        updateEpicStatus(epic);
        updateNextId();
        return true;
    }

    //Обновление задачи
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
        updateEpicStatus(epic);
        return true;
    }

    //Получение списка всех задач
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
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

    //Обновление счётчика ID
    public void updateNextId() {
        nextId++;
    }

    //Обновление статуса Эпика
    private void updateEpicStatus(Epic epic) {
        if (epic.getEpicSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask st : epic.getEpicSubtasks()) {
            if (st.getStatus() != Status.NEW) {
                allNew = false;
            }
            if (st.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

}