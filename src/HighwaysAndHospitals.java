import java.util.ArrayList;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Alexandre Haddad-Delaveau
 */

// QUICK NOTE: Since I missed the class on Union-Find, I also used Wikipedia as a source for information on the algorithm.
// Here is the link: https://en.wikipedia.org/wiki/Disjoint-set_data_structure
// Please let me know if this is something I should refrain from doing in the future.

public class HighwaysAndHospitals {
    public long cost(int n, int hospitalCost, int highwayCost, int[][] connections) {
        // Quick exit for hospital cost being lower than highway cost
        if (hospitalCost <= highwayCost) {
            return (long) n * hospitalCost;
        }

        // Map of cities to their root nodes
        int[] rootNodes = new int[n];
        int[] rank = new int[n];

        // Add default values to root nodes
        for (int i = 0; i < n; i++) {
            rootNodes[i] = i;
            rank[i] = 0;
        }

        // Loop through all city connections
        for (int[] connection : connections) {
            int root1 = find(connection[0] - 1, rootNodes);
            int root2 = find(connection[1] - 1, rootNodes);

            // If the roots are different, merge the trees
            if (root1 != root2) {
                // Merge the trees
                if (rank[root1] < rank[root2]) {
                    rootNodes[root1] = root2;
                } else if (rank[root1] > rank[root2]) {
                    rootNodes[root2] = root1;
                } else {
                    rootNodes[root1] = root2;
                    rank[root2]++;
                }
            }
        }

        // Count the number of root nodes
        int rootNodesCount = 0;
        for (int i = 0; i < n; i++) {
            if (rootNodes[i] == i) {
                rootNodesCount++;
            }
        }

        // Calculate the cost
        return (long) rootNodesCount * hospitalCost + (long) (n - rootNodesCount) * highwayCost;
    }

    private int find(int node, int[] rootNodes) {
        // Verify that the node is not the root node
        if (rootNodes[node] != node) {
            // Recursively find the root node
            rootNodes[node] = find(rootNodes[node], rootNodes); // Path compression
        }
        return rootNodes[node];
    }
}
