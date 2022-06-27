package io.github.himcs.todo.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class TodoItemExceptionTest {
    @Test
    public void should_create_todo_exception() {
        TodoItemException todoItemException = new TodoItemException();
        assertThat(todoItemException.getMessage()).isEqualTo(null);
    }

    @Test
    public void should_create_todo_exception_with_message() {
        TodoItemException todoItemException = new TodoItemException("foo");
        assertThat(todoItemException.getMessage()).isEqualTo("foo");
    }

    @Test
    public void should_create_todo_exception_with_message_and_exception() {
        TodoItemException todoItemException = new TodoItemException("foo", new IllegalArgumentException());
        assertThat(todoItemException.getMessage()).isEqualTo("foo");
    }
}