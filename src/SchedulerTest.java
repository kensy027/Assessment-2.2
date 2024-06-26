/**
 * File: SchedulerTest.java
 * Description: This class contains JUnit tests for the Scheduler class.
 * It verifies the correct scheduling of courses based on prerequisites,
 * including handling of cycles, completed courses, and various graph configurations.
 * Author: Sitthixay Kenpaseuth
 * Student ID: 110370389
 * Email ID: kensy027@mymail.unisa.edu.au
 * AI Tools Used: Copilot
 * This is my own work as defined by the University's Academic Integrity Policy.
 */

 import org.junit.jupiter.api.Test;
 import static org.junit.jupiter.api.Assertions.*;
 
 import java.util.List;
 import java.util.ArrayList;
 
 public class SchedulerTest {
 
     /**
      * Tests scheduling courses in a simple linear graph with no completed courses.
      */
     @Test
     void testScheduleCourses() {
         System.out.println("Running testScheduleCourses...");
         Graph graph = new Graph();
         Node a = graph.getOrCreateNode("A");
         Node b = graph.getOrCreateNode("B");
         Node c = graph.getOrCreateNode("C");
         Node d = graph.getOrCreateNode("D");
 
         b.addPrerequisite(a);
         c.addPrerequisite(b);
         d.addPrerequisite(c);
 
         List<String> completedCourses = new ArrayList<>();
 
         try {
             List<List<String>> schedule = Scheduler.scheduleCourses(graph, 4, completedCourses);
             assertNotNull(schedule, "Schedule should not be null");
             assertEquals(4, schedule.size(), "Schedule should contain 4 periods");
             assertEquals(List.of("A"), schedule.get(0), "First period should contain A");
             assertEquals(List.of("B"), schedule.get(1), "Second period should contain B");
             assertEquals(List.of("C"), schedule.get(2), "Third period should contain C");
             assertEquals(List.of("D"), schedule.get(3), "Fourth period should contain D");
         } catch (Exception e) {
             fail("Exception occurred during testScheduleCourses: " + e.getMessage());
         }
         System.out.println("Finished testScheduleCourses.");
     }
 
     /**
      * Tests scheduling courses when some courses are already completed.
      */
     @Test
     void testScheduleCoursesWithCompletedCourses() {
         System.out.println("Running testScheduleCoursesWithCompletedCourses...");
         Graph graph = new Graph();
         Node a = graph.getOrCreateNode("A");
         Node b = graph.getOrCreateNode("B");
         Node c = graph.getOrCreateNode("C");
         Node d = graph.getOrCreateNode("D");
 
         b.addPrerequisite(a);
         c.addPrerequisite(b);
         d.addPrerequisite(c);
 
         List<String> completedCourses = List.of("A", "B");
 
         try {
             List<List<String>> schedule = Scheduler.scheduleCourses(graph, 2, completedCourses);
             assertNotNull(schedule, "Schedule should not be null");
             assertEquals(2, schedule.size(), "Schedule should contain 2 periods");
             assertEquals(List.of("C"), schedule.get(0), "First period should contain C");
             assertEquals(List.of("D"), schedule.get(1), "Second period should contain D");
         } catch (Exception e) {
             fail("Exception occurred during testScheduleCoursesWithCompletedCourses: " + e.getMessage());
         }
         System.out.println("Finished testScheduleCoursesWithCompletedCourses.");
     }
 
     /**
      * Tests scheduling courses in a graph with a cycle, expecting an exception.
      */
     @Test
     void testScheduleCoursesWithCycle() {
         System.out.println("Running testScheduleCoursesWithCycle...");
         Graph graph = new Graph();
         Node a = graph.getOrCreateNode("A");
         Node b = graph.getOrCreateNode("B");
         Node c = graph.getOrCreateNode("C");
 
         b.addPrerequisite(a);
         c.addPrerequisite(b);
         a.addPrerequisite(c); // This creates a cycle
 
         List<String> completedCourses = new ArrayList<>();
 
         Exception exception = assertThrows(Exception.class, () -> {
             Scheduler.scheduleCourses(graph, 3, completedCourses);
         });
         assertEquals("There is a cycle in the graph", exception.getMessage(), "Exception message should be 'There is a cycle in the graph'");
         System.out.println("Finished testScheduleCoursesWithCycle.");
     }
 }
 