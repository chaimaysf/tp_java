package fr.hetic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class DatabaseToFile {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://SG-hetic-mt4-java-5275-pgsql-master.servers.mongodirector.com:5432/TP";
        String user = "etudiant";
        String password = "MT4@hetic2324";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT F.NOM, L.PARAM1, L.PARAM2, L.OPERATEUR, L.INDEX " +
                    "FROM FICHIER F " +
                    "INNER JOIN LIGNE L ON F.ID = L.FICHIER_ID " +
                    "WHERE F.TYPE = 'OP'";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String nomFichier = resultSet.getString("NOM");
                    int param1 = resultSet.getInt("PARAM1");
                    int param2 = resultSet.getInt("PARAM2");
                    String operateur = resultSet.getString("OPERATEUR");
                    int index = resultSet.getInt("INDEX");

                    String nomFichierFinal = generateFileName(nomFichier, param1, param2);

                    writeToFile(nomFichierFinal, param1, param2, operateur, index);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateFileName(String nomFichier, int param1, int param2) {
        return nomFichier + "_" + param1 + "_" + param2 + ".txt";
    }

    private static void writeToFile(String fileName, int param1, int param2, String operateur, int index) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Param1: " + param1 + "\n");
            writer.write("Param2: " + param2 + "\n");
            writer.write("Operateur: " + operateur + "\n");
            writer.write("Index: " + index + "\n");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier " + fileName + ": " + e.getMessage());
        }
    }
}
