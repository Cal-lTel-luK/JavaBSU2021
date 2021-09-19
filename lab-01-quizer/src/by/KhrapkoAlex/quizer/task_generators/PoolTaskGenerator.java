package by.KhrapkoAlex.quizer.task_generators;

import by.KhrapkoAlex.quizer.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PoolTaskGenerator implements Task.Generator {

    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this.tasks.addAll(Arrays.asList(tasks));
        this.allowDuplicate = allowDuplicate;
    }

    public PoolTaskGenerator(boolean allowDuplicate, ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.allowDuplicate = allowDuplicate;
    }

    @Override
    public Task generate() {
        int index = ThreadLocalRandom.current().nextInt(tasks.size());
        Task task = tasks.get(index);
        if (!allowDuplicate) {
            tasks.remove(index);
        }
        return task;
    }

    ArrayList<Task> tasks = new ArrayList<>();
    boolean allowDuplicate;
}
