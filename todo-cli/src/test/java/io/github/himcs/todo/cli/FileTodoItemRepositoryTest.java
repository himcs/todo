package io.github.himcs.todo.cli;

import com.google.common.collect.Iterables;
import io.github.himcs.todo.cli.file.FileTodoItemRepository;
import io.github.himcs.todo.core.TodoItem;
import io.github.himcs.todo.core.TodoItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FileTodoItemRepositoryTest {

    @TempDir
    File tempDir;
    private File tempFile;

    FileTodoItemRepository repository;

    @BeforeEach
    public void before() throws IOException {
        this.tempFile = File.createTempFile("file", "", tempDir);
        this.repository = new FileTodoItemRepository(this.tempFile);
    }

    @Test
    void should_find_nothing_for_empty_repository() {
        final Iterable<TodoItem> items = repository.findAll();
        assertThat(items).hasSize(0);
    }

    @Test
    void should_new_todo_item_save() {
        TodoItem todoItem = new TodoItem("foo");
        repository.save(todoItem);
        Iterable<TodoItem> all = repository.findAll();
        List<TodoItem> collect = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
        TodoItem todoItem1 = collect.get(0);
        assertThat(todoItem1.getId()).isEqualTo(1L);
        assertThat(todoItem1.getContent()).isEqualTo("foo");
    }

    @Test
    void should_new_todo_item_update() {
        TodoItem todoItem = new TodoItem("foo");
        TodoItem todoItem2 = new TodoItem("bar");
        TodoItem todoItem1 = repository.save(todoItem);
        TodoItem todoItem22 = repository.save(todoItem2);
        assertThat(todoItem1.getId()).isEqualTo(1L);
        assertThat(todoItem1.getContent()).isEqualTo("foo");
        assertThat(todoItem1.isDone()).isEqualTo(false);
        assertThat(todoItem22.getId()).isEqualTo(2L);
        assertThat(todoItem22.getContent()).isEqualTo("bar");
        assertThat(todoItem22.isDone()).isEqualTo(false);

        todoItem1.setDone(true);
        TodoItem save = repository.save(todoItem1);
        assertThat(save.getId()).isEqualTo(1L);
        assertThat(save.getContent()).isEqualTo("foo");
        assertThat(save.isDone()).isEqualTo(true);

    }

    @Test
    public void should_update_saved_items() {
        repository.save(new TodoItem("foo"));
        repository.save(new TodoItem("bar"));
        final Iterable<TodoItem> items = repository.findAll();
        final TodoItem toUpdate = Iterables.get(items, 0);
        toUpdate.setDone(true);

        repository.save(toUpdate);

        final Iterable<TodoItem> updated = repository.findAll();
        assertThat(updated).hasSize(2);
        assertThat(Iterables.get(items, 0).isDone()).isTrue();
    }

    @Test
    void should_not_save_null_todo_item() {
        assertThatExceptionOfType(TodoItemException.class).isThrownBy(() -> {
            repository.save(null);
        });
    }


}