package io.github.himcs.todo.core;

public class TodoItemException extends RuntimeException {

    public TodoItemException() {
        super();
    }

    public TodoItemException(final String message) {
        super(message);
    }

    public TodoItemException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
