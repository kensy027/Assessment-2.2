import java.io.IOException;

public class Runner {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Runner <filename> <maxConcurrentCourses>");
            return;
        }

        String filename = args[0];
        int maxConcurrentCourses = Integer.parseInt(args[1]);

        try {
            Graph graph = GraphBuilder.buildGraph(filename);

            // Example using BFS (You can choose to implement a more complex algorithm if needed)
            List<String> courseOrder = graph.bfs("startCourse");  // Replace with the actual starting course
            int studyPeriod = 1;

            for (int i = 0; i < courseOrder.size(); i += maxConcurrentCourses) {
                System.out.println("Study Period " + studyPeriod + ": " + courseOrder.subList(i, Math.min(i + maxConcurrentCourses, courseOrder.size())));
                studyPeriod++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
