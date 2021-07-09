import java.sql.*;
import java.util.Scanner;

import static main.SQL.*;


public class Main {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./tasks";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Scanner scanner = new Scanner(System.in);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            prepareDatabase(connection);
            workWithConnection(connection);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    private static void prepareDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {

            int dropTasks = statement.executeUpdate(DROP_TABLE_TASKS);
            int updateTasks = statement.executeUpdate(CREATE_TABLE_TASKS);
            int insertTasks = statement.executeUpdate(INSERT_TASKS);
            System.out.println(dropTasks+updateTasks+insertTasks);
            System.out.println("Table Tasks is created!");

        }
    }


    private static void workWithConnection(Connection connection) throws SQLException {
        while (true) {
            main.Action nextAction = getActionFromUser();
            switch (nextAction) {
                case ALL_PENDING_TASKS:
                    listAllPendingTasks(connection);
                    break;
                case ADD_TASK_TO_LIST:
                    addTask(connection);
                    break;
                case REMOVE_TASK_FROM_LIST:
                    removeTask(connection);
                    break;
                case MARK_AS_COMPLETED:
                    markCompleted(connection);
                    break;
                case ALL_COMPLETED_TASKS:
                    listAllCompletedTasks(connection);
                    break;
                case LIST_ALL_TASKS:
                    listAllTasks(connection);
                    break;
                case EXIT:
                    return;
            }
        }
    }
    private static main.Action getActionFromUser() {
        while (true) {
            System.out.println("Please select an action:");
            for (int i = 0; i < main.Action.values().length; i++) {
                System.out.println(i + "\t" + main.Action.values()[i]);
            }
            // scanner.nextInt(); - would not read new line characters
            int selected = Integer.parseInt(scanner.nextLine());
            if (main.Action.values().length <= selected || selected < 0) {
                System.out.println("You entered incorrect value!");
                continue;
            }
            return main.Action.values()[selected];
        }
    }

    // LIST_ALL_TASKS
    private static void listAllTasks(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(ALL_TASKS)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void printAllColumns(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t|\t");
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t|\t");
            }
            System.out.println();
        }
    }

    private static void listAllPendingTasks(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(PENDING_TASKS)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void addTask(Connection connection) throws SQLException {



        System.out.print("Please enter your task: ");
        String taskName = scanner.nextLine();

        System.out.print("Please enter deadline (yyyy-mm-dd): ");
        String date = scanner.nextLine();
        String status = "pending";


        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_TASK)) {
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, taskName);
            int updateTasks = preparedStatement.executeUpdate();
            System.out.println("Executed update: " + updateTasks);
        }
    }

    private static void removeTask(Connection connection) throws SQLException {
        System.out.println("Select which task to remove!");
        listAllTasks(connection);
        System.out.print("Enter a task ID: ");
        int taskID = Integer.parseInt(scanner.nextLine());
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_TASK)) {
            preparedStatement.setInt(1, taskID);
            int updateTasks = preparedStatement.executeUpdate();
            if (updateTasks == 1) {
                System.out.println("Task is successfully deleted!");
            } else if (updateTasks == 0) {
                System.out.println("Nothing was deleted. Probably ID is wrong!");
            } else {
            }
        }
    }

    //Mark_a_task_as_completed

    private static void markCompleted(Connection connection) throws SQLException {
        System.out.println("Select which task is completed !");
        listAllTasks(connection);
        System.out.print("Enter a task ID: ");
        int taskID = Integer.parseInt(scanner.nextLine());
        try (PreparedStatement preparedStatement = connection.prepareStatement(MARK_COMPLETED)) {
            preparedStatement.setInt(1, taskID);
            int updateTasks = preparedStatement.executeUpdate();
            if (updateTasks == 1) {
                System.out.println("Task is successfully completed!");
            } else if (updateTasks == 0) {
                System.out.println("Nothing was marked. Probably ID is wrong!");
            } else {
            }
        }
    }

    private static void listAllCompletedTasks(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(COMPLETED_TASKS)) {
                printAllColumns(resultSet);
            }
        }
    }
}



