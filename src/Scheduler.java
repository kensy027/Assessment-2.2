import java.util.*;

public class Scheduler {

    public static List<List<String>> scheduleCourses(Graph graph, int remainingCourses, List<String> completedCourseCodes) throws Exception {
        List<String> sortedCourses = topologicalSort(graph);
        
        // Validate completed courses
        Set<String> validCourseCodes = new HashSet<>(sortedCourses);
        for (String courseCode : completedCourseCodes) {
            if (!validCourseCodes.contains(courseCode)) {
                throw new IllegalArgumentException("Invalid course code: " + courseCode);
            }
        }

        return createStudyPlan(sortedCourses, remainingCourses, completedCourseCodes);
    }

    private static List<String> topologicalSort(Graph graph) throws Exception {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        for (Node node : graph.getAllNodes()) {
            if (!visited.contains(node)) {
                topologicalSortUtil(node, visited, stack);
            }
        }
        List<String> sortedCourses = new ArrayList<>();
        while (!stack.isEmpty()) {
            sortedCourses.add(stack.pop().getCourseCode());
        }
        return sortedCourses;
    }

    private static void topologicalSortUtil(Node node, Set<Node> visited, Stack<Node> stack) throws Exception {
        visited.add(node);
        for (Node prerequisite : node.getPrerequisites()) {
            if (!visited.contains(prerequisite)) {
                topologicalSortUtil(prerequisite, visited, stack);
            }
        }
        stack.push(node);
    }

    private static List<List<String>> createStudyPlan(List<String> sortedCourses, int remainingCourses, List<String> completedCourseCodes) {
        List<List<String>> studyPlan = new ArrayList<>();
        List<String> firstYearCourses = new ArrayList<>();
        List<String> secondYearCourses = new ArrayList<>();
        List<String> thirdYearCourses = new ArrayList<>();

        // Categorize courses by year
        for (String course : sortedCourses) {
            if (course.matches("^INFS1.*") || course.matches("^INFT1.*") || course.matches("^COMP1.*") || course.matches("^MATH1.*")) {
                firstYearCourses.add(course);
            } else if (course.matches("^INFS2.*") || course.matches("^INFT2.*") || course.matches("^COMP2.*") || course.matches("^MATH2.*")) {
                secondYearCourses.add(course);
            } else if (course.matches("^INFS3.*") || course.matches("^INFT3.*") || course.matches("^COMP3.*")) {
                thirdYearCourses.add(course);
            }
        }

        // Debug print statements
        System.out.println("First Year Courses: " + firstYearCourses);
        System.out.println("Second Year Courses: " + secondYearCourses);
        System.out.println("Third Year Courses: " + thirdYearCourses);

        // Remove completed courses
        firstYearCourses.removeAll(completedCourseCodes);
        secondYearCourses.removeAll(completedCourseCodes);
        thirdYearCourses.removeAll(completedCourseCodes);

        // Fill the study periods ensuring each study period has exactly 4 courses
        while (remainingCourses > 0) {
            List<String> currentPeriod = new ArrayList<>();
            for (int i = 0; i < 4 && remainingCourses > 0; i++) {
                if (!firstYearCourses.isEmpty()) {
                    currentPeriod.add(firstYearCourses.remove(0));
                } else if (!secondYearCourses.isEmpty()) {
                    currentPeriod.add(secondYearCourses.remove(0));
                } else if (!thirdYearCourses.isEmpty()) {
                    currentPeriod.add(thirdYearCourses.remove(0));
                }
                remainingCourses--;
            }
            studyPlan.add(currentPeriod);
        }

        return studyPlan;
    }
}
