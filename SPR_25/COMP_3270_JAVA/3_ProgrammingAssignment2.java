import java.io.*;
import java.util.*;


public class ProgrammingAssignment2 {

    public static List<String[]> readfile;
    public static Map<String, List<String>> buildGraph;
    public static List<String> bfs_sssp;
    public static List<String> dfs_sssp;

    public static void main(String[] args) {
        String filePath = "Test_Case_Assignment2.txt";
        List<String[]> connections = readFile(filePath);

        if (connections.isEmpty()) {
            System.out.println("No connections found in the file.");
            return;
        } else {
            //System.out.println("Connections read from the file:");
            //for (String[] connection : connections) {
            //    System.out.println(Arrays.toString(connection) + "\n");
            //}
        }

        Map<String, List<String>> graph = buildGraph(connections);
        
        /*
        System.out.println("Graph built from connections:");
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("Graph size: " + graph.size() + "\n");
        */

        /*
        String startNode = "N_0";
        String target_node = "N_24";
        List<String> bfsTraversal = bfs_sssp(graph, startNode, target_node);
        System.out.println();
        System.out.println("BFS traversal from " + startNode + " to " + target_node + ": " + bfsTraversal);
        System.out.println("Distance from " + startNode + " to " + target_node + ": " + (bfsTraversal.size() - 1) + "\n");
        */
          
        String startNode = "N_0";
        String target_node = "N_9";
        List<String> dfsTraversal = dfs_sssp(graph, startNode, target_node);
        System.out.println();
        System.out.println("DFS traversal from " + startNode + " to " + target_node + ": " + dfsTraversal);
        System.out.println("Distance from " + startNode + " to " + target_node + ": " + (dfsTraversal.size() - 1) + "\n");
        }  
    
    public static List<String[]> readFile(String filePath) {
        List<String[]> connections = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by commas and add to the list
                String[] terms = line.split(",");
                connections.add(terms);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return connections;
    }

    public static Map<String, List<String>> buildGraph(List<String[]> connections) {
        // Create new HashMap structure to store graph connections
        // (Key: node, Value: List of connected nodes)
        Map<String, List<String>> graph = new HashMap<>();

        // Iterate for each listed connection
        for (String[] connection : connections) {
            for (int i = 1; i < connection.length; i++) {
                // Check for duplicate connections before adding
                graph.putIfAbsent(connection[0], new ArrayList<>());
                graph.putIfAbsent(connection[i], new ArrayList<>());

                // Add edge if it doesn't already exist
                if (!graph.get(connection[0]).contains(connection[i])) {
                    graph.get(connection[0]).add(connection[i]);
                }
                if (!graph.get(connection[i]).contains(connection[0])) {
                    graph.get(connection[i]).add(connection[0]);
                }
            }
        }

        return graph;
    }
    
    
    public static List<String> bfs_sssp(Map<String, List<String>> g, String s, String t) {
        // Check if s (start), t (target) nodes exist in graph
        // -- return empty list if not found
        if (!g.containsKey(s)) {
            System.out.println("Start node not found: " + s);
            return new ArrayList<>();
        }
        if (!g.containsKey(t)) {
            System.out.println("Target node not found: " + t);
            return new ArrayList<>();
        }

        // Initialize BFS variables
        Set<String> visited = new HashSet<>();
        Queue<String> visitQ = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();

        visitQ.add(s); // Add starting node to queue
        visited.add(s); // Mark starting node as visited
        parent.put(s, null); // Initialize parent map

        // Start timer
        long startTime = System.nanoTime();
        System.out.println("Start time: " + startTime + " ns");
        System.out.println("BFS traversal starting from node: " + s);
        while (!visitQ.isEmpty()) {
            String current = visitQ.poll();

            // If target node is found, reconstruct path and break loop
            if (current.equals(t)) {
                // Stop timer and print result
                long endTime = System.nanoTime();
                System.out.println("End time: " + endTime + " ns");
                // Using nanoTime for more precise time measurement, still in ms ( time ns / 1000000.0 = time ms)
                System.out.println("Time taken to find the target node: " + ((endTime - startTime) / 1000000.0) + " ms");
                List<String> path = new ArrayList<>();
                String node = t;
                while (node != null) {
                    // Add node to front of path and move to parent node
                    path.add(0, node);
                    node = parent.get(node);
                }
                return path;
            }
            List<String> neighbors = g.get(current);

            // Get all neighbors of current node
            if (neighbors != null && !neighbors.isEmpty()) {
                for (String neighbor : neighbors) {
                    // Add neighbors only if not already visited
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        visitQ.add(neighbor);
                        // Save parent node for path reconstruction
                        parent.put(neighbor, current);
                    }
                }
            }
        }
        // If the target node is not reachable, return an empty list

        System.out.println("Target node not reachable: " + t);
        return new ArrayList<>();
    }
    
    public static List<String> dfs_sssp(Map<String, List<String>> g, String s, String t) {
        // Check if s (start), t (target) nodes exist in graph
        // -- return empty list if not found
        if (!g.containsKey(s)) {
            System.out.println("Start node not found: " + s);
            return new ArrayList<>();
        }
        if (!g.containsKey(t)) {
            System.out.println("Target node not found: " + t);
            return new ArrayList<>();
        }

        // Initialize DFS variables
        Set<String> visited = new HashSet<>();
        Stack<String> visitS = new Stack<>();
        Map<String, String> parent = new HashMap<>();

        visitS.push(s); // Add starting node to stack
        visited.add(s); // Mark starting node as visited
        parent.put(s, null); // Initialize parent map

        // Start timer
        long startTime = System.nanoTime();
        System.out.println("Start time: " + startTime + " ns");
        System.out.println("DFS traversal starting from node: " + s);
        while (!visitS.isEmpty()) {
            String current = visitS.pop();

            // If target node is found, reconstruct path and break loop
            if (current.equals(t)) {
                // Stop timer and print result
                long endTime = System.nanoTime();
                System.out.println("End time: " + endTime + " ns");
                System.out
                        .println("Time taken to find the target node: " + ((endTime - startTime) / 1000000.0) + " ms");
                List<String> path = new ArrayList<>();
                String node = t;
                while (node != null) {
                    // Add node to front of path and move to parent node
                    path.add(0, node);
                    node = parent.get(node);
                }
                return path;
            }
            List<String> neighbors = g.get(current);

            // Get all neighbors of current node
            if (neighbors != null && !neighbors.isEmpty()) {
                for (String neighbor : neighbors) {
                    // Add neighbors only if not already visited
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        visitS.push(neighbor);
                        // Save parent node for path reconstruction
                        parent.put(neighbor, current);
                    }
                }
            }
        }
        // If the target node is not reachable, return an empty list
        System.out.println("Target node not reachable: " + t);
        return new ArrayList<>();
        }
    }
