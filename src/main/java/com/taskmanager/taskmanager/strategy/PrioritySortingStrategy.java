package com.taskmanager.taskmanager.strategy;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.taskmanager.taskmanager.enums.Priority;
import com.taskmanager.taskmanager.model.Task;

@Component
public class PrioritySortingStrategy implements TaskSortingStrategy {

    @Override
    public Comparator<Task> getComparator() {

        return Comparator.comparingInt(task -> {
            if (task.getPriority() == Priority.HIGH) return 1;
            if (task.getPriority() == Priority.MEDIUM) return 2;
            return 3;
        });
    }
}