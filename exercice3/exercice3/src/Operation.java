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
        OperationStrategy strategy = OperationFactory.createOperation(operator);
        return strategy.calculate(operand1, operand2);
    }
}