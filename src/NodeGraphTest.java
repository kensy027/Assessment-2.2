import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class NodeGraphTest {

    @Test
    void testNodeCreation() {
        try {
            Node node = new Node("TEST101");
            System.out.println("Created node with course code: " + node.getCourseCode());
            assertNotNull(node);
            assertEquals("TEST101", node.getCourseCode());
        } catch (Exception e) {
            fail("Exception occurred during testNodeCreation: " + e.getMessage());
        }
    }

    @Test
    void testAddPrerequisite() {
        try {
            Node node = new Node("TEST101");
            Node prerequisite = new Node("PRE101");
            node.addPrerequisite(prerequisite);
            System.out.println("Added prerequisite: " + prerequisite.getCourseCode() + " to node: " + node.getCourseCode());
            assertTrue(node.getPrerequisites().contains(prerequisite));
        } catch (Exception e) {
            fail("Exception occurred during testAddPrerequisite: " + e.getMessage());
        }
    }

    @Test
    void testGetOrCreateNode() {
        try {
            Graph graph = new Graph();
            Node node = graph.getOrCreateNode("TEST101");
            System.out.println("Retrieved or created node with course code: " + node.getCourseCode());
            assertNotNull(node);
            assertEquals("TEST101", node.getCourseCode());
        } catch (Exception e) {
            fail("Exception occurred during testGetOrCreateNode: " + e.getMessage());
        }
    }

    @Test
    void testAddAndGetNode() {
        try {
            Graph graph = new Graph();
            Node node = graph.getOrCreateNode("TEST101");
            System.out.println("Node added and retrieved with course code: " + node.getCourseCode());
            assertEquals(node, graph.getNode("TEST101"));
        } catch (Exception e) {
            fail("Exception occurred during testAddAndGetNode: " + e.getMessage());
        }
    }

    @Test
    void testNoDuplicateNodes() {
        try {
            Graph graph = new Graph();
            Node node1 = graph.getOrCreateNode("TEST101");
            Node node2 = graph.getOrCreateNode("TEST101");
            System.out.println("No duplicate nodes created for course code: TEST101");
            assertSame(node1, node2);
        } catch (Exception e) {
            fail("Exception occurred during testNoDuplicateNodes: " + e.getMessage());
        }
    }

    @Test
    void testBfs() {
        try {
            Graph graph = new Graph();
            Node a = graph.getOrCreateNode("A");
            Node b = graph.getOrCreateNode("B");
            Node c = graph.getOrCreateNode("C");

            a.addPrerequisite(b);
            b.addPrerequisite(c);

            List<String> bfsResult = graph.bfs("A");
            System.out.println("BFS result starting from A: " + bfsResult);
            assertEquals(Arrays.asList("A", "B", "C"), bfsResult);
        } catch (Exception e) {
            fail("Exception occurred during testBfs: " + e.getMessage());
        }
    }

    @Test
    void testDfs() {
        try {
            Graph graph = new Graph();
            Node a = graph.getOrCreateNode("A");
            Node b = graph.getOrCreateNode("B");
            Node c = graph.getOrCreateNode("C");

            a.addPrerequisite(b);
            b.addPrerequisite(c);

            List<String> dfsResult = graph.dfs("A");
            System.out.println("DFS result starting from A: " + dfsResult);
            assertEquals(Arrays.asList("A", "B", "C"), dfsResult);
        } catch (Exception e) {
            fail("Exception occurred during testDfs: " + e.getMessage());
        }
    }

    @Test
    void testCycleInGraph() {
        try {
            Graph graph = new Graph();
            Node a = graph.getOrCreateNode("A");
            Node b = graph.getOrCreateNode("B");
            Node c = graph.getOrCreateNode("C");

            a.addPrerequisite(b);
            b.addPrerequisite(c);
            c.addPrerequisite(a); // Creating a cycle

            // Check that the cycle is correctly set up
            System.out.println("Checking cycle setup in graph");
            assertTrue(a.getPrerequisites().contains(b));
            assertTrue(b.getPrerequisites().contains(c));
            assertTrue(c.getPrerequisites().contains(a));

            // Check DFS results
            List<String> dfsResult = graph.dfs("A");
            System.out.println("DFS result in cyclic graph starting from A: " + dfsResult);
            assertEquals(Arrays.asList("A", "B", "C"), dfsResult);
        } catch (Exception e) {
            fail("Exception occurred during testCycleInGraph: " + e.getMessage());
        }
    }

    @Test
    void testDisconnectedGraph() {
        try {
            Graph graph = new Graph();
            Node a = graph.getOrCreateNode("A");
            Node b = graph.getOrCreateNode("B");
            Node c = graph.getOrCreateNode("C");
            Node d = graph.getOrCreateNode("D");

            a.addPrerequisite(b);
            c.addPrerequisite(d);

            List<String> bfsResultA = graph.bfs("A");
            List<String> bfsResultC = graph.bfs("C");

            System.out.println("BFS result for disconnected graph starting from A: " + bfsResultA);
            System.out.println("BFS result for disconnected graph starting from C: " + bfsResultC);

            assertEquals(Arrays.asList("A", "B"), bfsResultA);
            assertEquals(Arrays.asList("C", "D"), bfsResultC);
        } catch (Exception e) {
            fail("Exception occurred during testDisconnectedGraph: " + e.getMessage());
        }
    }

    @Test
    void testEmptyGraph() {
        try {
            Graph graph = new Graph();
            List<String> bfsResult = graph.bfs("A");
            List<String> dfsResult = graph.dfs("A");

            System.out.println("BFS result for empty graph starting from A: " + bfsResult);
            System.out.println("DFS result for empty graph starting from A: " + dfsResult);

            assertTrue(bfsResult.isEmpty());
            assertTrue(dfsResult.isEmpty());
        } catch (Exception e) {
            fail("Exception occurred during testEmptyGraph: " + e.getMessage());
        }
    }

    @Test
    void testSingleNodeGraph() {
        try {
            Graph graph = new Graph();
            graph.getOrCreateNode("A");

            List<String> bfsResult = graph.bfs("A");
            List<String> dfsResult = graph.dfs("A");

            System.out.println("BFS result for single node graph starting from A: " + bfsResult);
            System.out.println("DFS result for single node graph starting from A: " + dfsResult);

            assertEquals(Arrays.asList("A"), bfsResult);
            assertEquals(Arrays.asList("A"), dfsResult);
        } catch (Exception e) {
            fail("Exception occurred during testSingleNodeGraph: " + e.getMessage());
        }
    }
}
