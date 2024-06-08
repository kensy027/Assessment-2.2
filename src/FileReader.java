import java.io.*;
import java.util.*;

public class FileReader {
    public static Graph readGraphFromFile(String filename) throws IOException {
        Graph graph = new Graph();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filename))) {
            String line;
            List<String> courses = Arrays.asList(br.readLine().split(","));
            for (String course : courses) {
                graph.getOrCreateNode(course);
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Node course = graph.getOrCreateNode(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    Node prerequisite = graph.getOrCreateNode(parts[i]);
                    course.addPrerequisite(prerequisite);
                }
            }
        }
        return graph;
    }
}
