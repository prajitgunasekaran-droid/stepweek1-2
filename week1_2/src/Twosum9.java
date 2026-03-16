import java.util.*;

class Transaction {

    int id;
    int amount;
    String merchant;
    String account;
    int time;

    Transaction(int id, int amount, String merchant, String account, int time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

class Main {

    static List<Transaction> transactions = new ArrayList<>();

    public static void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // Classic Two Sum
    public static void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction other = map.get(complement);

                System.out.println("Pair Found: "
                        + other.id + " + " + t.id);
            }

            map.put(t.amount, t);
        }
    }

    // Duplicate Detection
    public static void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> list = map.get(key);

            if (list.size() > 1) {

                System.out.println("Duplicate for " + key);

                for (Transaction t : list)
                    System.out.println("Account: " + t.account);
            }
        }
    }

    public static void main(String[] args) {

        addTransaction(new Transaction(1,500,"StoreA","acc1",600));
        addTransaction(new Transaction(2,300,"StoreB","acc2",615));
        addTransaction(new Transaction(3,200,"StoreC","acc3",630));
        addTransaction(new Transaction(4,500,"StoreA","acc4",640));

        System.out.println("Two Sum Result:");
        findTwoSum(500);

        System.out.println("\nDuplicate Detection:");
        detectDuplicates();
    }
}