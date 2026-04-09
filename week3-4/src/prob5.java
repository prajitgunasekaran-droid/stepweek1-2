import java.util.Arrays;

public class prob5 {

    // 🔹 Linear Search (first occurrence)
    public static int linearFirst(String[] arr, String target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                System.out.println("Linear First: index = " + i + ", comparisons = " + comparisons);
                return i;
            }
        }

        System.out.println("Linear First: Not found, comparisons = " + comparisons);
        return -1;
    }

    // 🔹 Linear Search (last occurrence)
    public static int linearLast(String[] arr, String target) {
        int comparisons = 0;

        for (int i = arr.length - 1; i >= 0; i--) {
            comparisons++;
            if (arr[i].equals(target)) {
                System.out.println("Linear Last: index = " + i + ", comparisons = " + comparisons);
                return i;
            }
        }

        System.out.println("Linear Last: Not found, comparisons = " + comparisons);
        return -1;
    }

    // 🔹 Binary Search (any occurrence)
    public static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            int cmp = arr[mid].compareTo(target);

            if (cmp == 0) {
                System.out.println("Binary Search: index = " + mid + ", comparisons = " + comparisons);
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Binary Search: Not found, comparisons = " + comparisons);
        return -1;
    }

    // 🔹 Count occurrences using Binary Search
    public static int countOccurrences(String[] arr, String target) {
        int first = firstOccurrence(arr, target);
        int last = lastOccurrence(arr, target);

        if (first == -1) return 0;
        return last - first + 1;
    }

    // 🔹 First occurrence (Binary)
    public static int firstOccurrence(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                high = mid - 1; // move left
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    // 🔹 Last occurrence (Binary)
    public static int lastOccurrence(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                low = mid + 1; // move right
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    // 🔹 MAIN
    public static void main(String[] args) {

        String[] logs = {"accB", "accA", "accB", "accC"};

        System.out.println("Original Logs:");
        System.out.println(Arrays.toString(logs));

        String target = "accB";

        // Linear Search
        linearFirst(logs, target);
        linearLast(logs, target);

        // Sort for Binary Search
        Arrays.sort(logs);
        System.out.println("\nSorted Logs:");
        System.out.println(Arrays.toString(logs));

        // Binary Search
        binarySearch(logs, target);

        // Count duplicates
        int count = countOccurrences(logs, target);
        System.out.println("Count of " + target + " = " + count);
    }
}