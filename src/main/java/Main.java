import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "808080";
    private static final String DB_URL = "jdbc:postgresql://localhost:15432/meme_china";

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        while (true) {
            System.out.println("1. Показать список");
            System.out.println("2. Создать задачу");
            System.out.println("3. Выполнить задачу");
            System.out.println("4. Выход");

            switchCommand(scanner.nextInt(), connection, scanner);
        }
    }

    private static void switchCommand(int command, Connection connection, Scanner scanner) throws SQLException {
        switch (command) {
            case 1:
                // Стейтмент - объект, который умеет отправлять запросы в бд
                Statement statement = connection.createStatement();
                final String sqlSelectAllTasks = "SELECT * FROM task ORDER BY id";
                // ResultSet - объект, который хранит результат выполнения запроса
                ResultSet resultSet = statement.executeQuery(sqlSelectAllTasks);

                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + "-" +
                            resultSet.getString("name") + " " +
                            resultSet.getString("state") + " " +
                            resultSet.getString("priority") + " " +
                            resultSet.getString("place"));
                }
                System.out.print("\n");
                break;

            case 2:
                String sql2 = "INSERT INTO task (name, state) VALUES (?, 'ASSIGNED')";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

                System.out.println("Введите название задачи: ");
                scanner.nextLine();
                String taskName = scanner.nextLine();

                preparedStatement2.setString(1, taskName);
                preparedStatement2.executeUpdate();
                break;

            case 3:
                String sql3 = "UPDATE task SET state = 'DONE' WHERE id = ?";
                PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);

                System.out.println("Введите идентификатор задачи: ");
                int taskId = scanner.nextInt();

                preparedStatement3.setInt(1, taskId);
                preparedStatement3.executeUpdate();
                break;

            case 4:
                System.exit(0);
        }
    }
}