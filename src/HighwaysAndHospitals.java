import java.util.LinkedList;
import java.util.Queue;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Alexandre Haddad-Delaveau
 *
 */

public class HighwaysAndHospitals {
    // County Information
    int n;
    int hospitalCost;
    int highwayCost;
    int[][] cities;

    // Keep track of visited cities
    boolean[] visited;

    // Constructor
    public HighwaysAndHospitals(int n, int hospitalCost, int highwayCost, int[][] cities) {
        this.n = n;
        this.hospitalCost = hospitalCost;
        this.highwayCost = highwayCost;
        this.cities = cities;

        visited = new boolean[n];
    }

    public long cost() {
        // BFS + CHECK IF NEW NEIGHBOURS * HIGHWAY COST < HOSPITAL COST
        // If so, add highway
        // Else, add hospital and destroy neighboring hospitals
        // When complete, return total cost

        // Total Cost
        int totalCost = 0;

        // Queue for BFS
        Queue<Integer> queue = new LinkedList<>();

        // Loop until all cities have been visited
        while (true) {
            // Check if there are any cities left to visit
            int initialCity = findFirstCityWithoutHospital();
            if (initialCity == -1) {
                break;
            }

            // Add initial city to queue
            queue.add(initialCity);
            visited[initialCity] = true;
            totalCost += hospitalCost;

            //



        }


        return 0;
    }

    private int findFirstCityWithoutHospital() {
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                return i;
            }
        }
        return -1;
    }

    private boolean buildHospital(int neighbours) {
        return neighbours * highwayCost > hospitalCost;
    }
}
