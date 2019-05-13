package pl.marcin.workshop2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class SQLConnection {

    static Connection connectToProgrammingSchoolDatabase() throws SQLException {

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/programming_school?useSSl=false&characterEncoding=utf8&serverTimezone=UTC",
                "root",
                "coderslab");
    }

}
