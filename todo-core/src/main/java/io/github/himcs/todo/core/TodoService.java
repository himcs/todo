package io.github.himcs.todo.core;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    TodoItem addTodoItem(TodoParameter todoParameter);

    Optional<TodoItem> markTodoItemDone(TodoIndexParameter todoIndexParameter);

    List<TodoItem> listItem(boolean all);
}
