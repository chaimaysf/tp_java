class OperationFactory {
    public static OperationStrategy createOperation(char operator) {
        switch (operator) {
            case '+':
                return new Addition();
            case '-':
                return new Soustraction();
            case '*':
                return new Multiplication();
            case '/':
                return new Division();
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
