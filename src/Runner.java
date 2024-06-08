import java.io.*;
import java.util.*;

public class Runner {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Runner <filename> <max_courses_per_semester>");
            return;
        }

        String filename = args[0];

        System.out.println("Welcome to the program. Please note if you would like to partake in other study periods rather than 2 and 5, you may need to contact your course coordinator.");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of subjects you have completed: ");
            int completedSubjects = scanner.nextInt();

            if (completedSubjects == 22) {
                System.out.println("You have completed all required courses.");
                return;
            }

            System.out.print("Which study period are you headed towards (2 or 5)? ");
            int upcomingStudyPeriod = scanner.nextInt();
            if (upcomingStudyPeriod != 2 && upcomingStudyPeriod != 5) {
                System.out.println("Invalid study period. Please enter 2 or 5.");
                return;
            }

            scanner.nextLine(); // Consume newline
            System.out.println("Enter the course codes of the completed subjects, separated by commas: ");
            String completedCourseCodesInput = scanner.nextLine();
            List<String> completedCourseCodes = Arrays.asList(completedCourseCodesInput.split("\\s*,\\s*"));

            int totalCourses = 22;
            int remainingCourses = totalCourses - completedSubjects;

            Graph graph = FileReader.readGraphFromFile(filename);
            List<List<String>> studyPlan = Scheduler.scheduleCourses(graph, remainingCourses, completedCourseCodes);
            printStudyPlan(studyPlan, upcomingStudyPeriod);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error scheduling courses: " + e.getMessage());
        }
    }

    private static void printStudyPlan(List<List<String>> studyPlan, int upcomingStudyPeriod) {
        if (studyPlan.isEmpty()) {
            System.out.println("No study plan available.");
            return;
        }

        int periodCounter = (upcomingStudyPeriod == 5) ? 0 : 1; // Start with Study Period 5 if heading to Study Period 2

        String[] periodLabels = {"Study Period 5", "Study Period 2"};

        for (List<String> periodCourses : studyPlan) {
            System.out.println(periodLabels[periodCounter % periodLabels.length]);
            for (String course : periodCourses) {
                System.out.println("  " + course);
            }
            periodCounter++;
        }
    }
}
