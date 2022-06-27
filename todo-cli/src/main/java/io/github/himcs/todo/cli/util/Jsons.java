package io.github.himcs.todo.cli.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.himcs.todo.core.TodoItem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Jsons {

    private Jsons() {

    }

    private static final CollectionType TYPE = TypeFactory.defaultInstance()
            .constructCollectionType(List.class, TodoItem.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Iterable<TodoItem> toObjects(final File file) {
        try {
            Iterable<TodoItem> todoItemIterable = MAPPER.readValue(file, TYPE);
            return todoItemIterable;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeObjects(final File file, final Iterable<TodoItem> todoItems) {
        try {
            MAPPER.writeValue(file, todoItems);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
