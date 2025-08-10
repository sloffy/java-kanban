package ru.common.app;

import ru.common.manager.TaskManager;
import ru.common.model.Epic;
import ru.common.model.Status;
import ru.common.model.Subtask;
import ru.common.model.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Создаём задачи
        Task task1 = new Task("Task 1", "Description for task 1", Status.NEW);
        Task task2 = new Task("Task 2", "Description for task 2", Status.IN_PROGRESS);
        manager.addTask(task1);
        manager.addTask(task2);

        // Создаём эпик с 2 подзадачами
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask 1", "Subtask 1 description", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask 2 description", Status.NEW, epic1.getId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        // Создаём эпик с 1 подзадачей
        Epic epic2 = new Epic("Epic 2", "Epic 2 description");
        manager.addEpic(epic2);

        Subtask subtask3 = new Subtask("Subtask 3", "Subtask 3 description", Status.NEW, epic2.getId());
        manager.addSubtask(subtask3);

        // Печатаем все эпики, задачи, подзадачи
        System.out.println("Epics:");
        manager.getEpicList().forEach(System.out::println);

        System.out.println("\nTasks:");
        manager.getTaskList().forEach(System.out::println);

        System.out.println("\nSubtasks:");
        manager.getSubtaskList().forEach(System.out::println);

        // Изменяем статусы
        task1.setStatus(Status.IN_PROGRESS);
        task2.setStatus(Status.DONE);
        manager.updateTask(task1);
        manager.updateTask(task2);

        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);

        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);

        subtask3.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask3);

        // Печатаем снова, чтобы проверить статусы эпиков (обновятся автоматически)
        System.out.println("\nAfter status updates:");

        System.out.println("Epics:");
        manager.getEpicList().forEach(System.out::println);

        System.out.println("\nTasks:");
        manager.getTaskList().forEach(System.out::println);

        System.out.println("\nSubtasks:");
        manager.getSubtaskList().forEach(System.out::println);

        // Удаляем одну задачу и один эпик
        manager.deleteTask(task2.getId());
        manager.deleteEpic(epic1.getId());

        System.out.println("\nAfter deletions:");

        System.out.println("Epics:");
        manager.getEpicList().forEach(System.out::println);

        System.out.println("\nTasks:");
        manager.getTaskList().forEach(System.out::println);

        System.out.println("\nSubtasks:");
        manager.getSubtaskList().forEach(System.out::println);
    }
}
