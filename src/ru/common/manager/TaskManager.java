package ru.common.manager;

import ru.common.model.Epic;
import ru.common.model.Status;
import ru.common.model.Subtask;
import ru.common.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    //Создание задачи
    void addTask(Task task);

    void addEpic(Epic epic);

    boolean addSubtask(Subtask subtask);

    //Обновление задачи
    boolean updateTask(Task updatedTask);

    boolean updateEpic(Epic updatedEpic);

    boolean updateSubtask(Subtask updatedSubtask);

    //Удаление задачи
    boolean deleteTask(int id);

    boolean deleteEpic(int id);

    boolean deleteSubtask(int id);

    //Получение списка всех задач
    ArrayList<Task> getTaskList();

    ArrayList<Epic> getEpicList();

    ArrayList<Subtask> getSubtaskList();

    //Удаление всех задач
    void clearTaskList();

    void clearEpicList();

    void clearSubtaskList();

    //Получение по индификатору
    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    ArrayList<Subtask> getEpicSubtasksById(int id);

    List<Task> getHistory();

    //Обновление статуса Эпика
    default void updateEpicStatus(Epic epic) {
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
