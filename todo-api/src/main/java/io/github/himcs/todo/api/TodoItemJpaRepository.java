package io.github.himcs.todo.api;

import io.github.himcs.todo.core.TodoItem;
import io.github.himcs.todo.core.TodoItemRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemJpaRepository extends TodoItemRepository,
        org.springframework.data.repository.Repository<TodoItem, Long> {
}
