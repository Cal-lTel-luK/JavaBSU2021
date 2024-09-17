package by.KhrapkoAlex.quizer;

import by.KhrapkoAlex.quizer.exceptions.QuizNotFinishedException;

public class Quiz {

    Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        if (taskCount <= 0) {
            throw new IllegalArgumentException();
        }
        this.taskCount = taskCount;
    }

    public Task nextTask() {
        if (isFinished()) {
            throw new RuntimeException("Quiz finished!");
        }
        this.currentTask = generator.generate();
        taskCount--;
        return currentTask;
    }

    public Result provideAnswer(String answer) {
        if (currentTask == null) {
            throw new RuntimeException("No generate task!");
        }

        Result result = currentTask.validate(answer);
        switch (result) {
            case OK: {
                ++correctAnswerNumber;
                break;
            }

            case WRONG: {
                ++wrongAnswerNumber;
                break;
            }

            case INCORRECT_INPUT: {
                ++incorrectInputNumber;
                break;
            }
        }
        return result;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    public int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    boolean isFinished() {
        return taskCount == 0;
    }

    double getMark() throws QuizNotFinishedException {
        if (!isFinished()) {
            throw new QuizNotFinishedException("Please. Finish test!");
        }
        return 100.d * (double) correctAnswerNumber / (wrongAnswerNumber + correctAnswerNumber + incorrectInputNumber);
    }

    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private int taskCount;
    private Task currentTask = null;
    private final Task.Generator generator;
}