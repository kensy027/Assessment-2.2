import java.util.*;

public class Graph {
    private Map<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public Node getOrCreateNode(String courseCode) {
        Node node = nodes.get(courseCode);
        if (node == null) {
            node = new Node(courseCode);
            nodes.put(courseCode, node);
        }
        return node;
    }

    public Node getNode(String courseCode) {
        return nodes.get(courseCode);
    }

    public Collection<Node> getAllNodes() {
        return nodes.values();
    }

    public List<String> getAllCourses() {
        List<String> allCourses = new ArrayList<>();
        for (Node node : getAllNodes()) {
            allCourses.add(node.getCourseCode());
        }
        return allCourses;
    }

    // BFS implementation
    public List<String> bfs(String startCourse) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        Node startNode = getNode(startCourse);

        if (startNode == null) {
            return result; // Course not found
        }

        queue.add(startNode);
        visited.add(startCourse);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(current.getCourseCode());

            for (Node neighbor : current.getPrerequisites()) {
                if (!visited.contains(neighbor.getCourseCode())) {
                    queue.add(neighbor);
                    visited.add(neighbor.getCourseCode());
                }
            }
        }

        return result;
    }

    // DFS implementation
    public List<String> dfs(String startCourse) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Node startNode = getNode(startCourse);

        if (startNode == null) {
            return result; // Course not found
        }

        dfsUtil(startNode, visited, result);
        return result;
    }

    private void dfsUtil(Node node, Set<String> visited, List<String> result) {
        visited.add(node.getCourseCode());
        result.add(node.getCourseCode());

        for (Node neighbor : node.getPrerequisites()) {
            if (!visited.contains(neighbor.getCourseCode())) {
                dfsUtil(neighbor, visited, result);
            }
        }
    }
}
