package io.github.himcs.todo.api;

import io.github.himcs.todo.core.TodoItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:test.properties")
class TodoItemJpaRepositoryTest {
    @Autowired
    private TodoItemJpaRepository repository;

    @Test
    public void should_find_nothing_for_empty_repository() {
        final Iterable<TodoItem> items = repository.findAll();
        assertThat(items).hasSize(0);
    }

    @Test
    public void should_save_new_todo_items() {
        TodoItem todoItem = new TodoItem("foo");
        repository.save(todoItem);
        final Iterable<TodoItem> items = repository.findAll();
        assertThat(items).hasSize(1);
        List<TodoItem> collect = StreamSupport.stream(items.spliterator(), false).collect(Collectors.toList());
        assertThat(collect.get(0).getContent()).isEqualTo("foo");
        System.out.println(collect.get(0).getIndex());
    }
}