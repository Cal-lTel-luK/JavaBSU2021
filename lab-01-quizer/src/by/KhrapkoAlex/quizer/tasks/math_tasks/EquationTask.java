package by.KhrapkoAlex.quizer.tasks.math_tasks;

import by.KhrapkoAlex.quizer.Result;

import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    @Override
    Result validate(double answer) {
        return Math.abs((operator.doOperator(firstNumber, answer) - secondNumber) / secondNumber) <
                (operator == Operator.ADD || operator == Operator.SUBTRACT ? 1e-9 : getPrecision(precision) / 2) ?
                Result.OK : Result.WRONG;
    } // Please don't beat up, the author was trying to make it look good)

    @Override
    public String getText() {
        return String.format("%." + precision + "f", firstNumber) + " " +
                operator.getOperatorSymbol() + " x = " +
                String.format("%." + precision + "f", secondNumber);
    }

    public EquationTask(double firstNumber, double secondNumber, Operator operator, int precision) {
        super(firstNumber, secondNumber, operator, precision);
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber, EnumSet<Operator> operators, int precision) {
            super(minNumber, maxNumber, operators, precision);
        }

        private boolean isPossible(double firstNumber, double secondNumber, Operator operator) {
            switch (operator) {
                case ADD:
                case SUBTRACT:
                case MULTIPLY:
                    // check !!!
                    break;
                case DIV:
                    if (Math.abs(firstNumber) < getPrecision(precision)) {
                        return false;
                    }
                    break;
            }
            return true;
        }

        @Override
        public EquationTask generate() {
            double firstNumberGen;
            double secondNumberGen;
            Operator operatorGen;
            do {
                firstNumberGen = generateNumber(precision);
                secondNumberGen = generateNumber(precision);
                operatorGen = generateOperator();
            }
            while(!isPossible(firstNumberGen, secondNumberGen, operatorGen));
            return new EquationTask(firstNumberGen, secondNumberGen, operatorGen, precision);
        }
    }

}
