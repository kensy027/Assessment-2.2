import java.util.*;

public class Graph {
    private Map<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public Node getOrCreateNode(String courseCode) {
        return nodes.computeIfAbsent(courseCode, Node::new);
    }

    public Collection<Node> getAllNodes() {
        return nodes.values();
    }
}
