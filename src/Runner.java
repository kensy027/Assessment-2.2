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

            scanner.nextLine(); // Consume newline

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
                    break;
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

    private static List<String> getAvailableCoursesWithoutPrerequisites(Graph graph) {
        List<String> availableCourses = new ArrayList<>();
        for (Node node : graph.getAllNodes()) {
            if (node.getPrerequisites().isEmpty()) {
                availableCourses.add(node.getCourseCode());
            }
        }
        return availableCourses;
    }

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
}
