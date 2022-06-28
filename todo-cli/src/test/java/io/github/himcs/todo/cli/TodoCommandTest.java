package io.github.himcs.todo.cli;

import io.github.himcs.todo.core.TodoIndexParameter;
import io.github.himcs.todo.core.TodoItem;
import io.github.himcs.todo.core.TodoParameter;
import io.github.himcs.todo.core.TodoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TodoCommandTest {

    @TempDir
    File tempDir;
    private TodoService service;
    private CommandLine cli;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        final ObjectFactory factory = new ObjectFactory();
        final File repositoryFile = new File(tempDir, "repository.json");
        this.service = factory.createService(repositoryFile);
        this.cli = factory.createCommandLine(repositoryFile);
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @AfterEach
    public void clean(){
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void should_add_todo_item() {
        int result = cli.execute("add", "test");
        assertThat(result).isEqualTo(0);
        List<TodoItem> todoItems = service.listItem(true);
        assertThat(todoItems.get(0).getContent()).isEqualTo("test");
        assertThat(out.toString()).contains("Item 1 added");
    }

    @Test
    public void should_fail_to_add_empty_todo_item() {
        int result = cli.execute("add", "");
        assertThat(result).isNotEqualTo(0);
    }

    @Test
    public void should_mark_todo_item_done() {
        TodoItem todoItem = service.addTodoItem(new TodoParameter("miao"));
        int result = cli.execute("done", String.valueOf(todoItem.getIndex()));
        assertThat(result).isEqualTo(0);
        List<TodoItem> todoItems = service.listItem(true);
        assertThat(todoItems.get(0).getContent()).isEqualTo("miao");
        assertThat(todoItems.get(0).isDone()).isEqualTo(true);
    }

    @Test
    public void should_fail_to_mark_with_illegal_index() {
        assertThat(cli.execute("done", "-1")).isNotEqualTo(0);
        assertThat(cli.execute("done", "0")).isNotEqualTo(0);
    }

    @Test
    public void should_fail_to_mark_unknown_todo_item() {
        assertThat(cli.execute("done", "1")).isNotEqualTo(0);
    }

    @Test
    public void should_list_all_not_done_todo_item() {
        service.addTodoItem(new TodoParameter("miao"));
        service.addTodoItem(new TodoParameter("miao2"));
        service.addTodoItem(new TodoParameter("miao3"));
        List<TodoItem> todoItems = service.listItem(true);
        assertThat(todoItems.size()).isEqualTo(3);
        assertThat(cli.execute("list")).isEqualTo(0);
    }

    @Test
    public void should_list_all_todo_item() {
        service.addTodoItem(new TodoParameter("miao"));
        TodoItem todoItem = service.addTodoItem(new TodoParameter("miao2"));
        service.addTodoItem(new TodoParameter("miao3"));
        service.markTodoItemDone(new TodoIndexParameter(todoItem.getIndex()));

        assertThat(cli.execute("list","-a")).isEqualTo(0);
    }
}