package pl.marcin.workshop2.adminprograms;

import pl.marcin.workshop2.dao.Exercise;
import pl.marcin.workshop2.dao.Solution;
import pl.marcin.workshop2.dao.SolutionsDAO;
import pl.marcin.workshop2.dao.User;
import pl.marcin.workshop2.utilities.GeneralMethods;

import java.util.List;
import java.util.Scanner;

public class SolutionAdministrationConsole {

    private static final String ASSIGN_USER_TO_EXERCISE_COMMAND = "1";
    private static final String VIEW_SOLUTIONS_BY_USER_COMMAND = "2";
    private static final String QUIT_COMMAND = "q";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            printCommands();
            command = scanner.nextLine();
            if (command.equals(ASSIGN_USER_TO_EXERCISE_COMMAND)) {
                assignUserToExercise();
            } else if (command.equals(VIEW_SOLUTIONS_BY_USER_COMMAND)) {
                viewSolutionsByUser();
            } else if (!command.equals(QUIT_COMMAND)) {
                System.out.println("Unknown command: " + command);
            }
        } while (!command.equals(QUIT_COMMAND));

        System.out.println("Quitting program...");
    }

    private static void assignUserToExercise() {

        System.out.println("Assigning user to exercise...");
        User userFromConsole = GeneralMethods.getUserFromConsole();
        if (userFromConsole == null) return;

        Exercise exerciseFromConsole = GeneralMethods.getExerciseFromConsole();
        if (exerciseFromConsole == null) return;

        SolutionsDAO solutionsDAO = new SolutionsDAO();
        solutionsDAO.create(new Solution(null, exerciseFromConsole.getId(), userFromConsole.getId()));
        System.out.println("Assigned " + userFromConsole + " to " + exerciseFromConsole);

    }

    private static void viewSolutionsByUser() {

        System.out.println("Viewing solutions...");
        User userFromConsole = GeneralMethods.getUserFromConsole();
        if (userFromConsole == null) return;

        SolutionsDAO solutionsDAO = new SolutionsDAO();
        List<Solution> solutionsByUser = solutionsDAO.findSolutionsByUser(userFromConsole.getId());
        if (solutionsByUser.isEmpty()) {
            System.out.println("No solutions found for " + userFromConsole);
        } else {
            System.out.println("Solutions for " + userFromConsole);
            for (Solution s : solutionsByUser) {
                System.out.println(s);
            }
        }

    }

    private static void printCommands() {

        System.out.println("Solution admin console. Choose option:\n" +
                "[1] - Assign user to exercise\n" +
                "[2] - View solution by user\n" +
                "[q] - Quit");
    }

}
