/**
 * File: FileReaderTest.java
 * Description: This class contains JUnit tests for the FileReader class.
 * It verifies the correct reading and parsing of graph data from files,
 * including handling of valid, invalid, and empty files.
 * Author: Sitthixay Kenpaseuth
 * Student ID: 110370389
 * Email ID: kensy027@mymail.unisa.edu.au
 * AI Tools Used: Copilot
 * This is my own work as defined by the University's Academic Integrity Policy.
 */

 import org.junit.jupiter.api.Test;
 import static org.junit.jupiter.api.Assertions.*;
 import java.io.*;
 
 public class FileReaderTest {
 
     /**
      * Tests reading a graph from a valid file with course and prerequisite data.
      */
     @Test
     void testReadGraphFromFile() {
         System.out.println("Running testReadGraphFromFile...");
         try {
             // Create a temporary file with test data
             File tempFile = File.createTempFile("testGraph", ".txt");
             try (FileWriter writer = new FileWriter(tempFile)) {
                 writer.write("A,B,C\nA,B\nB,C\nC\n");
             }
 
             Graph graph = FileReader.readGraphFromFile(tempFile.getAbsolutePath());
 
             assertNotNull(graph, "Graph should not be null");
             assertEquals(3, graph.getAllNodes().size(), "Graph should contain 3 nodes");
 
             Node a = graph.getNode("A");
             Node b = graph.getNode("B");
             Node c = graph.getNode("C");
 
             assertNotNull(a, "Node A should not be null");
             assertNotNull(b, "Node B should not be null");
             assertNotNull(c, "Node C should not be null");
 
             assertTrue(a.getPrerequisites().contains(b), "Node A should have B as a prerequisite");
             assertTrue(b.getPrerequisites().contains(c), "Node B should have C as a prerequisite");
             assertTrue(c.getPrerequisites().isEmpty(), "Node C should have no prerequisites");
 
         } catch (IOException e) {
             fail("IOException occurred during testReadGraphFromFile: " + e.getMessage());
         }
         System.out.println("Finished testReadGraphFromFile.");
     }
 
     /**
      * Tests reading a graph from a non-existent file, expecting an IOException.
      */
     @Test
     void testReadGraphFromFileWithInvalidFile() {
         System.out.println("Running testReadGraphFromFileWithInvalidFile...");
         Exception exception = assertThrows(IOException.class, () -> {
             FileReader.readGraphFromFile("nonexistentfile.txt");
         });
         assertTrue(exception.getMessage().contains("nonexistentfile.txt"));
         System.out.println("Finished testReadGraphFromFileWithInvalidFile.");
     }
 
     /**
      * Tests reading a graph from an empty file, expecting an empty graph.
      */
     @Test
     void testReadGraphFromFileWithEmptyFile() {
         System.out.println("Running testReadGraphFromFileWithEmptyFile...");
         try {
             // Create a temporary empty file
             File tempFile = File.createTempFile("emptyGraph", ".txt");
 
             Graph graph = FileReader.readGraphFromFile(tempFile.getAbsolutePath());
 
             assertNotNull(graph, "Graph should not be null");
             assertEquals(0, graph.getAllNodes().size(), "Graph should contain no nodes");
 
         } catch (IOException e) {
             fail("IOException occurred during testReadGraphFromFileWithEmptyFile: " + e.getMessage());
         }
         System.out.println("Finished testReadGraphFromFileWithEmptyFile.");
     }
 }
 