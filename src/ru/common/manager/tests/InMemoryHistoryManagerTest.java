package ru.common.manager.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.common.manager.TaskManager;
import ru.common.manager.Managers;
import ru.common.model.Status;
import ru.common.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
    }

    @Test
    void shouldPreserveTaskStateInHistory() {
        Task task = new Task("Task 1", "Description", Status.NEW);
        manager.addTask(task);
        manager.getTaskById(task.getId());

        task.setName("Updated Task");
        task.setStatus(Status.DONE);

        List<Task> history = manager.getHistory();

        assertEquals(1, history.size());
        Task historyTask = history.get(0);
        
        assertEquals("Task 1", historyTask.getName(),
                "История должна сохранять прежнее имя задачи");
        assertEquals(Status.NEW, historyTask.getStatus(),
                "История должна сохранять прежний статус задачи");
    }
}