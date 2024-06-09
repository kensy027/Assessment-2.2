/**
 * File: FileReader.java
 * Description: This class is responsible for reading a graph representation of courses and their prerequisites from a file.
 * Author: Sitthixay Kenpaseuth
 * Student ID: 110370389
 * Email ID: kensy027@mymail.unisa.edu.au
 * AI Tools Used: Copilot
 * This is my own work as defined by the University's Academic Integrity Policy.
 */

 import java.io.*;
 import java.util.*;
 
 public class FileReader {
    /**
     * Reads the graph structure from a file and constructs a Graph object.
     *
     * @param filename the name of the file containing the graph data
     * @return a Graph object representing the courses and their prerequisites
     * @throws IOException if an I/O error occurs reading from the file
     */
    public static Graph readGraphFromFile(String filename) throws IOException {
        Graph graph = new Graph();
        Set<String> uniqueCourses = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filename))) {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                return graph; // Return an empty graph if the file is empty
            }
            List<String> courses = Arrays.asList(line.split(","));
            for (String course : courses) {
                uniqueCourses.add(course.trim());
            }
            for (String course : uniqueCourses) {
                graph.getOrCreateNode(course);
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