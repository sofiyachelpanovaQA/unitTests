package complex_tasks.task_5_inventory_service;

import java.util.*;

public class InventoryService {
    private boolean isInventoryOpen = true;

    public void setInventoryOpen(boolean inventoryOpen) {
        isInventoryOpen = inventoryOpen;
    }

    Map<String, List<Product>> productListMap = new HashMap<>();

    public Map<String, List<Product>> getProductListMap() {
        return Map.copyOf(productListMap);
    }

    public void addProduct(Product product) {
        if (!isInventoryOpen) {
            throw new IllegalStateException("Нет доступа к складу");
        }
        productListMap.computeIfAbsent(product.getCategory().toLowerCase(), k -> new ArrayList<>()).add(product);
    }

    public Product getProductByCategory(String category) {
        List<Product> productListInOneCategory = productListMap.get(category.toLowerCase());
        if (productListInOneCategory == null) {
            throw new IllegalArgumentException("Данной категории еще не существует, необходимо ее добавить");
        }
        if (productListInOneCategory.isEmpty()) {
            throw new OutOfStockException();
        }

        return productListInOneCategory.removeLast();
    }

    public List<Product> findProductsByCategory(String category) {
        return productListMap.get(category.toLowerCase());
    }

    public List<Product> filterByPrice(double minPrice, double maxPrice) {
        return productListMap.values().stream()
                .flatMap(Collection::stream)
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .toList();
    }
}
