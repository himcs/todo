package io.github.himcs.todo.cli;

import io.github.himcs.todo.cli.file.FileTodoItemRepository;
import io.github.himcs.todo.core.TodoItemRepository;
import io.github.himcs.todo.core.TodoService;
import io.github.himcs.todo.core.impl.TodoServiceImpl;
import picocli.CommandLine;

import java.io.File;

public class ObjectFactory {
    public CommandLine createCommandLine(final File repositoryFile) {
        return new CommandLine(createTodoCommand(repositoryFile));
    }

    private TodoCommand createTodoCommand(final File repositoryFile) {
        return new TodoCommand(createService(repositoryFile));
    }

    public TodoService createService(final File repositoryFile) {
        TodoItemRepository todoItemRepository = new FileTodoItemRepository(repositoryFile);
        TodoService todoService = new TodoServiceImpl(todoItemRepository);
        return todoService;
    }

}
