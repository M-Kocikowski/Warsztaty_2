package pl.marcin.workshop2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupsDAO {

    private static final String INSERT_GROUP = "INSERT INTO groups(name) VALUES (?)";
    private static final String SELECT_GROUP = "SELECT id, name FROM groups WHERE id = ?";
    private static final String UPDATE_GROUP = "UPDATE groups SET name = ? WHERE id = ?";
    private static final String DELETE_GROUP = "DELETE FROM groups WHERE id = ?";
    private static final String SELECT_ALL_GROUPS = "SELECT * FROM groups";

    public Group create(Group group) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, group.getName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                group.setId(generatedKeys.getInt(1));
            }
            System.out.println("Created new group: " + group.getName() + " with ID: " + group.getId());
            return group;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Group read(int id) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GROUP);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getGroupFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Group group) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GROUP);
            preparedStatement.setString(1, group.getName());
            preparedStatement.setInt(2, group.getId());
            preparedStatement.executeUpdate();
            System.out.println("Updated group with ID: " + group.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GROUP);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Deleted group with ID: " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Group> findAllGroups(){

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()){

            List<Group> allGroups = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_GROUPS);

            while (resultSet.next()){
                Group group = getGroupFromResultSet(resultSet);
                allGroups.add(group);
            }
            return allGroups;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Group getGroupFromResultSet(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getInt("id"));
        group.setName(rs.getString("name"));
        return group;
    }
}
