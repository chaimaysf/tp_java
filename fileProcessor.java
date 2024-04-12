import java.io.*;

public class fileProcessor {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java FileProcessor <directory>");
            return;
        }

        File dir = new File(args[0]);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".op"));

        if (files == null) {
            System.out.println("No .op files found in the directory.");
            return;
        }

        for (File file : files) {
            processFile(file);
        }
    }

    private static void processFile(File file) {
        File resultFile = new File(file.getParent(), file.getName().replace(".op", ".res"));
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(processLine(line) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error processing file: " + file.getName() + ", " + e.getMessage());
        }
    }

    private static String processLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
            return "ERROR";
        }

        try {
            double number1 = Double.parseDouble(parts[0]);
            double number2 = Double.parseDouble(parts[1]);
            String operator = parts[2];
            double result;

            switch (operator) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
                default:
                    return "ERROR";
            }
            return String.valueOf(result);
        } catch (NumberFormatException e) {
            return "ERROR";
        }
    }

}
