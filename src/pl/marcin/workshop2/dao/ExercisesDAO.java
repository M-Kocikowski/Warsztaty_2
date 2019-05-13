package pl.marcin.workshop2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExercisesDAO {

    private static final String INSERT_EXERCISE = "INSERT INTO exercises(title, description) VALUES (?, ?)";
    private static final String SELECT_EXERCISE = "SELECT id, title, description FROM exercises WHERE id = ?";
    private static final String UPDATE_EXERCISE = "UPDATE exercises SET title = ?, description = ? WHERE id = ?";
    private static final String DELETE_EXERCISE = "DELETE FROM exercises WHERE id = ?";
    private static final String SELECT_ALL_EXERCISES = "SELECT * FROM exercises";

    public Exercise create(Exercise exercise) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EXERCISE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, exercise.getTitle());
            preparedStatement.setString(2, exercise.getDescription());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                exercise.setId(generatedKeys.getInt(1));
            }
            System.out.println("Created new exercise: " + exercise.getTitle() + " with ID: " + exercise.getId());
            return exercise;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise read(int id) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXERCISE);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getExerciseFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Exercise exercise) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXERCISE);
            preparedStatement.setString(1, exercise.getTitle());
            preparedStatement.setString(2, exercise.getDescription());
            preparedStatement.setInt(3, exercise.getId());
            preparedStatement.executeUpdate();
            System.out.println("Updated exercise with ID: " + exercise.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()) {

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EXERCISE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Deleted exercise with ID: " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exercise> findAllExercises(){

        try (Connection connection = SQLConnection.connectToProgrammingSchoolDatabase()){

            List<Exercise> allExercises = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_EXERCISES);

            while (resultSet.next()){
                Exercise exercise = getExerciseFromResultSet(resultSet);
                allExercises.add(exercise);
            }
            return allExercises;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Exercise getExerciseFromResultSet(ResultSet rs) throws SQLException {
        Exercise exercise = new Exercise();
        exercise.setId(rs.getInt("id"));
        exercise.setTitle(rs.getString("title"));
        exercise.setDescription(rs.getString("description"));
        return exercise;
    }
}
