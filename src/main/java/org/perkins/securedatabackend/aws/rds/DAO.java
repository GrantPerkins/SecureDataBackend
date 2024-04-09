package org.perkins.securedatabackend.aws.rds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    // jdbc:mysql://<url>/ebdb
    // ebroot:ebrooteb

    String endpoint = System.getenv("RDS_ENDPOINT");
    String username = System.getenv("RDS_USERNAME");
    String password = System.getenv("RDS_PASSWORD");
    String dbName = System.getenv("RDS_DBNAME");
    int port = Integer.parseInt(System.getenv("RDS_PORT"));
    String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s", endpoint, port, dbName);


    public DAO() {
        connect();
    }

    public void connect() {
        System.out.println(jdbcUrl +" " +username+" "+password);
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
            // Use the connection here
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }
}
