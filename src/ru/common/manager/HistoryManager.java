package ru.common.manager;

import ru.common.model.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    boolean addToHistory(Task task);

}
