package by.KhrapkoAlex.quizer;

public interface Task {
    interface Generator {
        Task generate();
    }
    String getText();
    Result validate(String Answer);
}
