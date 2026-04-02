import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// ==========================
// TRANSACTION ENTITY
// ==========================
class Transaction {
    String id;
    double fee;
    Date timestamp;

    public Transaction(String id, double fee, String ts) throws ParseException {
        this.id = id;
        this.fee = fee;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        this.timestamp = sdf.parse(ts);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return id + ":" + fee + "@" + sdf.format(timestamp);
    }
}

// ==========================
// SORTING & AUDIT SERVICE
// ==========================
class TransactionSorter {

    // --------------------------
    // BUBBLE SORT (fee ascending)
    // --------------------------
    public static void bubbleSortFee(List<Transaction> transactions) {
        int n = transactions.size();
        boolean swapped;
        int passes = 0, swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (transactions.get(j).fee > transactions.get(j + 1).fee) {
                    Collections.swap(transactions, j, j + 1);
                    swapped = true;
                    swaps++;
                }
            }
            if (!swapped) break; // early termination
        }

        System.out.println("BubbleSort (fees): " + transactions);
        System.out.println("Passes: " + passes + ", Swaps: " + swaps);
    }

    // --------------------------
    // INSERTION SORT (fee + timestamp)
    // --------------------------
    public static void insertionSortFeeTimestamp(List<Transaction> transactions) {
        for (int i = 1; i < transactions.size(); i++) {
            Transaction key = transactions.get(i);
            int j = i - 1;

            // Compare fee first, then timestamp if fees equal
            while (j >= 0 &&
                    (transactions.get(j).fee > key.fee ||
                            (transactions.get(j).fee == key.fee &&
                                    transactions.get(j).timestamp.after(key.timestamp)))) {
                transactions.set(j + 1, transactions.get(j)); // shift right
                j--;
            }
            transactions.set(j + 1, key); // insert key
        }

        System.out.println("InsertionSort (fee+ts): " + transactions);
    }

    // --------------------------
    // HIGH-FEE OUTLIER DETECTION
    // --------------------------
    public static void flagHighFeeOutliers(List<Transaction> transactions, double threshold) {
        List<Transaction> outliers = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.fee > threshold) outliers.add(t);
        }

        if (outliers.isEmpty()) System.out.println("High-fee outliers: none");
        else System.out.println("High-fee outliers: " + outliers);
    }
}

// ==========================
// MAIN DEMO
// ==========================
public class Problem1 {

    public static void main(String[] args) throws ParseException {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));
        transactions.add(new Transaction("id4", 55.0, "11:00")); // high-fee outlier

        // --------------------------
        // Bubble Sort (small batch)
        // --------------------------
        List<Transaction> bubbleList = new ArrayList<>(transactions.subList(0, 3));
        TransactionSorter.bubbleSortFee(bubbleList);

        // --------------------------
        // Insertion Sort (medium batch)
        // --------------------------
        TransactionSorter.insertionSortFeeTimestamp(transactions);

        // --------------------------
        // Flag high-fee outliers
        // --------------------------
        TransactionSorter.flagHighFeeOutliers(transactions, 50.0);
    }
}