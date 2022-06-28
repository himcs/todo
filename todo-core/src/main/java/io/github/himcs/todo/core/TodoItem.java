package io.github.himcs.todo.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoItem {
    @Setter
    private long index;
    private String content;
    @Setter
    private boolean done;

    public TodoItem(final String content) {
        this.content = content;
        done = false;
    }

}
