import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Classe pour représenter une opération
class Operation {
    private double operand1;
    private double operand2;
    private char operator;

    public Operation(double operand1, double operand2, char operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public double calculate() {
        double result = 0.0;
        try {
            String[] args = {String.valueOf(operand1), String.valueOf(operand2), String.valueOf(operator)};
            result = Calculateur.calculate(args);
        } catch (NumberFormatException e) {
            System.out.println("Les deux premiers paramètres doivent être numériques.");
        } catch (IllegalArgumentException e) {
            System.out.println("Opérateur invalide.");
        }
        return result;
    }
}

// Classe principale pour traiter les fichiers
public class FileProcessor {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FileProcessor <directory>");
            return;
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".op"));

        if (files == null) {
            System.out.println("Invalid directory path or directory does not exist.");
            return;
        }

        for (File file : files) {
            processFile(file);
        }

        System.out.println("Processing complete.");
    }

    public static void processFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> results = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 3) {
                        throw new SyntaxErrorException("Invalid syntax: " + line);
                    }

                    double operand1 = Double.parseDouble(parts[0]);
                    double operand2 = Double.parseDouble(parts[1]);
                    char operator = parts[2].charAt(0);

                    Operation operation = new Operation(operand1, operand2, operator);
                    double result = operation.calculate();
                    results.add(String.valueOf(result));
                } catch (NumberFormatException e) {
                    results.add("ERROR");
                } catch (SyntaxErrorException e) {
                    results.add("ERROR: " + e);
                }
            }

            writeResultsToFile(file, results);
        } catch (IOException e) {
            System.out.println("Error reading file: " + file.getName());
        }
    }

    public static void writeResultsToFile(File inputFile, List<String> results) {
        String outputFileName = inputFile.getName().replace(".op", ".res");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(inputFile.getParent(), outputFileName)))) {
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing results to file: " + outputFileName);
        }
    }

    static class SyntaxErrorException extends Exception {
        public SyntaxErrorException(String message) {
            super(message);
        }
    }
}
