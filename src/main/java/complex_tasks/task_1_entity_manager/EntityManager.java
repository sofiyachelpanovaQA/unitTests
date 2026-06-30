package complex_tasks.task_1_entity_manager;

import java.util.ArrayList;
import java.util.List;

public class EntityManager<T extends Entity> {

    List<T> entityList = new ArrayList<>();

    public synchronized void addEntity(T entity) {
        entityList.add(entity);
    }

    public synchronized boolean deleteEntity(T entity) {
        return entityList.remove(entity);
    }

    public synchronized List<T> getAll() {
        return List.copyOf(entityList);
    }

    public List<T> filterByAge(int minAge, int maxAge) {
        if (minAge <= maxAge) {
            return entityList.stream()
                    .filter(entity -> (entity.getAge() >= minAge) && (entity.getAge() <= maxAge))
                    .toList();
        }
        throw new IllegalArgumentException("minAge must be <= maxAge");
    }

    public List<T> filterByName(String name) {
        return entityList.stream()
                .filter(entity -> entity.getName().equals(name))
                .toList();
    }

    public List<T> filterByActive(boolean isActive) {
        return entityList.stream()
                .filter(entity -> entity.getActive() == isActive)
                .toList();
    }
}
