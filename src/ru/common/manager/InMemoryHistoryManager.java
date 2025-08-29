package ru.common.manager;

import ru.common.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager  implements HistoryManager{

    private List<Task> historyList;

    public InMemoryHistoryManager() {
        historyList = new ArrayList<>();
    }

    @Override
    public List<Task> getHistory(){
        return new ArrayList<>(historyList);
    }

    public boolean addToHistory(Task task){
        if (task == null) return false;
        Task taskCopy = new Task(task.getName(), task.getDescription(), task.getStatus());
        taskCopy.setId(task.getId());
        historyList.add(taskCopy);
        if (historyList.size() > 10) {
            historyList.remove(0);
        }
        return true;
    }
}
