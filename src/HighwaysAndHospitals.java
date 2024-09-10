import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Alexandre Haddad-Delaveau
 */

public class HighwaysAndHospitals {
    // County Information
    int n;
    int hospitalCost;
    int highwayCost;
    int[][] cities;

    // Keep track of visited cities
    boolean[] visited;
    boolean[] hasHospital;

    // Constructor
    public HighwaysAndHospitals(int n, int hospitalCost, int highwayCost, int[][] cities) {
        this.n = n;
        this.hospitalCost = hospitalCost;
        this.highwayCost = highwayCost;
        visited = new boolean[n + 1];
        hasHospital = new boolean[n + 1];

        // Convert cities to new format
        this.cities = new int[n + 1][];
        ArrayList<Integer>[] citiesArrayList = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) citiesArrayList[i] = new ArrayList<>();

        for (int[] edge : cities) {
            citiesArrayList[edge[0]].add(edge[1]);
            citiesArrayList[edge[1]].add(edge[0]);
        }

        // Convert to array
        for (int i = 1; i <= n; i++) {
            this.cities[i] = citiesArrayList[i].stream().mapToInt(j -> j).toArray();
        }
    }

    public long cost() {
        // BFS + CHECK IF NEW NEIGHBOURS * HIGHWAY COST < HOSPITAL COST
        // If so, add highway
        // Else, add hospital and destroy neighboring hospitals
        // When complete, return total cost

        // Quick bypass for hospitals cost >= highways cost
        if (hospitalCost <= highwayCost) {
            return (long) n * hospitalCost;
        }

        // Queue for BFS
        Queue<Integer> queue = new LinkedList<>();

        // Loop until all cities have been visited
        while (true) {
            // Check if there are any cities left to visit
            int initialCity = findFirstUnvisitedCity();
            if (initialCity == -1) break;

            // Add initial city to queue
            queue.add(initialCity);
            visited[initialCity] = true;
            hasHospital[initialCity] = true;

            // Loop through neighbours
            while (!queue.isEmpty()) {
                // Get current city
                int currentCity = queue.remove();

                // Count new neighbours
                int newNeighbours = 0;
                for (int neighbour : cities[currentCity]) {
                    if (!visited[neighbour]) {
                        newNeighbours++;

                        // Also add to queue & mark visited
                        queue.add(neighbour);
                        visited[neighbour] = true;
                    }
                }

                // Decide if a hospital or neighbor is cheaper
                if (buildHospital(newNeighbours)) {
                    hasHospital[currentCity] = true;

                    // If a hospital is built, destroy neighboring hospitals (if hopsital cost > highway cost)
                    for (int neighbour : cities[currentCity]) {
                        hasHospital[neighbour] = false;
                    }
                }
            }
        }
        return calculateCost();
    }

    private int findFirstUnvisitedCity() {
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                return i;
            }
        }
        return -1;
    }

    private boolean buildHospital(int neighbours) {
        return neighbours * highwayCost > hospitalCost || highwayCost >= hospitalCost;
    }

    private long calculateCost() {
        // Calculate cost of building hospitals and highways
        // (assuming each city must either be connected by a highway or have a hospital)
        long totalCost = 0;
        for (int i = 1; i <= n; i++) {
            if (hasHospital[i]) {
                totalCost += hospitalCost;
            } else {
                totalCost += highwayCost;
            }
        }

        return totalCost;
    }
}
