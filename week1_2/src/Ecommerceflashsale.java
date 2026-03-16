import java.util.*;

class FlashSaleInventory {

    // productId -> stock
    private HashMap<String, Integer> inventory = new HashMap<>();

    // productId -> waiting list of users
    private HashMap<String, LinkedList<Integer>> waitingList = new HashMap<>();


    // Add product with stock
    public void addProduct(String productId, int stock) {
        inventory.put(productId, stock);
        waitingList.put(productId, new LinkedList<>());
    }


    // Check stock availability
    public int checkStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }


    // Purchase item (thread-safe)
    public synchronized String purchaseItem(String productId, int userId) {

        int stock = inventory.getOrDefault(productId, 0);

        if (stock > 0) {

            inventory.put(productId, stock - 1);

            return "Success, " + (stock - 1) + " units remaining";
        }

        else {

            LinkedList<Integer> queue = waitingList.get(productId);
            queue.add(userId);

            return "Added to waiting list, position #" + queue.size();
        }
    }


    // Restock product
    public synchronized void restock(String productId, int quantity) {

        int stock = inventory.getOrDefault(productId, 0);
        inventory.put(productId, stock + quantity);

        LinkedList<Integer> queue = waitingList.get(productId);

        while (!queue.isEmpty() && inventory.get(productId) > 0) {

            int user = queue.poll();
            inventory.put(productId, inventory.get(productId) - 1);

            System.out.println("Waiting user " + user + " purchased product.");
        }
    }


    public static void main(String[] args) {

        FlashSaleInventory system = new FlashSaleInventory();

        system.addProduct("IPHONE15_256GB", 100);

        System.out.println(system.checkStock("IPHONE15_256GB"));

        System.out.println(system.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(system.purchaseItem("IPHONE15_256GB", 67890));

        // simulate stock exhaustion
        for(int i=0;i<100;i++){
            system.purchaseItem("IPHONE15_256GB", i);
        }

        System.out.println(system.purchaseItem("IPHONE15_256GB", 99999));
    }
}