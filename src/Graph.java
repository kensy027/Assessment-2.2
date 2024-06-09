/**
 * File: Graph.java
 * Description: This class represents a graph data structure using adjacency lists. 
 * It includes methods for creating nodes, getting nodes, retrieving all nodes, 
 * and performing BFS and DFS traversals.
 * Author: Sitthixay Kenpaseuth
 * Student ID: 110370389
 * Email ID: kensy027@mymail.unisa.edu.au
 * AI Tools Used: Copilot
 * This is my own work as defined by the University's Academic Integrity Policy.
 */
 import java.util.*;

 public class Graph {
     private Map<String, Node> nodes;
 
     /**
      * Constructs a new Graph with an empty set of nodes.
      */
     public Graph() {
         nodes = new HashMap<>();
     }
 
     /**
      * Gets or creates a node in the graph.
      *
      * @param courseCode the course code for the node
      * @return the Node object for the course code
      */
     public Node getOrCreateNode(String courseCode) {
         Node node = nodes.get(courseCode);
         if (node == null) {
             node = new Node(courseCode);
             nodes.put(courseCode, node);
         }
         return node;
     }
 
     /**
      * Gets a node from the graph.
      *
      * @param courseCode the course code for the node
      * @return the Node object for the course code, or null if not found
      */
     public Node getNode(String courseCode) {
         return nodes.get(courseCode);
     }
 
     /**
      * Gets all nodes from the graph.
      *
      * @return a collection of all nodes in the graph
      */
     public Collection<Node> getAllNodes() {
         return nodes.values();
     }
 
     /**
      * Gets all course codes from the graph.
      *
      * @return a list of all course codes in the graph
      */
     public List<String> getAllCourses() {
         List<String> allCourses = new ArrayList<>();
         for (Node node : getAllNodes()) {
             allCourses.add(node.getCourseCode());
         }
         return allCourses;
     }
 
     /**
      * Performs a breadth-first search (BFS) starting from the specified course.
      *
      * @param startCourse the starting course code
      * @return a list of course codes in the order they were visited
      */
     public List<String> bfs(String startCourse) {
         List<String> result = new ArrayList<>();
         Set<String> visited = new HashSet<>();
         Queue<Node> queue = new LinkedList<>();
         Node startNode = getNode(startCourse);
 
         if (startNode == null) {
             return result; // Course not found
         }
 
         queue.add(startNode);
         visited.add(startCourse);
 
         while (!queue.isEmpty()) {
             Node current = queue.poll();
             result.add(current.getCourseCode());
 
             for (Node neighbor : current.getPrerequisites()) {
                 if (!visited.contains(neighbor.getCourseCode())) {
                     queue.add(neighbor);
                     visited.add(neighbor.getCourseCode());
                 }
             }
         }
 
         return result;
     }
 
     /**
      * Performs a depth-first search (DFS) starting from the specified course.
      *
      * @param startCourse the starting course code
      * @return a list of course codes in the order they were visited
      */
     public List<String> dfs(String startCourse) {
         List<String> result = new ArrayList<>();
         Set<String> visited = new HashSet<>();
         Node startNode = getNode(startCourse);
 
         if (startNode == null) {
             return result; // Course not found
         }
 
         dfsUtil(startNode, visited, result);
         return result;
     }
 
     /**
      * Utility method for performing DFS traversal.
      *
      * @param node the current node being visited
      * @param visited a set of visited nodes to avoid cycles
      * @param result a list of course codes in the order they were visited
      */
     private void dfsUtil(Node node, Set<String> visited, List<String> result) {
         visited.add(node.getCourseCode());
         result.add(node.getCourseCode());
 
         for (Node neighbor : node.getPrerequisites()) {
             if (!visited.contains(neighbor.getCourseCode())) {
                 dfsUtil(neighbor, visited, result);
             }
         }
     }
 }
 