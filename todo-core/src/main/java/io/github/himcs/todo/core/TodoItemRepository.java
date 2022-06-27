package io.github.himcs.todo.core;

public interface TodoItemRepository {
    TodoItem save(TodoItem todoItem);

    Iterable<TodoItem> findAll();
}
