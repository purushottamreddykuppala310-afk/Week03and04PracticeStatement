import java.util.*;

// ==========================
// TRADE ENTITY
// ==========================
class Trade {
    String id;
    int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

// ==========================
// TRADE SORTING SERVICE
// ==========================
class TradeSorter {

    // --------------------------
    // MERGE SORT ASCENDING (stable)
    // --------------------------
    public static Trade[] mergeSortAscending(Trade[] trades) {
        if (trades.length <= 1) return trades;

        int mid = trades.length / 2;
        Trade[] left = Arrays.copyOfRange(trades, 0, mid);
        Trade[] right = Arrays.copyOfRange(trades, mid, trades.length);

        return merge(mergeSortAscending(left), mergeSortAscending(right));
    }

    private static Trade[] merge(Trade[] left, Trade[] right) {
        Trade[] merged = new Trade[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].volume <= right[j].volume) { // stable
                merged[k++] = left[i++];
            } else {
                merged[k++] = right[j++];
            }
        }

        while (i < left.length) merged[k++] = left[i++];
        while (j < right.length) merged[k++] = right[j++];

        return merged;
    }

    // --------------------------
    // QUICK SORT DESCENDING (in-place)
    // --------------------------
    public static void quickSortDescending(Trade[] trades, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(trades, low, high);
            quickSortDescending(trades, low, pivotIndex - 1);
            quickSortDescending(trades, pivotIndex + 1, high);
        }
    }

    private static int partition(Trade[] trades, int low, int high) {
        Trade pivot = trades[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (trades[j].volume >= pivot.volume) { // descending
                i++;
                Trade temp = trades[i];
                trades[i] = trades[j];
                trades[j] = temp;
            }
        }
        Trade temp = trades[i + 1];
        trades[i + 1] = trades[high];
        trades[high] = temp;
        return i + 1;
    }

    // --------------------------
    // MERGE TWO SORTED TRADE LISTS
    // --------------------------
    public static Trade[] mergeTwoSortedLists(Trade[] list1, Trade[] list2) {
        Trade[] merged = new Trade[list1.length + list2.length];
        int i = 0, j = 0, k = 0;

        while (i < list1.length && j < list2.length) {
            if (list1[i].volume <= list2[j].volume) {
                merged[k++] = list1[i++];
            } else {
                merged[k++] = list2[j++];
            }
        }
        while (i < list1.length) merged[k++] = list1[i++];
        while (j < list2.length) merged[k++] = list2[j++];

        return merged;
    }

    // --------------------------
    // TOTAL VOLUME
    // --------------------------
    public static int totalVolume(Trade[] trades) {
        int sum = 0;
        for (Trade t : trades) sum += t.volume;
        return sum;
    }
}

// ==========================
// MAIN DEMO
// ==========================
public class Problem3 {
    public static void main(String[] args) {

        Trade[] trades = new Trade[] {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        // 1️⃣ Merge Sort Ascending
        Trade[] mergeSorted = TradeSorter.mergeSortAscending(trades);
        System.out.println("MergeSort (asc): " + Arrays.toString(mergeSorted));

        // 2️⃣ Quick Sort Descending
        Trade[] quickSorted = trades.clone();
        TradeSorter.quickSortDescending(quickSorted, 0, quickSorted.length - 1);
        System.out.println("QuickSort (desc): " + Arrays.toString(quickSorted));

        // 3️⃣ Merge morning + afternoon (example)
        Trade[] morning = new Trade[] {new Trade("tradeA", 200), new Trade("tradeB", 400)};
        Trade[] afternoon = new Trade[] {new Trade("tradeC", 300), new Trade("tradeD", 500)};
        Trade[] mergedSession = TradeSorter.mergeTwoSortedLists(
                TradeSorter.mergeSortAscending(morning),
                TradeSorter.mergeSortAscending(afternoon)
        );
        System.out.println("Merged morning+afternoon: " + Arrays.toString(mergedSession));
        System.out.println("Total volume: " + TradeSorter.totalVolume(mergedSession));
    }
}