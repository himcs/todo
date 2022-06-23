package io.github.himcs.to.core.impl;

import io.github.himcs.to.core.TodoIndexParameter;
import io.github.himcs.to.core.TodoItem;
import io.github.himcs.to.core.TodoItemRepository;
import io.github.himcs.to.core.TodoParameter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TodoServiceImpl {
    private TodoItemRepository todoItemRepository;

    public TodoServiceImpl(final TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public TodoItem addTodoItem(final TodoParameter todoParameter) {
        if (Objects.isNull(todoParameter)) {
            throw new IllegalArgumentException("todoItemParameter is null");
        }
        TodoItem todoItem = new TodoItem(todoParameter.getContent());
        return todoItemRepository.save(todoItem);
    }

    public Optional<TodoItem> markTodoItemDone(final TodoIndexParameter todoIndexParameter) {
        Iterable<TodoItem> all = this.todoItemRepository.findAll();
        Optional<TodoItem> todoItem = StreamSupport.stream(all.spliterator(), false)
                .filter(
                        element -> element.getId() == todoIndexParameter.getIndex()
                ).findFirst();
        return todoItem.flatMap(e -> {
            e.setDone(true);
            return Optional.of(todoItemRepository.save(e));
        });
    }

    public List<TodoItem> listItem(final boolean all) {
        Iterable<TodoItem> itemIterable = todoItemRepository.findAll();
        return StreamSupport.stream(itemIterable.spliterator(), false)
                .filter(element ->
                        all ? true : element.isDone()
                )
                .collect(Collectors.toList());
    }
}
