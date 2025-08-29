package ru.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    private Epic epic1;
    private Epic epic2;

    @BeforeEach
    void setUp() {
        epic1 = new Epic("Task 1", "Description 1");
        epic2 = new Epic("Task 2", "Description 2");
    }

    @Test
    void epicsAreEqualsIfIdsAreEquals() {
        epic1.setId(1);
        epic2.setId(1);

        assertEquals(epic1, epic2, "Задачи с одинаковыми Id считаются равными");
    }
}