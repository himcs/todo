package io.github.himcs.to.core;

public interface TodoItemRepository {
    TodoItem save(TodoItem todoItem);

    Iterable<TodoItem> findAll();
}
