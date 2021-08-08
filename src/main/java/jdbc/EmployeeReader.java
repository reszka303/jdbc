package jdbc;

import java.sql.*;

class EmployeeReader {
    public static void main(String[] args) throws SQLException {
        try(
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?serverTimezone=UTC", "root", "admin");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT first_name, last_name, hire_date FROM employees LIMIT 10");
        ) {
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date hireDate = resultSet.getDate("hire_date");
                System.out.printf("%s %s - data zatrudnienia: %s\n", firstName, lastName, hireDate.toString());
            }
        }
    }
}
