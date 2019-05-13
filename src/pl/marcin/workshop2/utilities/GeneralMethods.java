package pl.marcin.workshop2.utilities;

import pl.marcin.workshop2.dao.Exercise;
import pl.marcin.workshop2.dao.ExercisesDAO;
import pl.marcin.workshop2.dao.User;
import pl.marcin.workshop2.dao.UsersDAO;

import java.util.List;
import java.util.Scanner;

public class GeneralMethods {

    public static User getUserFromConsole() {

        if (!printExistingUsers()) return null;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please choose user ID:");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid number!");
            System.out.println("Please choose user ID:");
            scanner.nextLine();
        }
        int userId = scanner.nextInt();

        UsersDAO usersDAO = new UsersDAO();
        User read = usersDAO.read(userId);
        if (read == null) {
            System.out.println("User with ID: " + userId + " doesn't exist!");
            return null;
        }
        return read;
    }

    public static boolean printExistingUsers() {

        UsersDAO usersDAO = new UsersDAO();
        List<User> allUsers = usersDAO.findAllUsers();
        if (allUsers.isEmpty()) {
            System.out.println("No users exist.");
            return false;
        } else {
            System.out.println("Existing users: ");
            System.out.println();

            for (User u : allUsers) {
                System.out.println(u);
            }
            return true;

        }
    }

    public static boolean printExistingExercises() {

        ExercisesDAO exercisesDAO = new ExercisesDAO();
        List<Exercise> allExercises = exercisesDAO.findAllExercises();
        if (allExercises.isEmpty()) {
            System.out.println("No exercises exist.");
            return false;
        } else {
            System.out.println("Existing exercises: ");
            System.out.println();

            for (Exercise e : allExercises) {
                System.out.println(e);
            }
            return true;
        }
    }

    public static Exercise getExerciseFromConsole() {

        if (!printExistingExercises()) return null;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please choose exercise ID:");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid number!");
            System.out.println("Please choose exercise ID:");
            scanner.nextLine();
        }
        int exerciseId = scanner.nextInt();

        ExercisesDAO exercisesDAO = new ExercisesDAO();
        Exercise read = exercisesDAO.read(exerciseId);
        if (read == null) {
            System.out.println("Exercise with ID: " + exerciseId + " doesn't exist!");
            return null;
        }
        return read;
    }
}
