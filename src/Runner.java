/**
 * File: Runner.java
 * Description: This class contains the main method to run the degree planning application. 
 * It handles user input, validates course prerequisites, and generates a study plan.
 * Author: Sitthixay Kenpaseuth
 * Student ID: 110370389
 * Email ID: kensy027@mymail.unisa.edu.au
 * AI Tools Used: Copilot
 * This is my own work as defined by the University's Academic Integrity Policy.
 */
 import java.io.*;
 import java.util.*;
 
 public class Runner {
     public static void main(String[] args) {
         if (args.length != 2) {
             System.out.println("Usage: java Runner <filename> <course_type>");
             return;
         }
 
         String filename = args[0];
         String courseType = args[1].toUpperCase();
         int maxCourses = courseType.equals("XBIT") ? 22 : 23;
 
         System.out.println("Welcome to the program. Please note if you would like to partake in other study periods rather than 2 and 5, you may need to contact your course coordinator.");
 
         try (Scanner scanner = new Scanner(System.in)) {
             System.out.print("Enter the number of subjects you have completed: ");
             int completedSubjects = scanner.nextInt();
 
             if (completedSubjects >= maxCourses) {
                 System.out.println("You have completed all required courses.");
                 return;
             }
 
             Graph graph = FileReader.readGraphFromFile(filename);
 
             // Condition for 0 completed subjects
             if (completedSubjects == 0) {
                 List<String> availableCourses = getAvailableCoursesWithoutPrerequisites(graph);
                 System.out.println("You have not completed any courses. Here are the recommended courses for your first study period:");
                 for (String course : availableCourses) {
                     System.out.println(course);
                 }
                 return;
             }
 
             int upcomingStudyPeriod = 0;
             // Loop to ensure valid study period input
             while (true) {
                 System.out.print("Which study period are you headed towards (2 or 5)? ");
                 upcomingStudyPeriod = scanner.nextInt();
                 if (upcomingStudyPeriod == 2 || upcomingStudyPeriod == 5) {
                     break;
                 } else {
                     System.out.println("Invalid study period. Please enter 2 or 5.");
                 }
             }
 
             scanner.nextLine();
 
             List<String> completedCourseCodes = new ArrayList<>();
             while (true) {
                 System.out.println("Enter the course codes of the completed subjects, separated by commas: ");
                 String completedCourseCodesInput = scanner.nextLine();
                 List<String> inputCodes = Arrays.asList(completedCourseCodesInput.split("\\s*,\\s*"));
 
                 boolean allValid = true;
                 Set<String> validCourseCodes = new HashSet<>(graph.getAllCourses());
 
                 for (String code : inputCodes) {
                     String upperCaseCode = code.toUpperCase();
                     if (!validCourseCodes.contains(upperCaseCode)) {
                         System.out.println("Invalid course code: " + code);
                         allValid = false;
                         break;
                     } else {
                         completedCourseCodes.add(upperCaseCode);
                     }
                 }
 
                 if (allValid) {
                     if (arePrerequisitesSatisfied(graph, completedCourseCodes)) {
                         break;
                     } else {
                         completedCourseCodes.clear(); // Clear the list if prerequisites are not met
                         System.out.println("Some prerequisites are not satisfied. Please re-enter valid course codes.");
                     }
                 } else {
                     completedCourseCodes.clear(); // Clear the list if invalid code is found
                     System.out.println("Please re-enter valid course codes.");
                 }
             }
 
             int remainingCourses = maxCourses - completedSubjects;
 
             List<List<String>> studyPlan = Scheduler.scheduleCourses(graph, remainingCourses, completedCourseCodes);
             printStudyPlan(studyPlan, upcomingStudyPeriod);
 
         } catch (IOException e) {
             System.err.println("Error reading file: " + e.getMessage());
         } catch (Exception e) {
             System.err.println("An error occurred: " + e.getMessage());
         }
     }
 
     /**
      * Gets the list of courses that do not have prerequisites.
      *
      * @param graph the graph representing the courses and their prerequisites
      * @return a list of course codes that do not have prerequisites
      */
     private static List<String> getAvailableCoursesWithoutPrerequisites(Graph graph) {
         List<String> availableCourses = new ArrayList<>();
         for (Node node : graph.getAllNodes()) {
             if (node.getPrerequisites().isEmpty()) {
                 availableCourses.add(node.getCourseCode());
             }
         }
         return availableCourses;
     }
 
     /**
      * Prints the study plan.
      *
      * @param studyPlan a list of lists representing the study plan
      * @param initialStudyPeriod the initial study period (2 or 5)
      */
     private static void printStudyPlan(List<List<String>> studyPlan, int initialStudyPeriod) {
         int studyPeriod = initialStudyPeriod;
         for (List<String> semester : studyPlan) {
             System.out.println("Study Period " + studyPeriod);
             for (String course : semester) {
                 System.out.println("  " + course);
             }
             // Toggle study period between 2 and 5
             studyPeriod = (studyPeriod == 2) ? 5 : 2;
         }
     }
 
     /**
      * Checks if all prerequisites are satisfied for the completed courses.
      *
      * @param graph the graph representing the courses and their prerequisites
      * @param completedCourseCodes a list of completed course codes
      * @return true if all prerequisites are satisfied, false otherwise
      */
     private static boolean arePrerequisitesSatisfied(Graph graph, List<String> completedCourseCodes) {
         Set<String> completedSet = new HashSet<>(completedCourseCodes);
         for (String courseCode : completedCourseCodes) {
             Node courseNode = graph.getNode(courseCode);
             if (courseNode != null) {
                 if (!bfsPrerequisiteCheck(graph, courseNode, completedSet)) {
                     System.out.println("Prerequisite for course " + courseCode + " is not satisfied.");
                     return false;
                 }
             }
         }
         return true;
     }
 
     /**
      * Performs a BFS to check if all prerequisites are satisfied for a course.
      *
      * @param graph the graph representing the courses and their prerequisites
      * @param courseNode the course node to check prerequisites for
      * @param completedSet a set of completed course codes
      * @return true if all prerequisites are satisfied, false otherwise
      */
     private static boolean bfsPrerequisiteCheck(Graph graph, Node courseNode, Set<String> completedSet) {
         Queue<Node> queue = new LinkedList<>();
         Set<String> visited = new HashSet<>();
         queue.add(courseNode);
 
         while (!queue.isEmpty()) {
             Node current = queue.poll();
             visited.add(current.getCourseCode());
             for (Node prerequisite : current.getPrerequisites()) {
                 if (!completedSet.contains(prerequisite.getCourseCode())) {
                     System.out.println("Missing prerequisite: " + prerequisite.getCourseCode());
                     return false;
                 }
                 if (!visited.contains(prerequisite.getCourseCode())) {
                     queue.add(prerequisite);
                 }
             }
         }
         return true;
     }
 }
 