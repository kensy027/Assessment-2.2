import java.util.*;

public class Scheduler {

    public static List<List<String>> scheduleCourses(Graph graph, int remainingCourses, List<String> completedCourseCodes) throws Exception {
        List<String> sortedCourses = topologicalSort(graph);

        // Validate completed courses
        Set<String> validCourseCodes = new HashSet<>(sortedCourses);
        for (String courseCode : completedCourseCodes) {
            if (!validCourseCodes.contains(courseCode.trim())) {
                throw new IllegalArgumentException("Invalid course code: " + courseCode);
            }
        }

        return createStudyPlan(graph, sortedCourses, remainingCourses, completedCourseCodes);
    }

    private static List<String> topologicalSort(Graph graph) throws Exception {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        for (Node node : graph.getAllNodes()) {
            if (!visited.contains(node)) {
                topologicalSortUtil(node, visited, stack);
            }
        }

        List<String> sortedList = new ArrayList<>();
        while (!stack.isEmpty()) {
            sortedList.add(stack.pop().getCourseCode());
        }
        return sortedList;
    }

    private static void topologicalSortUtil(Node node, Set<Node> visited, Stack<Node> stack) throws Exception {
        visited.add(node);
        for (Node neighbor : node.getPrerequisites()) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
        stack.push(node);
    }

    private static List<List<String>> createStudyPlan(Graph graph, List<String> sortedCourses, int remainingCourses, List<String> completedCourseCodes) {
        List<List<String>> studyPlan = new ArrayList<>();
        Set<String> completedSet = new HashSet<>(completedCourseCodes);

        while (remainingCourses > 0 && !sortedCourses.isEmpty()) {
            List<String> currentPeriod = new ArrayList<>();
            Iterator<String> iterator = sortedCourses.iterator();

            while (iterator.hasNext() && currentPeriod.size() < 4 && remainingCourses > 0) {
                String course = iterator.next();
                Node courseNode = graph.getNode(course);
                boolean prerequisitesMet = true;

                for (Node prerequisite : courseNode.getPrerequisites()) {
                    if (!completedSet.contains(prerequisite.getCourseCode())) {
                        prerequisitesMet = false;
                        break;
                    }
                }

                if (prerequisitesMet && !completedSet.contains(course)) {
                    currentPeriod.add(course);
                    completedSet.add(course);
                    iterator.remove();
                    remainingCourses--;
                }
            }

            if (!currentPeriod.isEmpty()) {
                studyPlan.add(currentPeriod);
            } else {
                // If no courses can be added in the current period due to unmet prerequisites, break the loop
                break;
            }
        }

        return studyPlan;
    }
}
