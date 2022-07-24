package org.example;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
/**
 * Hello world!
 *
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@SpringBootApplication
public class App {

    private static final String createTableSQL = "create table users (\r\n" + "  id  int(3) primary key,\r\n" +
            "  name varchar(20),\r\n" + "  email varchar(20),\r\n" + "  country varchar(20),\r\n" +
            "  password varchar(20)\r\n" + "  );";


    public static void main(String[] args)
    {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(App.class)
                .headless(false).run(args);

        SwingApp sw = new SwingApp();
        sw.setVisible(true);


        //App createTableExample = new App();
        //createTableExample.createTable();

    }

    public void createTable() throws SQLException {

        System.out.println(createTableSQL);
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             Statement statement = connection.createStatement();) {

            // Step 3: Execute the query or update query
            statement.execute(createTableSQL);

        } catch (SQLException e) {
            // print SQL exception information
            H2JDBCUtils.printSQLException(e);
        }
    }
}
