package complex_tasks.task_1_entity_manager;

public class Entity {
    private int age;
    private String name;
    boolean isActive;

    Entity(int age, String name, boolean isActive) {
        this.age = age;
        this.name = name;
        this.isActive = isActive;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
