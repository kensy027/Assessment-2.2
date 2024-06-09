/**
 * File: Node.java
 * Description: This class represents a node in a graph, specifically a course and its prerequisites.
 * Author: Sitthixay Kenpaseuth
 * Student ID: 110370389
 * Email ID: kensy027@mymail.unisa.edu.au
 * AI Tools Used: Copilot
 * This is my own work as defined by the University's Academic Integrity Policy.
 */
 
 import java.util.*;

 public class Node {
     private String courseCode;
     private List<Node> prerequisites;
 
     /**
      * Constructs a new Node with the specified course code.
      *
      * @param courseCode the course code for this node
      */
     public Node(String courseCode) {
         this.courseCode = courseCode;
         this.prerequisites = new ArrayList<>();
     }
 
     /**
      * Gets the course code for this node.
      *
      * @return the course code
      */
     public String getCourseCode() {
         return courseCode;
     }
 
     /**
      * Adds a prerequisite to this course.
      *
      * @param node the prerequisite node
      */
     public void addPrerequisite(Node node) {
         prerequisites.add(node);
     }
 
     /**
      * Gets the list of prerequisites for this course.
      *
      * @return a list of prerequisite nodes
      */
     public List<Node> getPrerequisites() {
         return prerequisites;
     }
 }
 