import java.util.*;

public class Node {
    private String courseCode;
    private List<Node> prerequisites;

    public Node(String courseCode) {
        this.courseCode = courseCode;
        this.prerequisites = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void addPrerequisite(Node node) {
        prerequisites.add(node);
    }

    public List<Node> getPrerequisites() {
        return prerequisites;
    }
}
