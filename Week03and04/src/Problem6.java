import java.util.Arrays;

class RiskThresholdLookup {

    // --------------------------
    // LINEAR SEARCH
    // --------------------------
    public static void linearSearch(int[] risks, int target) {
        int comparisons = 0;
        boolean found = false;
        for (int i = 0; i < risks.length; i++) {
            comparisons++;
            if (risks[i] == target) {
                found = true;
                System.out.println("Linear: threshold=" + target + " → found at index " + i + " (" + comparisons + " comparisons)");
                break;
            }
        }
        if (!found) {
            System.out.println("Linear: threshold=" + target + " → not found (" + comparisons + " comparisons)");
        }
    }

    // --------------------------
    // BINARY SEARCH FLOOR AND CEILING
    // --------------------------
    public static void binaryFloorCeil(int[] sortedRisks, int target) {
        int low = 0, high = sortedRisks.length - 1;
        int comparisons = 0;
        int floor = -1;
        int ceil = -1;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            if (sortedRisks[mid] == target) {
                floor = sortedRisks[mid];
                ceil = sortedRisks[mid];
                break;
            } else if (sortedRisks[mid] < target) {
                floor = sortedRisks[mid];
                low = mid + 1;
            } else {
                ceil = sortedRisks[mid];
                high = mid - 1;
            }
        }

        System.out.println("Binary: floor(" + target + ")=" + (floor != -1 ? floor : "N/A") +
                ", ceiling=" + (ceil != -1 ? ceil : "N/A") + " (" + comparisons + " comparisons)");
    }
}

// ==========================
// MAIN DEMO
// ==========================
public class Problem6 {
    public static void main(String[] args) {
        int[] risks = {10, 25, 50, 100}; // sorted
        int target = 30;

        // Linear search on unsorted risks
        RiskThresholdLookup.linearSearch(risks, target);

        // Binary floor/ceiling search (requires sorted)
        Arrays.sort(risks);
        RiskThresholdLookup.binaryFloorCeil(risks, target);
    }
}