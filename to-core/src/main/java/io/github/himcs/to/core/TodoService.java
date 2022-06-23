package io.github.himcs.to.core;

import java.util.Optional;

public interface TodoService {
    TodoItem addTodoItem(TodoParameter todoParameter);

    Optional<TodoItem> markTodoItemDone(TodoIndexParameter todoIndexParameter);
}
