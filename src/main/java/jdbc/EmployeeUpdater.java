package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class EmployeeUpdater {
    public static void main(String[] args) throws SQLException {
        try(
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?serverTimezone=UTC", "root", "admin");
                Statement statement = connection.createStatement();
        ) {
            int updatedRows = statement.executeUpdate("UPDATE employees SET first_name = 'Peter' WHERE emp_no = 10001");
            System.out.println("Liczba zaktualizowanych wierszy: " + updatedRows);
        }
    }
}
