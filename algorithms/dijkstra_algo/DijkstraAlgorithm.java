import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstraAlgorithm {
    private final int n;

    private int edgeCount;
    private double[] dist;
    private int[] prev;
    private List<List<Edge>> graph;


    public DijkstraAlgorithm(int n) { // Using D-Heap
        this.n = n;
        createEmptyGraph();
    }

    private void createEmptyGraph() {
        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    }

    private void addEdge(int from, int to, int cost) {
        edgeCount++;
        Edge newEdge = new Edge(to, cost);
        graph.get(from).add(newEdge);
    }

    public List<List<Edge>> getGraph() {
        return graph;
    }

    public double dijkstra(int start, int end) // End for optimization
    {
        int degree = edgeCount / n; // Keeping Indexed Priority Queue (IPQ) arranged in optimized size for the next most promising node to visit
        MinIndexedDHeap<Double> ipq = new MinIndexedDHeap<>(degree, n);
        ipq.insert(start, 0D);

        dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0D;

        boolean[] visited = new boolean[n];
        prev = new int[n];

        while (!ipq.isEmpty()) {
            int nodeId = ipq.peekMinKeyIndex();

            visited[nodeId] = true;
            double minValue = ipq.pollMinValue();

            // Current Distance is smaller than the new minimal value, ignoring this case
            if (minValue > dist[nodeId]) continue;

            for (Edge edge : graph.get(nodeId)) {
                // since we are choosing lowest (most promising) path, we do not care about the already visited edges, since we are -sort of- sure
                // of choosing the better node by ignoring the visited, and checking the unvisited
                // (there is no better path for the visited node other that what we already found)
                if (visited[edge.to]) continue;

                double newDistance = dist[nodeId] + edge.cost;
                if (newDistance < dist[edge.to]) {
                    prev[edge.to] = nodeId;
                    dist[edge.to] = newDistance;

                    if (!ipq.contains(edge.to))
                        ipq.insert(edge.to, newDistance);
                    else
                        ipq.decrease(edge.to, newDistance);
                }
            }

            // Once we have reached to our final ending node, we no longer expecting there is better pathway to that specific node (No Negative Weights)
            if (nodeId == end) return dist[end];
        }

        // Unreachable (Just for Java Syntax)
        return Double.POSITIVE_INFINITY;
    }


    public List<Integer> reconstructPaths(int start, int end) {
        if (end < 0 || end >= n || start < 0 || start >= n)
            throw new IllegalArgumentException("Invalid Node Index");

        List<Integer> path = new ArrayList<>();
        double dist = dijkstra(start, end);
        if (dist == Double.POSITIVE_INFINITY)
            return path;

        for (Integer at = end; at != null; at = prev[at])
            path.add(at);

        Collections.reverse(path);
        return path;

    }

}

