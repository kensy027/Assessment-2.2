import java.util.*;

public class Graph {
    private Map<String, List<String>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(String course) {
        adjacencyList.putIfAbsent(course, new ArrayList<>());
    }

    public void addEdge(String course, String prerequisite) {
        adjacencyList.get(course).add(prerequisite);
    }

    public List<String> getPrerequisites(String course) {
        return adjacencyList.get(course);
    }

    public Set<String> getAllCourses() {
        return adjacencyList.keySet();
    }
}
