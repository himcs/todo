package io.github.himcs.to.core;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TodoItem {
    @Setter
    private int id;
    private String content;
    @Setter
    private boolean done;

    public TodoItem(final String content) {
        this.content = content;
        done = false;
    }

}
