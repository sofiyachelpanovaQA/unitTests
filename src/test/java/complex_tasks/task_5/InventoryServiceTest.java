package complex_tasks.task_5;

import complex_tasks.task_5_inventory_service.InventoryService;
import complex_tasks.task_5_inventory_service.OutOfStockException;
import complex_tasks.task_5_inventory_service.Product;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InventoryServiceTest {
    /* Проверки метода добавления товара на склад
    * 1. Добавление первого товара на складе с новой категорией
    *   -> создана новая запись с новым ключом, товар есть в списке, размер списка товаров увеличился на 1
    * 2. Добавление n-го товара по категории
    *   -> товар есть в списке, размер списка товаров - n + 1, новой записи не создано (размер productListMap не изменился)
    * 3. Добавление категорий в разном регистре
    *   -> не создано новых записей (размер productListMap не изменился), оба товара в списке
    * 4. Добавление нескольких товаров разных категорий
    *   -> создано несколько записей, товары есть в категориях
    * 5. Добавление товара при isInventoryOpen = false
    *   -> IllegalStateException, товар не добавлен
     */

    @Test
    @DisplayName("Добавление первого товара на складе с новой категорией")
    public void addFirstProductInCategory() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "Food");

        assertTrue(inventoryService.getProductListMap().isEmpty()); // проверили что productListMap пуст

        inventoryService.addProduct(banana);
        assertTrue(inventoryService.getProductListMap().containsKey(banana.getCategory().toLowerCase())); // проверили что категория появилась
        assertEquals(banana, inventoryService.getProductListMap().get(banana.getCategory().toLowerCase()).getFirst()); // проверили что товар есть
    }

    @Test
    @DisplayName("Добавление не первого товара по категории")
    public void addNotFirstProductInCategory() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "Food");
        Product milk = new Product("Milk", 80.0, "Food");

        inventoryService.addProduct(banana);
        int sizeMapBefore = inventoryService.getProductListMap().size(); // запомнили размер productListMap
        int sizeProductListBefore = inventoryService.getProductListMap().get("food").size(); // запомнили размер списка продуктов категории food
        inventoryService.addProduct(milk); // добавили второй продукт

        assertEquals(sizeMapBefore, inventoryService.getProductListMap().size()); // проверили что новой записи не создано (размер productListMap не изменился)
        assertEquals(sizeProductListBefore + 1, inventoryService.getProductListMap().get("food").size()); // проверили что размер списка товаров - n + 1
        assertEquals(milk, inventoryService.getProductListMap().get("food").getLast()); // проверили что товар есть в списке
    }

    @Test
    @DisplayName("Добавление категорий в разном регистре")
    public void addProductWithCategoryInOtherRegister() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "Food");
        Product milk = new Product("Milk", 80.0, "food");

        inventoryService.addProduct(banana);
        int sizeMapBefore = inventoryService.getProductListMap().size(); // запомнили размер productListMap
        inventoryService.addProduct(milk); // добавили второй c той же категорией но в другом регистре

        assertEquals(sizeMapBefore, inventoryService.getProductListMap().size()); // проверили что новой записи не создано (размер productListMap не изменился)
        assertTrue(inventoryService.getProductListMap().get("food").containsAll(List.of(milk, banana))); // проверили что товар есть в списке
    }

    @Test
    @DisplayName("Добавление нескольких товаров разных категорий")
    public void addProductInSeveralCategories() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "food");
        Product milk = new Product("Milk", 80.0, "food");
        Product iphone = new Product("iPhone", 40000.0, "electronics");
        Product tv = new Product("TV", 20000.0, "electronics");

        inventoryService.addProduct(banana);
        inventoryService.addProduct(iphone);
        inventoryService.addProduct(milk);
        inventoryService.addProduct(tv); // добавили продукты разных категорий

        assertEquals(2, inventoryService.getProductListMap().size()); // проверили что появилось 2 новые записи
        assertTrue(inventoryService.getProductListMap().get("food").containsAll(List.of(milk, banana))); // проверили что товары категории food есть в списке
        assertTrue(inventoryService.getProductListMap().get("electronics").containsAll(List.of(tv, iphone))); // проверили что товары категории electronics есть в списке
    }

    @Test
    @DisplayName("Добавление товара при isInventoryOpen = false")
    public void addProductIfInventoryIsNotOpen() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "Food");

        inventoryService.setInventoryOpen(false);
        assertThrows(IllegalStateException.class, () -> inventoryService.addProduct(banana));
    }

    /* Проверки метода получения товара по категории
     * 1. Получение товара из существующей категории.
     *   -> метод возвращает товар, товара больше нет на складе, размер списка товаров уменьшился на 1
     * 2. Поиск по категории не зависит от регистра
     *   -> метод работает для одного и того же списка продуктов
     * 3. Получение товара в пустой категории
     *   -> OutOfStockException
     * 4. Получение товара по несуществующей категории
     *   -> IllegalArgumentException + текст "Данной категории еще не существует, необходимо ее добавить"
     * 5. Получение последнего товара
     *   -> категория пустая
     */

    @Test
    @DisplayName("Получение товара из существующей категории")
    public void getProductIfCategoryExists() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "food");
        Product milk = new Product("Milk", 80.0, "food");
        Product iphone = new Product("iPhone", 80.0, "electronics");
        Product tv = new Product("TV", 80.0, "electronics");

        inventoryService.addProduct(banana);
        inventoryService.addProduct(iphone);
        inventoryService.addProduct(milk);
        inventoryService.addProduct(tv); // добавили продукты разных категорий

        int sizeProductListBefore = inventoryService.getProductListMap().get("food").size(); // запомнили размер списка продуктов категории food
        Product takenProduct = inventoryService.getProductByCategory("food"); // забрали продукт и запомнили
        assertEquals(takenProduct, milk); // проверили что метод вернул товар
        assertEquals(sizeProductListBefore - 1, inventoryService.getProductListMap().get("food").size()); // проверили что размер уменьшился на 1
        assertFalse(inventoryService.getProductListMap().get("food").contains(takenProduct)); // проверили что товара больше нет в списке

    }

    @Test
    @DisplayName("Поиск по категории не зависит от регистра")
    public void findingProductIgnoreCase() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "food");
        Product milk = new Product("Milk", 80.0, "food");

        inventoryService.addProduct(banana);
        inventoryService.addProduct(milk);

        Product takenProduct = inventoryService.getProductByCategory("Food"); // забрали продукт по категории в другом регистре и запомнили
        assertEquals(takenProduct, milk); // проверили что метод вернул товар
        assertFalse(inventoryService.getProductListMap().get("food").contains(takenProduct)); // проверили что товара больше нет в списке

    }

    @Test
    @DisplayName("Получение товара в пустой категории")
    public void getProductInEmptyCategory() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "food");
        Product milk = new Product("Milk", 80.0, "food");

        inventoryService.addProduct(banana);
        inventoryService.getProductByCategory("food"); // сделали категорию пустой

        Exception exception = assertThrows(OutOfStockException.class, () -> inventoryService.getProductByCategory("food")); // проверили исключение
        assertEquals("Товара с указанной категорией нет на складе", exception.getMessage()); // проверили текст ошибки
    }

    @Test
    @DisplayName("Получение товара по несуществующей категории")
    public void getProductByNonexistCategory() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "food");
        Product milk = new Product("Milk", 80.0, "food");

        inventoryService.addProduct(banana);
        inventoryService.addProduct(milk);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> inventoryService.getProductByCategory("electronics")); // сделали поиск по несуществующей категории и проверили исключение
        assertEquals("Данной категории еще не существует, необходимо ее добавить", exception.getMessage()); // проверили текст ошибки
    }

    @Test
    @DisplayName("Получение последнего товара")
    public void getLastProductInCategory() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "food");

        inventoryService.addProduct(banana);
        inventoryService.getProductByCategory("food"); // забрали последний товар

        assertTrue(inventoryService.getProductListMap().get("food").isEmpty()); //проверили что список товаров пустой
    }

    // проверка метода поиска товаров по категории
    @Test
    @DisplayName("Проверка метода поиска товаров по категории")
    public void getProductsByCategory() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "food");
        Product milk = new Product("Milk", 80.0, "food");
        Product iphone = new Product("iPhone", 80.0, "electronics");
        Product tv = new Product("TV", 80.0, "electronics");

        List.of(banana, milk, iphone, tv).forEach(inventoryService::addProduct);

        assertEquals(inventoryService.findProductsByCategory("Food"), List.of(banana, milk));
    }

    // проверка метода сортировки по цене
    @Test
    @DisplayName("Проверка метода сортировки по цене")
    public void filterByPrice() {
        InventoryService inventoryService = new InventoryService();
        Product banana = new Product("Banana", 40.0, "food");
        Product milk = new Product("Milk", 80.0, "food");
        Product iphone = new Product("iPhone", 40000.0, "electronics");
        Product tv = new Product("TV", 20000.0, "electronics");

        List.of(banana, milk, iphone, tv).forEach(inventoryService::addProduct);

        assertEquals(inventoryService.filterByPrice(10000.0, 20000.0), List.of(tv));
    }
}
