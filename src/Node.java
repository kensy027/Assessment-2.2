public class Node {
    private String course;
    private List<String> prerequisites;

    public Node(String course) {
        this.course = course;
        this.prerequisites = new ArrayList<>();
    }

    public String getCourse() {
        return course;
    }

    public void addPrerequisite(String prerequisite) {
        prerequisites.add(prerequisite);
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }
}
