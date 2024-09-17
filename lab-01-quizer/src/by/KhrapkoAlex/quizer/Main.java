package by.KhrapkoAlex.quizer;

import by.KhrapkoAlex.quizer.task_generators.GroupTaskGenerator;
import by.KhrapkoAlex.quizer.task_generators.PoolTaskGenerator;
import by.KhrapkoAlex.quizer.tasks.TextTask;
import by.KhrapkoAlex.quizer.exceptions.QuizNotFinishedException;
import by.KhrapkoAlex.quizer.tasks.math_tasks.EquationTask;
import by.KhrapkoAlex.quizer.tasks.math_tasks.ExpressionTask;
import by.KhrapkoAlex.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();

        quizMap.put("group", new Quiz(new GroupTaskGenerator(
                new ExpressionTask.Generator(-10, 10, EnumSet.allOf(MathTask.Operator.class), 2),
                new EquationTask.Generator(-10, 10, EnumSet.allOf(MathTask.Operator.class),1),
                new EquationTask.Generator(-10, 10, EnumSet.allOf(MathTask.Operator.class), 0),
                new ExpressionTask.Generator(-10, 10, EnumSet.allOf(MathTask.Operator.class), 0)),
                5));
        quizMap.put("pool", new Quiz(new PoolTaskGenerator(true,
            new TextTask("Did I have time to come up with tests?", "No"),
            new TextTask("Did I want to make up tests?", "Yes"),
            new TextTask("Question1?", "Answer1"),
            new TextTask("Question2?", "Answer2")),3));

        quizMap.put("int", new Quiz(new GroupTaskGenerator(
                new ExpressionTask.Generator(-100, 100, EnumSet.allOf(MathTask.Operator.class), 0),
                new EquationTask.Generator(-100, 100, EnumSet.allOf(MathTask.Operator.class), 0)), 10));

        quizMap.put("double", new Quiz(new GroupTaskGenerator(
                new ExpressionTask.Generator(-20, 20, EnumSet.allOf(MathTask.Operator.class), 1),
                new EquationTask.Generator(-20, 20, EnumSet.allOf(MathTask.Operator.class), 2)), 5));

        quizMap.put("random", new Quiz(new GroupTaskGenerator(
                new ExpressionTask.Generator(-20, 20, EnumSet.allOf(MathTask.Operator.class), 0),
                new EquationTask.Generator(-20, 20, EnumSet.allOf(MathTask.Operator.class), 1)), 7));

        return quizMap;
    }

    public static void main(String[] args) throws QuizNotFinishedException {
        System.out.println("Enter quiz name. There are : ");
        Map<String, Quiz> quizMap = getQuizMap();
        for (Map.Entry<String, Quiz> entry : quizMap.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
        }

        Scanner scanner = new Scanner(System.in);
        Quiz quizSelectedByTheUser;
        while (true) {
            String userInput = scanner.nextLine();
            if (!quizMap.containsKey(userInput)) {
                System.out.println("There is no such test. Enter the correct test name. Case does matter)");
                continue;
            }
            quizSelectedByTheUser = quizMap.get(userInput);
            break;
        }
        while (!quizSelectedByTheUser.isFinished()) {
            System.out.println(quizSelectedByTheUser.nextTask().getText());
            String userInput = scanner.nextLine();
            Result result = quizSelectedByTheUser.provideAnswer(userInput);
            System.out.println("Verdict: " + result.toString());
        }
        System.out.println("Your mark: " + quizSelectedByTheUser.getMark());
    }
}
