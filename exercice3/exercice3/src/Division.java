class Division implements OperationStrategy {
    @Override
    public double calculate(double operand1, double operand2) {
        if (operand2 == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        return operand1 / operand2;
    }
}