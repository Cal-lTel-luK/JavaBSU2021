package by.KhrapkoAlex.quizer.tasks.math_tasks;

import by.KhrapkoAlex.quizer.Task;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
        @Override
        MathTask generate();

        double getMinNumber();
        double getMaxNumber();

        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }

    enum Operator {
        ADD {
            public String getOperatorSymbol() {
                return "+";
            }

            public double doOperator(double x, double y) {
                return x + y;
            }
        },
        SUBTRACT {
            public String getOperatorSymbol() {
                return "-";
            }

            public double doOperator(double x, double y) {
                return x - y;
            }
        },
        MULTIPLY {
            public String getOperatorSymbol() {
                return "*";
            }

            public double doOperator(double x, double y) {
                return x * y;
            }
        },
        DIV {
            public String getOperatorSymbol() {
                return "/";
            }

            public double doOperator(double x, double y) {
                return x / y;
            }
        };

        public abstract double doOperator(double x, double y);
        public abstract String getOperatorSymbol();
    }
}
