package io.github.himcs.todo.cli;

import com.google.common.base.Strings;
import io.github.himcs.todo.core.TodoIndexParameter;
import io.github.himcs.todo.core.TodoItem;
import io.github.himcs.todo.core.TodoParameter;
import io.github.himcs.todo.core.TodoService;
import picocli.CommandLine;

import java.util.List;
import java.util.Optional;

@CommandLine.Command(name = "todo", version = "0.0.1")
public class TodoCommand {
    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    private TodoService todoService;

    public TodoCommand(final TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * todo add <item>
     * 1. <item>
     * Item <itemIndex> added
     *
     * @param context
     * @return
     */
    @CommandLine.Command(name = "add")
    public int add(@CommandLine.Parameters(index = "0", description = "todo context") final String context) {
        if (Strings.isNullOrEmpty(context)) {
            throw new CommandLine.ParameterException(spec.commandLine(), "empty item is not allowed");
        }
        final TodoItem todoItem = todoService.addTodoItem(new TodoParameter(context));
        System.out.printf("%s. %s\n", todoItem.getIndex(), todoItem.getContent());
        System.out.printf("Item %s added\n", todoItem.getIndex());
        return 0;
    }

    /**
     * todo done <itemIndex>
     * Item <itemIndex> done.
     *
     * @param index
     * @return
     */
    @CommandLine.Command(name = "done")
    public int done(@CommandLine.Parameters(index = "0", description = "todo context") final long index) {
        if (index <= 0) {
            throw new CommandLine.ParameterException(spec.commandLine(), "index must >= 0");
        }
        Optional<TodoItem> todoItem = todoService.markTodoItemDone(new TodoIndexParameter(index));
        if (todoItem.isPresent()) {
            System.out.printf("will done todo %s\n", index);
        } else {
            throw new CommandLine.ParameterException(spec.commandLine(), "unknown todo item index");
        }
        return 0;
    }

    /**
     * todo list
     * 1. <item1>
     * 2. <item2>
     * Total: 2 items
     *
     * <p>
     * todo list --all
     * 1. <item1>
     * 2. <item2>
     * 3. [Done] <item3>
     * Total: 3 items, 1 item done
     *
     * @param all
     * @return
     */
    @CommandLine.Command(name = "list")
    public int list(@CommandLine.Option(names = {"-a", "-all"}, description = "list all") final boolean all) {
        List<TodoItem> todoItems = todoService.listItem(all);
        for (TodoItem todoItem : todoItems) {
            System.out.printf("%s. %s\n", todoItem.getIndex(), todoItem.getContent());
        }
        System.out.printf("Total: %d items\n", todoItems.size());
        return 0;
    }
}
