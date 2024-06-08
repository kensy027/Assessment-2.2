import java.io.*;
import java.util.*;

public class FileReader {
    public static Graph readGraphFromFile(String filename) throws IOException {
        Graph graph = new Graph();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filename))) {
            String line;
            List<String> courses = Arrays.asList(br.readLine().split(","));
            for (String course : courses) {
                graph.getOrCreateNode(course.trim());
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                String course = parts[0].trim();
                Node courseNode = graph.getOrCreateNode(course);
                if (parts.length > 1) {
                    String[] prerequisites = parts[1].split(",");
                    for (String prerequisite : prerequisites) {
                        Node prerequisiteNode = graph.getOrCreateNode(prerequisite.trim());
                        courseNode.addPrerequisite(prerequisiteNode);
                    }
                }
            }
        }
        return graph;
    }
}
