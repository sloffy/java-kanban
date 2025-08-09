public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        String descriptionLength = (getDescription() != null) ? String.valueOf(getDescription().length()) : "null";

        return "Task{" +
                "name='" + getName() + '\'' +
                ", description.length=" + descriptionLength +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}