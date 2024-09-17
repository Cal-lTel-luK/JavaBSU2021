package by.KhrapkoAlex.quizer.task_generators;

import by.KhrapkoAlex.quizer.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupTaskGenerator implements Task.Generator {
    public GroupTaskGenerator(Task.Generator... generators) {
        this.taskGenerators.addAll(Arrays.asList(generators));
    }

    public GroupTaskGenerator(ArrayList<Task.Generator> generators) {
        this.taskGenerators = generators;
    }

    @Override
    public Task generate() {
        return taskGenerators.get((int)(Math.random() * taskGenerators.size())).generate();
    }

    ArrayList<Task.Generator> taskGenerators = new ArrayList<>();
}
