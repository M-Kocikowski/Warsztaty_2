package pl.marcin.workshop2.adminprograms;

import pl.marcin.workshop2.dao.Exercise;
import pl.marcin.workshop2.dao.ExercisesDAO;
import pl.marcin.workshop2.utilities.GeneralMethods;

import java.util.Scanner;

public class ExerciseAdministrationConsole {

    private static final String ADD_EXERCISE_COMMAND = "1";
    private static final String EDIT_EXISTING_EXERCISE = "2";
    private static final String DELETE_EXISTING_EXERCISE = "3";
    private static final String QUIT_COMMAND = "q";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            printExercisesAndCommands();
            command = scanner.nextLine();
            if (command.equals(ADD_EXERCISE_COMMAND)) {
                addNewExercise();
            } else if (command.equals(EDIT_EXISTING_EXERCISE)) {
                editExistingExercise();
            } else if (command.equals(DELETE_EXISTING_EXERCISE)) {
                deleteExistingExercise();
            } else if (!command.equals(QUIT_COMMAND)) {
                System.out.println("Unknown command: " + command);
            }
        } while (!command.equals(QUIT_COMMAND));

        System.out.println("Quitting program...");

    }

    private static void addNewExercise() {

        System.out.println("Adding new exercise...");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter title:");
        String exerciseTitle = scanner.nextLine();

        System.out.print("Please enter description:");
        String exerciseDescription = scanner.nextLine();

        ExercisesDAO exercisesDAO = new ExercisesDAO();
        exercisesDAO.create(new Exercise(exerciseTitle, exerciseDescription));

    }

    private static void editExistingExercise() {

        System.out.println("Editing...");

        Exercise exerciseFromConsole = GeneralMethods.getExerciseFromConsole();
        if (exerciseFromConsole == null) return;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Modifying: " + exerciseFromConsole);
        System.out.println();
        System.out.print("Enter new title:");
        exerciseFromConsole.setTitle(scanner.nextLine());
        System.out.print("Enter new description:");
        exerciseFromConsole.setDescription(scanner.nextLine());
        ExercisesDAO exercisesDAO = new ExercisesDAO();
        exercisesDAO.update(exerciseFromConsole);

    }

    private static void deleteExistingExercise() {

        System.out.println("Deleting...");

        Exercise exerciseFromConsole = GeneralMethods.getExerciseFromConsole();
        if (exerciseFromConsole == null) return;

        System.out.println();
        System.out.println("Are you sure you want to delete " + exerciseFromConsole + "? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().toUpperCase().equals("Y")) {
            ExercisesDAO exercisesDAO = new ExercisesDAO();
            exercisesDAO.delete(exerciseFromConsole.getId());
        } else System.out.println("Record not deleted!");

    }

    private static void printExercisesAndCommands() {

        GeneralMethods.printExistingExercises();

        System.out.println();
        System.out.println("Exercise admin console. Choose option:\n" +
                "[1] - Add new exercise\n" +
                "[2] - Edit existing exercise\n" +
                "[3] - Delete existing exercise\n" +
                "[q] - Quit");

    }

}
