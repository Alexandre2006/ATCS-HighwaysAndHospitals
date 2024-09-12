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
    int[][] cities; // Edges (index is city, array is connected cities)

    // Keep track of visited cities
    boolean[] visited;
    int lastUnvisited = 1;

    // Keep track of hospitals
    int hospitalCount = 0;

    // Constructor
    public HighwaysAndHospitals(int n, int hospitalCost, int highwayCost, int[][] cities) {
        this.n = n;
        this.hospitalCost = hospitalCost;
        this.highwayCost = highwayCost;
        visited = new boolean[n + 1];

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

    // TODO: Make Efficient
    public long cost() {
        // Quick bypass for hospitals cost >= highways cost
        if (hospitalCost <= highwayCost) {
            return (long) n * hospitalCost;
        }

        // Count # of "islands" of nodes
        // Loop until all cities have been visited
        while (true) {
            // Check if there are any cities left to visit
            int initialCity = findFirstUnvisitedCity();
            if (initialCity == -1) break;

            // Add initial city to queue
            visited[initialCity] = true;

            // Count hospital in island
            hospitalCount++;

            // Start DFS
            dfs(initialCity);
        }
        return ((long) hospitalCount * hospitalCost) + ((long) (n - hospitalCount) * highwayCost);
    }

    void dfs(int city) {
        visited[city] = true;
        for (int neighbour : cities[city]) {
            if (!visited[neighbour]) {
                dfs(neighbour);
            }
        }
    }

    private int findFirstUnvisitedCity() {
        for (int i = lastUnvisited; i <= n; i++) {
            if (!visited[i]) {
                lastUnvisited = i;
                return i;
            }
        }
        return -1;
    }
}
