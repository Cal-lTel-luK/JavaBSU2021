package by.KhrapkoAlex.quizer.tasks;

import by.KhrapkoAlex.quizer.Result;
import by.KhrapkoAlex.quizer.Task;

public class TextTask implements Task {
    public TextTask(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Result validate(String answer) {
        return (this.answer.equals(answer) ? Result.OK : Result.WRONG);
    }

    private final String text;
    private final String answer;
}
