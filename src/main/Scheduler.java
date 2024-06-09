/**
 * File: Scheduler.java
 * Description: This class is responsible for scheduling courses based on their prerequisites.
 * Author: Sitthixay Kenpaseuth
 * Student ID: 110370389
 * Email ID: kensy027@mymail.unisa.edu.au
 * AI Tools Used: Copilot
 * This is my own work as defined by the University's Academic Integrity Policy.
 */
 package main;
 import java.util.*;

 public class Scheduler {
 
     /**
      * Schedules courses based on their prerequisites and completed courses.
      *
      * @param graph the graph representing the courses and their prerequisites
      * @param remainingCourses the number of remaining courses to schedule
      * @param completedCourseCodes a list of completed course codes
      * @return a list of lists representing the study plan
      * @throws Exception if an error occurs during scheduling
      */
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
 
     /**
      * Performs a topological sort on the graph to determine the order of courses.
      * This method uses Depth-First Search (DFS) to perform the sorting.
      *
      * @param graph the graph representing the courses and their prerequisites
      * @return a list of course codes in topologically sorted order
      * @throws Exception if an error occurs during sorting
      */
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
 
     /**
      * Utility method for performing topological sort using Depth-First Search (DFS).
      *
      * @param node the current node being visited
      * @param visited a set of visited nodes to avoid cycles
      * @param stack a stack to hold the sorted nodes
      * @throws Exception if an error occurs during sorting
      */
     private static void topologicalSortUtil(Node node, Set<Node> visited, Stack<Node> stack) throws Exception {
         visited.add(node);
         for (Node neighbor : node.getPrerequisites()) {
             if (!visited.contains(neighbor)) {
                 topologicalSortUtil(neighbor, visited, stack);
             }
         }
         stack.push(node);
     }
 
     /**
      * Creates a study plan based on the sorted courses and completed courses.
      *
      * @param graph the graph representing the courses and their prerequisites
      * @param sortedCourses a list of course codes in topologically sorted order
      * @param remainingCourses the number of remaining courses to schedule
      * @param completedCourseCodes a list of completed course codes
      * @return a list of lists representing the study plan
      */
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
 