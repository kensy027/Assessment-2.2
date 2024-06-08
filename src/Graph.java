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
}
