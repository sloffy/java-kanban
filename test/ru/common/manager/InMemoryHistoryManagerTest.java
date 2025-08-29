package ru.common.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void shouldReflectTaskChangesInHistory() {
        Task task = new Task("Task 1", "Description", Status.NEW);
        manager.addTask(task);
        manager.getTaskById(task.getId());

        // меняем задачу
        task.setName("Updated Task");
        task.setStatus(Status.DONE);

        List<Task> history = manager.getHistory();

        assertEquals(1, history.size());
        Task historyTask = history.get(0);

        assertEquals("Updated Task", historyTask.getName(),
                "История должна ссылаться на актуальное имя задачи");
        assertEquals(Status.DONE, historyTask.getStatus(),
                "История должна ссылаться на актуальный статус задачи");
    }
}