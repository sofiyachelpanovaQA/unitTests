package complex_tasks.task_5_inventory_service;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException() {
        super("Товара с указанной категорией нет на складе");
    }
}
