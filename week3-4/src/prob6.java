import java.util.Arrays;

public class prob6 {

    // 🔹 Linear Search (unsorted)
    public static void linearSearch(int[] arr, int target) {
        int comparisons = 0;
        boolean found = false;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear: Found at index " + i + ", comparisons = " + comparisons);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Linear: Not found, comparisons = " + comparisons);
        }
    }

    // 🔹 Binary Search for insertion point (lower_bound)
    public static int lowerBound(int[] arr, int target) {
        int low = 0, high = arr.length;
        int comparisons = 0;

        while (low < high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        System.out.println("Insertion Index (lower_bound): " + low + ", comparisons = " + comparisons);
        return low;
    }

    // 🔹 Floor (largest ≤ target)
    public static int floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int result = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] == target) {
                result = arr[mid];
                break;
            } else if (arr[mid] < target) {
                result = arr[mid]; // possible floor
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Floor(" + target + "): " + result + ", comparisons = " + comparisons);
        return result;
    }

    // 🔹 Ceiling (smallest ≥ target)
    public static int ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int result = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] == target) {
                result = arr[mid];
                break;
            } else if (arr[mid] > target) {
                result = arr[mid]; // possible ceiling
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        System.out.println("Ceiling(" + target + "): " + result + ", comparisons = " + comparisons);
        return result;
    }

    // 🔹 MAIN
    public static void main(String[] args) {

        int[] unsorted = {50, 10, 100, 25};
        int[] sorted = {10, 25, 50, 100};

        int target = 30;

        System.out.println("Unsorted Risks:");
        System.out.println(Arrays.toString(unsorted));

        // Linear Search
        linearSearch(unsorted, target);

        System.out.println("\nSorted Risks:");
        System.out.println(Arrays.toString(sorted));

        // Binary insertion point
        lowerBound(sorted, target);

        // Floor & Ceiling
        floor(sorted, target);
        ceiling(sorted, target);
    }
}