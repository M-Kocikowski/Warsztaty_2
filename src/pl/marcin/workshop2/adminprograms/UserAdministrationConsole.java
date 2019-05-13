package pl.marcin.workshop2.adminprograms;

import pl.marcin.workshop2.dao.User;
import pl.marcin.workshop2.dao.UsersDAO;
import pl.marcin.workshop2.utilities.GeneralMethods;

import java.util.Scanner;

public class UserAdministrationConsole {

    private static final String ADD_USER_COMMAND = "1";
    private static final String EDIT_USER_COMMAND = "2";
    private static final String DELETE_USER_COMMAND = "3";
    private static final String QUIT_COMMAND = "q";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            printUsersAndCommands();
            command = scanner.nextLine();

            if (command.equals(ADD_USER_COMMAND)) {
                addNewUser();
            } else if (command.equals(EDIT_USER_COMMAND)) {
                editExistingUser();
            } else if (command.equals(DELETE_USER_COMMAND)) {
                deleteExistingUser();
            } else if (!command.equals(QUIT_COMMAND))
                System.out.println("Unknown command: " + command);

        } while (!command.equals(QUIT_COMMAND));

        System.out.println("Quiting program...");

    }

    private static void addNewUser() {

        System.out.println("Adding new user...");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter new username:");
        String userName = scanner.nextLine();

        System.out.print("Please enter new user's email:");
        String email = scanner.nextLine();

        System.out.print("Please enter new password:");
        String password = scanner.nextLine();

        UsersDAO usersDAO = new UsersDAO();
        usersDAO.create(new User(userName, email, password));

    }

    private static void editExistingUser() {

        System.out.println("Editing...");

        User user = GeneralMethods.getUserFromConsole();
        if (user == null) return;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Modifying: " + user);
        System.out.println();
        System.out.print("Enter new username:");
        user.setUserName(scanner.nextLine());
        System.out.print("Enter new email address:");
        user.setEmail(scanner.nextLine());
        System.out.print("Enter new password:");
        user.setPassword(scanner.nextLine(), true);
        UsersDAO usersDAO = new UsersDAO();
        usersDAO.update(user);
    }

    private static void deleteExistingUser() {

        System.out.println("Deleting...");

        User user = GeneralMethods.getUserFromConsole();
        if (user == null) return;

        System.out.println();
        System.out.println("Are you sure you want to delete " + user + "? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().toUpperCase().equals("Y")){
            UsersDAO usersDAO = new UsersDAO();
            usersDAO.delete(user.getId());
        }
        else System.out.println("Record not deleted!");

    }

    private static void printUsersAndCommands() {

        GeneralMethods.printExistingUsers();

        System.out.println();
        System.out.println("User admin console. Choose option:\n" +
                "[1] - Add new user\n" +
                "[2] - Edit existing user\n" +
                "[3] - Delete existing user\n" +
                "[q] - Quit");

    }

}
