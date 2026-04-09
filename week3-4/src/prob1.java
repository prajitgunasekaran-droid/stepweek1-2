import java.util.*;

class Transaction {
    String id;
    double fee;
    String timestamp;

    Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    public String toString() {
        return id + ":" + fee + "@" + timestamp;
    }
}

class Main {

    // 🔹 Bubble Sort (Ascending by fee)
    public static void bubbleSort(ArrayList<Transaction> list) {
        int n = list.size();
        int passes = 0, swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++;

            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {

                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);

                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }

        System.out.println("BubbleSort (fees): " + list);
        System.out.println("Passes: " + passes + ", Swaps: " + swaps);
    }

    // 🔹 Insertion Sort (fee + timestamp)
    public static void insertionSort(ArrayList<Transaction> list) {

        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    (list.get(j).fee > key.fee ||
                            (list.get(j).fee == key.fee &&
                                    list.get(j).timestamp.compareTo(key.timestamp) > 0))) {

                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }

        System.out.println("InsertionSort (fee+ts): " + list);
    }

    // 🔹 Outliers (>50)
    public static void findOutliers(ArrayList<Transaction> list) {
        System.out.print("High-fee outliers: ");
        boolean found = false;

        for (Transaction t : list) {
            if (t.fee > 50) {
                System.out.print(t + " ");
                found = true;
            }
        }

        if (!found) System.out.print("none");

        System.out.println();
    }

    // 🔹 MAIN (Hardcoded Input)
    public static void main(String[] args) {

        ArrayList<Transaction> transactions = new ArrayList<>();

        // ✅ SAMPLE INPUT (already given)
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        System.out.println("Input Transactions:");
        System.out.println(transactions);

        // Bubble Sort
        bubbleSort(transactions);

        // Insertion Sort
        insertionSort(transactions);

        // Outliers
        findOutliers(transactions);
    }
}