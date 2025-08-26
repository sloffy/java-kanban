package ru.common.app;

import ru.common.manager.Managers;
import ru.common.manager.TaskManager;
import ru.common.model.Epic;
import ru.common.model.Status;
import ru.common.model.Subtask;
import ru.common.model.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        // Создаём задачи
        Task task1 = new Task("Task 1", "Description for task 1", Status.NEW);
        Task task2 = new Task("Task 2", "Description for task 2", Status.IN_PROGRESS);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Создаём эпик с 2 подзадачами
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask 1", "Subtask 1 description", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask 2 description", Status.NEW, epic1.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        // Создаём эпик с 1 подзадачей
        Epic epic2 = new Epic("Epic 2", "Epic 2 description");
        taskManager.addEpic(epic2);

        Subtask subtask3 = new Subtask("Subtask 3", "Subtask 3 description", Status.NEW, epic2.getId());
        taskManager.addSubtask(subtask3);

        // Печатаем все эпики, задачи, подзадачи
        System.out.println("Epics:");
        taskManager.getEpicList().forEach(System.out::println);

        System.out.println("\nTasks:");
        taskManager.getTaskList().forEach(System.out::println);

        System.out.println("\nSubtasks:");
        taskManager.getSubtaskList().forEach(System.out::println);

        // Изменяем статусы
        task1.setStatus(Status.IN_PROGRESS);
        task2.setStatus(Status.DONE);
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);

        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);

        subtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask2);

        subtask3.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask3);

        // Печатаем снова, чтобы проверить статусы эпиков (обновятся автоматически)
        System.out.println("\nAfter status updates:");

        System.out.println("Epics:");
        taskManager.getEpicList().forEach(System.out::println);

        System.out.println("\nTasks:");
        taskManager.getTaskList().forEach(System.out::println);

        System.out.println("\nSubtasks:");
        taskManager.getSubtaskList().forEach(System.out::println);

        // Удаляем одну задачу и один эпик
        taskManager.deleteTask(task2.getId());
        taskManager.deleteEpic(epic1.getId());

        System.out.println("\nAfter deletions:");

        System.out.println("Epics:");
        taskManager.getEpicList().forEach(System.out::println);

        System.out.println("\nTasks:");
        taskManager.getTaskList().forEach(System.out::println);

        System.out.println("\nSubtasks:");
        taskManager.getSubtaskList().forEach(System.out::println);

        System.out.println("\nHistory test:");

        // Просматриваем несколько задач/эпиков/подзадач
        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic2.getId());
        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getSubtaskById(subtask1.getId());

        // Печатаем историю
        System.out.println("History of viewed tasks:");
        taskManager.getHistory().forEach(System.out::println);
    }
}
