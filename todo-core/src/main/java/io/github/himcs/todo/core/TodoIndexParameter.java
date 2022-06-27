package io.github.himcs.todo.core;

import lombok.Getter;

@Getter
public class TodoIndexParameter {
    private long index;

    public TodoIndexParameter(final long index) {
        this.index = index;
    }
}
