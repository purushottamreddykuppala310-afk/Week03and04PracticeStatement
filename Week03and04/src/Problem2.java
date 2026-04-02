import java.util.*;

// ==========================
// CLIENT ENTITY
// ==========================
class Client {
    String name;
    int riskScore;
    double accountBalance;

    public Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return name + "(" + riskScore + ")";
    }
}

// ==========================
// SORTING SERVICE
// ==========================
class ClientSorter {

    // --------------------------
    // BUBBLE SORT ASCENDING riskScore
    // --------------------------
    public static void bubbleSortRiskScore(Client[] clients) {
        int n = clients.length;
        boolean swapped;
        int passes = 0, swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                    swapped = true;
                    swaps++;
                    // Visualize swap
                    System.out.println("Swapped: " + clients[j] + " <-> " + clients[j + 1]);
                }
            }
            if (!swapped) break; // early termination
        }

        System.out.println("\nBubble Sort (asc riskScore): " + Arrays.toString(clients));
        System.out.println("Total passes: " + passes + ", Total swaps: " + swaps + "\n");
    }

    // --------------------------
    // INSERTION SORT DESC riskScore + accountBalance
    // --------------------------
    public static void insertionSortRiskDesc(Client[] clients) {
        int n = clients.length;

        for (int i = 1; i < n; i++) {
            Client key = clients[i];
            int j = i - 1;

            while (j >= 0 &&
                    (clients[j].riskScore < key.riskScore ||
                            (clients[j].riskScore == key.riskScore && clients[j].accountBalance < key.accountBalance))) {
                clients[j + 1] = clients[j]; // shift
                j--;
            }
            clients[j + 1] = key; // insert key
        }

        System.out.println("Insertion Sort (desc riskScore+balance): " + Arrays.toString(clients));
    }

    // --------------------------
    // TOP N HIGHEST RISK
    // --------------------------
    public static void topRiskClients(Client[] clients, int topN) {
        System.out.print("Top " + topN + " risks: ");
        for (int i = 0; i < Math.min(topN, clients.length); i++) {
            System.out.print(clients[i] + (i < topN - 1 ? ", " : ""));
        }
        System.out.println();
    }
}

// ==========================
// MAIN DEMO
// ==========================
public class Problem2 {
    public static void main(String[] args) {

        Client[] clients = new Client[] {
                new Client("clientC", 80, 5000.0),
                new Client("clientA", 20, 10000.0),
                new Client("clientB", 50, 7000.0),
                new Client("clientD", 80, 6000.0)
        };

        // 1️⃣ Bubble sort ascending riskScore
        ClientSorter.bubbleSortRiskScore(clients.clone()); // use clone to keep original

        // 2️⃣ Insertion sort descending riskScore + accountBalance
        ClientSorter.insertionSortRiskDesc(clients);

        // 3️⃣ Top 10 risks (or top N)
        ClientSorter.topRiskClients(clients, 10);
    }
}