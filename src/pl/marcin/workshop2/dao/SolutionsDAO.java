package pl.marcin.workshop2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolutionsDAO {


    private static final String INSERT_SOLUTION = "INSERT INTO solutions(created, description, exercise_id, user_id) VALUES(NOW(), ?, ?, ?)";
    private static final String SELECT_SOLUTION = "SELECT id, created, updated, description, exercise_id, user_id FROM solutions WHERE id = ?";
    private static final String UPDATE_SOLUTION = "UPDATE solutions SET updated = NOW(), description = ? WHERE id = ?";
    private static final String DELETE_SOLUTION = "DELETE FROM solutions WHERE id = ?";
    private static final String SELECT_ALL_SOLUTIONS = "SELECT * FROM solutions";
    private static final String SELECT_SOLUTIONS_BY_USER = "SELECT * FROM solutions WHERE user_id = ?";

    public Solution create(Solution solution) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SOLUTION, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, solution.getDescription());
            preparedStatement.setInt(2, solution.getExercise_id());
            preparedStatement.setInt(3, solution.getUser_id());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                solution.setId(generatedKeys.getInt(1));
            }
            System.out.println("Created new solution with ID: " + solution.getId());
            return solution;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Solution read(int id) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SOLUTION);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getSolutionFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Solution solution) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SOLUTION);
            preparedStatement.setString(1, solution.getDescription());
            preparedStatement.setInt(2, solution.getId());
            preparedStatement.executeUpdate();
            System.out.println("Updated solution with ID: " + solution.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SOLUTION);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Deleted solution with ID: " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Solution> findAllSolutions() {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            List<Solution> allSolutions = new ArrayList<>();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SOLUTIONS);
            while (resultSet.next()) {
                Solution solution = getSolutionFromResultSet(resultSet);
                allSolutions.add(solution);
            }
            return allSolutions;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Solution> findSolutionsByUser(int userId){

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()){

            List<Solution> userSolutions = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SOLUTIONS_BY_USER);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Solution solution = getSolutionFromResultSet(resultSet);
                userSolutions.add(solution);
            }
            return userSolutions;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Solution getSolutionFromResultSet(ResultSet rs) throws SQLException {
        Solution solution = new Solution();
        solution.setId(rs.getInt("id"));
        solution.setCreated(rs.getString("created"));
        solution.setUpdated(rs.getString("updated"));
        solution.setDescription(rs.getString("description"));
        solution.setExercise_id(rs.getInt("exercise_id"));
        solution.setUser_id(rs.getInt("user_id"));
        return solution;
    }
}
