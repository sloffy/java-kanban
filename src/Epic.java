import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Subtask> epicSubtasks;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
        epicSubtasks = new ArrayList<>();
    }

    public void addSubtaskToEpic(Subtask subtask) {
        epicSubtasks.add(subtask);
        updateEpicStatus();
    }

    public void updateSubtaskInEpic(Subtask subtask) {
        int index = epicSubtasks.indexOf(subtask);
        epicSubtasks.set(index, subtask);
        updateEpicStatus();
    }

    public boolean removeSubtaskFromEpic(Subtask subtask) {
        if (!epicSubtasks.contains(subtask)) {
            return false;
        }
        epicSubtasks.remove(subtask);
        updateEpicStatus();
        return true;
    }

    public ArrayList<Subtask> getEpicSubtasks() {
        return new ArrayList<>(epicSubtasks);
    }

    private void updateEpicStatus() {
        if (epicSubtasks.isEmpty()) {
            setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask st : epicSubtasks) {
            if (st.getStatus() != Status.NEW) {
                allNew = false;
            }
            if (st.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            setStatus(Status.NEW);
        } else if (allDone) {
            setStatus(Status.DONE);
        } else {
            setStatus(Status.IN_PROGRESS);
        }
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