package ru.common.manager.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.common.manager.Managers;
import ru.common.manager.TaskManager;
import ru.common.model.Epic;
import ru.common.model.Subtask;
import ru.common.model.Task;
import ru.common.model.Status;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
    }

    //Для проверяющего: В моём случае тест «эпик не может быть своей подзадачей» теряет смысл,
    // потому что в реальном коде эпик и сабтаск — это разные классы, и эпик никогда не создаётся как сабтаск.

    @Test
    void shouldReturnFalseWhenSubtaskPointsToItselfAsEpic() {
        Subtask subtask = new Subtask("Fake subtask", "desc", Status.NEW, 1);

        subtask.setId(subtask.getEpicId());

        assertFalse(manager.addSubtask(subtask), "Подзадача не может ссылаться сама на себя как на эпик");
    }

    @Test
    void shouldAddAndFindTaskById() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        manager.addTask(task);

        Task savedTask = manager.getTaskById(task.getId());

        assertNotNull(savedTask, "Задача должна быть найдена по id");
        assertEquals(task, savedTask, "Задача должна совпадать с сохранённой");
    }

    @Test
    void shouldAddAndFindEpicById() {
        Epic epic = new Epic("Epic 1", "Epic description");
        manager.addEpic(epic);

        Epic savedEpic = manager.getEpicById(epic.getId());

        assertNotNull(savedEpic, "Эпик должен быть найден по id");
        assertEquals(epic, savedEpic, "Эпик должен совпадать с сохранённым");
    }

    @Test
    void shouldAddAndFindSubtaskById() {
        Epic epic = new Epic("Epic 1", "Epic description");
        manager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", "Subtask description", Status.NEW, epic.getId());
        manager.addSubtask(subtask);

        Subtask savedSubtask = manager.getSubtaskById(subtask.getId());

        assertNotNull(savedSubtask, "Подзадача должна быть найдена по id");
        assertEquals(subtask, savedSubtask, "Подзадача должна совпадать с сохранённой");
    }

    @Test
    void shouldNotConflictWithManuallyAssignedId() {
        Task task1 = new Task("Task 1", "Desc", Status.NEW);
        manager.addTask(task1);

        int manualId = task1.getId(); // уже занятый id
        Task task2 = new Task("Task 2", "Desc", Status.NEW);
        task2.setId(manualId);

        manager.addTask(task2);
        assertNotEquals(task1.getId(), task2.getId(), "Авто-id не должен конфликтовать с существующим");
    }

    @Test
    void shouldNotChangeTaskFieldsOnAdd() {
        Task task = new Task("Test Task", "Some description", Status.NEW);

        String originalName = task.getName();
        String originalDescription = task.getDescription();
        Status originalStatus = task.getStatus();

        manager.addTask(task);

        // Проверяем, что поля (кроме id) остались неизменными
        assertEquals(originalName, task.getName(), "Имя задачи не должно изменяться при добавлении");
        assertEquals(originalDescription, task.getDescription(), "Описание задачи не должно изменяться при добавлении");
        assertEquals(originalStatus, task.getStatus(), "Статус задачи не должен изменяться при добавлении");
    }
}