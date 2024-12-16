package ar.edu.davinci.Utils;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            InputStream sqlFileStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream("sql/initialize_database.sql");
            if (sqlFileStream == null) {
                throw new FileNotFoundException("Archivo SQL no encontrado");
            }

            StringBuilder sqlScript = new StringBuilder();
            try (Scanner scanner = new Scanner(sqlFileStream)) {
                while (scanner.hasNextLine()) {
                    sqlScript.append(scanner.nextLine()).append("\n");
                }
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute(sqlScript.toString());
            }

            System.out.println("Base de datos inicializada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}