package com.taskmanager.taskmanager.strategy;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.taskmanager.taskmanager.model.Task;

@Component
public class DueDateSortingStrategy implements TaskSortingStrategy {

    @Override
    public Comparator<Task> getComparator() {

        return Comparator.comparing(
                Task::getDueDate,
                Comparator.nullsLast(Comparator.naturalOrder())
        );
    }
}