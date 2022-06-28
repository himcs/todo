package io.github.himcs.todo.core.impl;

import io.github.himcs.todo.core.TodoIndexParameter;
import io.github.himcs.todo.core.TodoItem;
import io.github.himcs.todo.core.TodoItemRepository;
import io.github.himcs.todo.core.TodoParameter;
import io.github.himcs.todo.core.TodoService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TodoServiceImpl implements TodoService {
    private TodoItemRepository todoItemRepository;

    public TodoServiceImpl(final TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @Override
    public TodoItem addTodoItem(final TodoParameter todoParameter) {
        if (Objects.isNull(todoParameter)) {
            throw new IllegalArgumentException("todoItemParameter is null");
        }
        TodoItem todoItem = new TodoItem(todoParameter.getContent());
        return todoItemRepository.save(todoItem);
    }

    @Override
    public Optional<TodoItem> markTodoItemDone(final TodoIndexParameter todoIndexParameter) {
        Iterable<TodoItem> all = this.todoItemRepository.findAll();
        Optional<TodoItem> todoItem = StreamSupport.stream(all.spliterator(), false)
                .filter(
                        element -> element.getIndex() == todoIndexParameter.getIndex()
                ).findFirst();
        return todoItem.flatMap(e -> {
            e.setDone(true);
            return Optional.of(todoItemRepository.save(e));
        });
    }

    @Override
    public List<TodoItem> listItem(final boolean all) {
        Iterable<TodoItem> itemIterable = todoItemRepository.findAll();
        return StreamSupport.stream(itemIterable.spliterator(), false)
                .filter(element ->
                        all ? true : !element.isDone()
                )
                .collect(Collectors.toList());
    }
}
