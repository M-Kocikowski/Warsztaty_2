package pl.marcin.workshop2.userprograms;

import pl.marcin.workshop2.dao.Solution;
import pl.marcin.workshop2.dao.SolutionsDAO;
import pl.marcin.workshop2.dao.User;
import pl.marcin.workshop2.dao.UsersDAO;

import java.util.List;
import java.util.Scanner;

public class SolutionProgram {

    private static final String ADD_SOLUTION_COMMAND = "1";
    private static final String VIEW_SOLUTIONS_COMMAND = "2";
    private static final String QUIT_COMMAND = "q";

    public static void main(String[] args) {

        int userId;
        try {
            userId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input parameter!");
            return;
        }

        UsersDAO usersDAO = new UsersDAO();
        User user = usersDAO.read(userId);
        if (user == null) {
            System.out.println("User with ID: " + userId + " doesn't exist");
            return;
        } else System.out.println("Welcome " + user);

        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            printCommands();
            command = scanner.nextLine();
            if (command.equals(ADD_SOLUTION_COMMAND)) {
                addSolution(userId);
            } else if (command.equals(VIEW_SOLUTIONS_COMMAND)) {
                viewSolutions(userId);
            } else if (!command.equals(QUIT_COMMAND)) {
                System.out.println("Unknown command: " + command);
            }

        } while (!command.equals(QUIT_COMMAND));

        System.out.println("Quitting program...");
    }

    private static void addSolution(int userId) {

        if (!printUserSolutions(userId)) return;

        Solution solutionFromConsole = getSolutionFromConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add solution description:");
        solutionFromConsole.setDescription(scanner.nextLine());
        SolutionsDAO solutionsDAO = new SolutionsDAO();
        solutionsDAO.update(solutionFromConsole);

    }

    private static void viewSolutions(int userId) {
        printUserSolutions(userId);
    }

    private static Solution getSolutionFromConsole() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please choose solution ID:");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid number!");
            System.out.println("Please choose solution ID:");
            scanner.nextLine();
        }
        int solutionId = scanner.nextInt();

        SolutionsDAO solutionsDAO = new SolutionsDAO();
        Solution read = solutionsDAO.read(solutionId);
        if (read == null) {
            System.out.println("Solution with ID: " + solutionId + " doesn't exist!");
            return null;
        }
        return read;


    }

    private static boolean printUserSolutions(int userId) {

        SolutionsDAO solutionsDAO = new SolutionsDAO();
        List<Solution> solutionsByUser = solutionsDAO.findSolutionsByUser(userId);
        if (solutionsByUser.isEmpty()) {
            System.out.println("No exercises assigned to user.");
            return false;
        } else {
            for (Solution s : solutionsByUser){
                System.out.println(s);
            }
            return true;
        }

    }

    private static void printCommands() {

        System.out.println("Solution console. Choose option:\n" +
                "[1] - Add solution\n" +
                "[2] - View own solutions\n" +
                "[q] - Quit");
    }
}
