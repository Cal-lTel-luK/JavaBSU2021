package by.KhrapkoAlex.quizer.tasks.math_tasks;

import by.KhrapkoAlex.quizer.Result;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMathTask implements MathTask {
    public AbstractMathTask(double firstNumber, double secondNumber, Operator operator, int precision) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operator = operator;
        this.precision = precision;
    }

    protected static double getPrecision(int precision) {
        return Math.pow(10, -precision);
    }

    public Result validate(String answer) {
        try {
            return validate(Double.parseDouble(answer));
        } catch (NumberFormatException NFE) {
            return Result.INCORRECT_INPUT;
        }
    }

    abstract Result validate(double answer);

    protected final Operator operator;
    protected double firstNumber;
    protected double secondNumber;
    protected int precision;

    public abstract static class Generator implements MathTask.Generator {
        EnumSet<Operator> operators;
        protected double minNumber;
        protected double maxNumber;
        protected int precision;

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        protected final Operator generateOperator() {
            int position = ThreadLocalRandom.current().nextInt(operators.size());
            return (Operator) operators.toArray()[position];
        }

        protected double roundNumberWithPrecision(double number) {
            return Double.parseDouble(String.format("%." + precision + "f", number));
        }

        protected double generateNumber(int precision) {
            return roundNumberWithPrecision(ThreadLocalRandom.current().nextDouble(minNumber, maxNumber));
        }

        public Generator(double minNumber, double maxNumber, EnumSet<Operator> operators, int precision) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operators = operators;
            this.precision = precision;

            if (this.minNumber > this.maxNumber) {
                throw new IllegalArgumentException("Min more than Max");
            }
            if (this.precision < 0 || this.precision > 8) {
                throw new IllegalArgumentException("So big precision");
            }
            if (this.operators.isEmpty()) {
                throw new IllegalArgumentException("There are no operators in set"); // or Runtime Exception
            }
        }

    }
}

