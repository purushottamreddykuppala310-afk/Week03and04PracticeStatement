import java.util.*;

// ==========================
// TRANSACTION LOG ENTRY
// ==========================
class Transaction {
    String accountId;
    double amount;
    String timestamp;

    public Transaction(String accountId, double amount, String timestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return accountId + ":" + amount + "@" + timestamp;
    }
}

// ==========================
// ACCOUNT ID LOOKUP SERVICE
// ==========================
class AccountIdLookupService {

    // --------------------------
    // LINEAR SEARCH FIRST OCCURRENCE
    // --------------------------
    public static int linearSearchFirst(Transaction[] logs, String target) {
        int comparisons = 0;
        for (int i = 0; i < logs.length; i++) {
            comparisons++;
            if (logs[i].accountId.equals(target)) {
                System.out.println("Linear first '" + target + "': index " + i + " (" + comparisons + " comparisons)");
                return i;
            }
        }
        System.out.println("Linear first '" + target + "': not found (" + comparisons + " comparisons)");
        return -1;
    }

    // --------------------------
    // LINEAR SEARCH LAST OCCURRENCE
    // --------------------------
    public static int linearSearchLast(Transaction[] logs, String target) {
        int comparisons = 0;
        int lastIndex = -1;
        for (int i = 0; i < logs.length; i++) {
            comparisons++;
            if (logs[i].accountId.equals(target)) lastIndex = i;
        }
        if (lastIndex != -1) {
            System.out.println("Linear last '" + target + "': index " + lastIndex + " (" + comparisons + " comparisons)");
        } else {
            System.out.println("Linear last '" + target + "': not found (" + comparisons + " comparisons)");
        }
        return lastIndex;
    }

    // --------------------------
    // BINARY SEARCH EXACT MATCH + COUNT
    // --------------------------
    public static void binarySearch(Transaction[] logs, String target) {
        // Ensure sorted by accountId
        Arrays.sort(logs, Comparator.comparing(t -> t.accountId));

        int low = 0, high = logs.length - 1;
        int comparisons = 0;
        int foundIndex = -1;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int cmp = logs[mid].accountId.compareTo(target);
            if (cmp == 0) {
                foundIndex = mid;
                break;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (foundIndex != -1) {
            // Count duplicates
            int count = 1;
            int left = foundIndex - 1;
            while (left >= 0 && logs[left].accountId.equals(target)) { count++; left--; }
            int right = foundIndex + 1;
            while (right < logs.length && logs[right].accountId.equals(target)) { count++; right++; }

            System.out.println("Binary '" + target + "': index " + foundIndex + ", count=" + count +
                    " (" + comparisons + " comparisons)");
        } else {
            System.out.println("Binary '" + target + "': not found (" + comparisons + " comparisons)");
        }
    }
}

// ==========================
// MAIN DEMO
// ==========================
public class Problem5 {
    public static void main(String[] args) {
        Transaction[] logs = new Transaction[] {
                new Transaction("accB", 100, "10:00"),
                new Transaction("accA", 200, "09:30"),
                new Transaction("accB", 50, "10:15"),
                new Transaction("accC", 300, "11:00")
        };

        // Linear Search
        AccountIdLookupService.linearSearchFirst(logs, "accB");
        AccountIdLookupService.linearSearchLast(logs, "accB");

        // Binary Search
        AccountIdLookupService.binarySearch(logs, "accB");
    }
}