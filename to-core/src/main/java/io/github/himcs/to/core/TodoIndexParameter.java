package io.github.himcs.to.core;

import lombok.Getter;

@Getter
public class TodoIndexParameter {
    private int index;

    public TodoIndexParameter(final int index) {
        this.index = index;
    }
}
