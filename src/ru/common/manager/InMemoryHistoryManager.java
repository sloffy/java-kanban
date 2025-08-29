package ru.common.manager;

import ru.common.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager  implements HistoryManager{

    private List<Task> historyList;
    private final int MAX_AVAILABLE_SIZE_OF_HISTORY_LIST = 10;
    private final int INDEX_OF_THE_FIRST_ELEMENT = 0;

    public InMemoryHistoryManager() {
        historyList = new ArrayList<>();
    }

    @Override
    public List<Task> getHistory(){
        return new ArrayList<>(historyList);
    }

    public boolean addToHistory(Task task){
        if (task == null) return false;

        historyList.add(task);
        if (historyList.size() > MAX_AVAILABLE_SIZE_OF_HISTORY_LIST) {
            historyList.remove(INDEX_OF_THE_FIRST_ELEMENT);
        }
        return true;
    }
}
