public class Calculateur {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Calculateur <number1> <number2> <operator>");
            return;
        }

        try {
            double number1 = Double.parseDouble(args[0]);
            double number2 = Double.parseDouble(args[1]);
            String operator = args[2];
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
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }

            System.out.println("Result: " + result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
