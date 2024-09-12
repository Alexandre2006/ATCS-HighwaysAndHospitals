import java.util.ArrayList;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Alexandre Haddad-Delaveau
 */

public class HighwaysAndHospitals {
    public long cost(int n, int hospitalCost, int highwayCost, int[][] oldCities) {
        // Quick bypass for hospitals cost >= highways cost
        if (hospitalCost <= highwayCost) {
            return (long) n * hospitalCost;
        }

        // Variables for tracking visited cities & total hospital count
        boolean[] visited = new boolean[n + 1];
        int lastUnvisited = 1;
        int hospitalCount = 0;

        // Convert cities to map (city, neighbors)
        int[][] cities = new int[n + 1][];
        ArrayList<Integer>[] citiesArrayList = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) citiesArrayList[i] = new ArrayList<>();

        for (int[] edge : oldCities) {
            citiesArrayList[edge[0]].add(edge[1]);
            citiesArrayList[edge[1]].add(edge[0]);
        }

        // Convert back to array
        for (int i = 1; i <= n; i++) {
            cities[i] = citiesArrayList[i].stream().mapToInt(j -> j).toArray();
        }

        // Count # of "islands" of nodes
        // Loop until all cities have been visited
        while (true) {
            // Check if there are any cities left to visit
            int initialCity = findFirstUnvisitedCity(n, visited, lastUnvisited);
            if (initialCity == -1) break;
            lastUnvisited = initialCity;

            // Add initial city to queue
            visited[initialCity] = true;

            // Count hospital in island
            hospitalCount++;

            // Start DFS (from initial city)
            dfs(initialCity, visited, cities);
        }
        return ((long) hospitalCount * hospitalCost) + ((long) (n - hospitalCount) * highwayCost);
    }

    void dfs(int city, boolean[] visited, int[][] cities) {
        // Performs DFS on the graph
        // Doesn't actually search, just marks a city as visited
        visited[city] = true;
        for (int neighbour : cities[city]) {
            if (!visited[neighbour]) {
                dfs(neighbour, visited, cities);
            }
        }
    }

    private int findFirstUnvisitedCity(int n, boolean[] visited, int lastUnvisited) {
        // Find the first unvisited city in the list of cities
        for (int i = lastUnvisited; i <= n; i++) {
            if (!visited[i]) {
                return i;
            }
        }
        return -1;
    }
}
