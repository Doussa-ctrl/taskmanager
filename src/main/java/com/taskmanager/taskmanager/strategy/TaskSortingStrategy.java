package com.taskmanager.taskmanager.strategy;

import java.util.Comparator;
import com.taskmanager.taskmanager.model.Task;

public interface TaskSortingStrategy {

    Comparator<Task> getComparator();

}