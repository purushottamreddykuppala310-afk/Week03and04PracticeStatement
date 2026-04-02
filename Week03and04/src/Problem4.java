import java.util.*;

// ==========================
// ASSET ENTITY
// ==========================
class Asset {
    String symbol;
    double returnRate;   // in percentage
    double volatility;   // e.g., standard deviation of returns

    public Asset(String symbol, double returnRate, double volatility) {
        this.symbol = symbol;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return symbol + ":" + returnRate + "%";
    }
}

// ==========================
// PORTFOLIO SORTING SERVICE
// ==========================
class PortfolioSorter {

    // --------------------------
    // MERGE SORT ASCENDING (stable)
    // --------------------------
    public static Asset[] mergeSortAscending(Asset[] assets) {
        if (assets.length <= 1) return assets;

        int mid = assets.length / 2;
        Asset[] left = Arrays.copyOfRange(assets, 0, mid);
        Asset[] right = Arrays.copyOfRange(assets, mid, assets.length);

        return merge(mergeSortAscending(left), mergeSortAscending(right));
    }

    private static Asset[] merge(Asset[] left, Asset[] right) {
        Asset[] merged = new Asset[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].returnRate <= right[j].returnRate) { // stable
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
    // QUICK SORT DESC returnRate + ASC volatility
    // --------------------------
    public static void quickSort(Asset[] assets, int low, int high) {
        if (low < high) {
            int pivotIndex = medianOfThreePivot(assets, low, high);
            swap(assets, pivotIndex, high); // move pivot to end
            int partitionIndex = partition(assets, low, high);
            quickSort(assets, low, partitionIndex - 1);
            quickSort(assets, partitionIndex + 1, high);
        }
    }

    private static int partition(Asset[] assets, int low, int high) {
        Asset pivot = assets[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (assets[j].returnRate > pivot.returnRate ||
                    (assets[j].returnRate == pivot.returnRate && assets[j].volatility < pivot.volatility)) {
                i++;
                swap(assets, i, j);
            }
        }
        swap(assets, i + 1, high);
        return i + 1;
    }

    // --------------------------
    // MEDIAN OF THREE PIVOT
    // --------------------------
    private static int medianOfThreePivot(Asset[] assets, int low, int high) {
        int mid = low + (high - low) / 2;

        double a = assets[low].returnRate;
        double b = assets[mid].returnRate;
        double c = assets[high].returnRate;

        if ((a - b) * (c - a) >= 0) return low;
        else if ((b - a) * (c - b) >= 0) return mid;
        else return high;
    }

    private static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

// ==========================
// MAIN DEMO
// ==========================
public class Problem4 {
    public static void main(String[] args) {
        Asset[] portfolio = new Asset[] {
                new Asset("AAPL", 12, 0.3),
                new Asset("TSLA", 8, 0.6),
                new Asset("GOOG", 15, 0.25),
                new Asset("MSFT", 12, 0.2)
        };

        // 1️⃣ Merge Sort ascending
        Asset[] mergeSorted = PortfolioSorter.mergeSortAscending(portfolio);
        System.out.println("Merge Sort (asc returnRate): " + Arrays.toString(mergeSorted));

        // 2️⃣ Quick Sort descending returnRate + volatility
        Asset[] quickSorted = portfolio.clone();
        PortfolioSorter.quickSort(quickSorted, 0, quickSorted.length - 1);
        System.out.println("Quick Sort (desc returnRate + asc volatility): " + Arrays.toString(quickSorted));
    }
}