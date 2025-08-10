package ru.common.model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> epicSubtasks;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
        epicSubtasks = new ArrayList<>();
    }

    public void addSubtaskToEpic(Subtask subtask) {
        epicSubtasks.add(subtask);
    }

    public void updateSubtaskInEpic(Subtask subtask) {
        int index = epicSubtasks.indexOf(subtask);
        epicSubtasks.set(index, subtask);
    }

    public boolean removeSubtaskFromEpic(Subtask subtask) {
        if (!epicSubtasks.contains(subtask)) {
            return false;
        }
        epicSubtasks.remove(subtask);
        return true;
    }

    public ArrayList<Subtask> getEpicSubtasks() {
        return new ArrayList<>(epicSubtasks);
    }



    @Override
    public String toString() {
        String descriptionLength = (getDescription() != null) ? String.valueOf(getDescription().length()) : "null";

        return "Task{" +
                "name='" + getName() + '\'' +
                ", description.length=" + descriptionLength +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", epicSubtasksCount=" + epicSubtasks.size() +
                '}';
    }
}