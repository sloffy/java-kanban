package ru.common.model.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.common.model.Status;
import ru.common.model.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task("Task 1", "Description 1", Status.NEW);
        task2 = new Task("Task 2", "Description 2", Status.DONE);
    }

    @Test
    void tasksAreEqualsIfIdsAreEquals() {
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковыми Id считаются равными");
    }
}