package io.github.himcs.todo.core.impl;

import com.google.common.collect.ImmutableList;
import io.github.himcs.todo.core.TodoIndexParameter;
import io.github.himcs.todo.core.TodoItem;
import io.github.himcs.todo.core.TodoItemRepository;
import io.github.himcs.todo.core.TodoParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TodoServiceImplTest {
    TodoServiceImpl todoService;
    TodoItemRepository todoItemRepository;

    @BeforeEach
    public void before() {
        todoItemRepository = mock(TodoItemRepository.class);
        todoService = new TodoServiceImpl(todoItemRepository);
    }

    @Test
    public void should_add_todo_item() {
        when(todoItemRepository.save(any())).then(returnsFirstArg());
        TodoItem item = todoService.addTodoItem(new TodoParameter("foo"));
        assertThat(item.getContent()).isEqualTo("foo");
    }

    @Test
    public void should_fail_exception_add_todo_item() {
        when(todoItemRepository.save(any())).then(returnsFirstArg());
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> todoService.addTodoItem(null));
    }

    @Test
    public void should_mark_todo_item_done() {
        TodoItem todoItem1 = new TodoItem("123");
        todoItem1.setId(1);
        when(todoItemRepository.findAll()).thenReturn(ImmutableList.of(todoItem1));
        when(todoItemRepository.save(any())).then(returnsFirstArg());
        Optional<TodoItem> todoItem = todoService.markTodoItemDone(new TodoIndexParameter(1));
        assertThat(todoItem.get().isDone()).isEqualTo(true);
    }

    @Test
    public void should_not_mark_todo_item_for_out_of_scope_index() {
        when(todoItemRepository.findAll()).thenReturn(ImmutableList.of(new TodoItem("123")));
        final Optional<TodoItem> todoItem = todoService.markTodoItemDone(new TodoIndexParameter(1));
        assertThat(todoItem).isEmpty();
    }

    @Test
    public void should_list_done_item() {
        when(todoItemRepository.findAll()).thenReturn(ImmutableList.of(new TodoItem("123")));
        List<TodoItem> todoItems = todoService.listItem(true);
        TodoItem todoItem = todoItems.get(0);
        assertThat(todoItem.getContent()).isEqualTo("123");
        assertThat(todoItem.isDone()).isEqualTo(false);
    }

    @Test
    public void should_list_all_item() {
        TodoItem todoItem1 = new TodoItem("foo");
        todoItem1.setId(1);
        todoItem1.setDone(false);
        TodoItem todoItem2 = new TodoItem("bar");
        todoItem2.setId(2);
        todoItem2.setDone(true);

        when(todoItemRepository.findAll()).thenReturn(ImmutableList.of(todoItem1, todoItem2));
        List<TodoItem> todoItems = todoService.listItem(false);
        TodoItem todoItem = todoItems.get(0);
        assertThat(todoItem.getId()).isEqualTo(1);
        assertThat(todoItem.getContent()).isEqualTo("foo");
        assertThat(todoItem.isDone()).isEqualTo(false);
    }


}