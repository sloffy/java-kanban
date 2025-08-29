package ru.common.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void getDefaultShouldReturnInitializedTaskManager() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Менеджер задач должен быть проинициализирован");
        assertNotNull(manager.getTaskList(), "Список задач должен быть инициализирован");
        assertNotNull(manager.getEpicList(), "Список эпиков должен быть инициализирован");
        assertNotNull(manager.getSubtaskList(), "Список подзадач должен быть инициализирован");
    }

    @Test
    void getDefaultHistoryShouldReturnInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер истории должен быть проинициализирован");
        assertTrue(historyManager.getHistory().isEmpty(), "История должна быть пустой при старте");
    }
}