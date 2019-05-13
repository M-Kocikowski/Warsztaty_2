package pl.marcin.workshop2.adminprograms;

import pl.marcin.workshop2.dao.*;

import java.util.List;
import java.util.Scanner;

public class GroupAdministrationConsole {

    private static final String ADD_GROUP_COMMAND = "1";
    private static final String EDIT_GROUP_COMMAND = "2";
    private static final String DELETE_GROUP_COMMAND = "3";
    private static final String QUIT_COMMAND = "q";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            printGroupsAndCommands();
            command = scanner.nextLine();

            if (command.equals(ADD_GROUP_COMMAND)) {
                addNewGroup();
            } else if (command.equals(EDIT_GROUP_COMMAND)) {
                editExistingGroup();
            } else if (command.equals(DELETE_GROUP_COMMAND)) {
                deleteExistingGroup();
            } else if (!command.equals(QUIT_COMMAND))
                System.out.println("Unknown command: " + command);

        } while (!command.equals(QUIT_COMMAND));

        System.out.println("Quiting program...");

    }

    private static void addNewGroup() {

        System.out.println("Adding new group...");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter name of new group:");
        String groupName = scanner.nextLine();

        GroupsDAO groupsDAO = new GroupsDAO();
        groupsDAO.create(new Group(groupName));

    }

    private static void editExistingGroup() {

        System.out.println("Editing...");

        Group group = retrieveGroup();
        if (group == null) return;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Modifying: " + group);
        System.out.println();
        System.out.print("Enter new group name:");
        group.setName(scanner.nextLine());
        GroupsDAO groupsDAO = new GroupsDAO();
        groupsDAO.update(group);

    }

    private static void deleteExistingGroup() {

        System.out.println("Deleting...");

        Group group = retrieveGroup();
        if (group == null) return;

        System.out.println();
        System.out.println("Are you sure you want to delete " + group + "? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().toUpperCase().equals("Y")) {
            GroupsDAO groupsDAO = new GroupsDAO();
            groupsDAO.delete(group.getId());
        } else System.out.println("Record not deleted!");
    }

    private static void printGroupsAndCommands() {

        printExistingGroups();

        System.out.println();
        System.out.println("Group admin console. Choose option:\n" +
                "[1] - Add new group\n" +
                "[2] - Edit existing group\n" +
                "[3] - Delete existing group\n" +
                "[q] - Quit");

    }

    private static boolean printExistingGroups() {

        GroupsDAO groupsDAO = new GroupsDAO();
        List<Group> groupList = groupsDAO.findAllGroups();
        if (groupList.isEmpty()){
            System.out.println("No groups exist.");
            return false;
        }else {

            System.out.println("Existing groups:");
            System.out.println();

            for (Group g : groupList) {
                System.out.println(g);
            }
            return true;
        }

    }

    private static Group retrieveGroup() {

        if (!printExistingGroups()) return null;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter group ID:");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid number!");
            System.out.println("Please enter group ID:");
            scanner.nextLine();
        }
        int groupId = scanner.nextInt();

        GroupsDAO groupsDAO = new GroupsDAO();
        Group read = groupsDAO.read(groupId);
        if (read == null) {
            System.out.println("Group with ID: " + groupId + " doesn't exist!");
            return null;
        }
        return read;
    }

}
