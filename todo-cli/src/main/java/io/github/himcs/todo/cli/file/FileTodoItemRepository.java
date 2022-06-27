package io.github.himcs.todo.cli.file;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import io.github.himcs.todo.cli.util.Jsons;
import io.github.himcs.todo.core.TodoItem;
import io.github.himcs.todo.core.TodoItemException;
import io.github.himcs.todo.core.TodoItemRepository;

import java.io.File;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FileTodoItemRepository implements TodoItemRepository {
    private File file;

    public FileTodoItemRepository(final File file) {
        this.file = file;
    }

    @Override
    public TodoItem save(final TodoItem todoItem) {
        if (Objects.isNull(todoItem)) {
            throw new TodoItemException();
        }
        Iterable<TodoItem> saveContent = getSaveContent(todoItem);
        Jsons.writeObjects(file, saveContent);
        return todoItem;
    }

    private Iterable<TodoItem> getSaveContent(final TodoItem todoItem) {
        Iterable<TodoItem> todoItemIterable = findAll();
        if (todoItem.getId() == 0) {
            long newIndex = Iterables.size(todoItemIterable) + 1;
            todoItem.setId(newIndex);
            return ImmutableList.<TodoItem>builder().addAll(todoItemIterable).add(todoItem).build();
        }
        return StreamSupport.stream(todoItemIterable.spliterator(), false).map(
                element -> element.getId() == todoItem.getId() ? todoItem : element
        ).collect(Collectors.toList());
    }


    @Override
    public Iterable<TodoItem> findAll() {
        return loadFormFile();
    }

    private Iterable<TodoItem> loadFormFile() {
        if (this.file.length() == 0) {
            return ImmutableList.of();
        }
        return Jsons.toObjects(file);
    }
}
