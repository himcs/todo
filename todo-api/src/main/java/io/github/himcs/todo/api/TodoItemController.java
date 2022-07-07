package io.github.himcs.todo.api;


import io.github.himcs.todo.core.TodoIndexParameter;
import io.github.himcs.todo.core.TodoItem;
import io.github.himcs.todo.core.TodoParameter;
import io.github.himcs.todo.core.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo-items")
public class TodoItemController {

    private final TodoService service;

    @Autowired
    public TodoItemController(final TodoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity addTodoItem(@RequestBody final AddTodoItemRequest request) {
        if (!StringUtils.hasText(request.getContent())) {
            return ResponseEntity.badRequest().build();
        }
        final TodoParameter parameter = new TodoParameter(request.getContent());
        final TodoItem todoItem = this.service.addTodoItem(parameter);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todoItem.getIndex())
                .toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity doneItem(@PathVariable("id") final long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<TodoItem> todoItem = this.service.markTodoItemDone(new TodoIndexParameter(id));
        if (todoItem.isPresent()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }


    @GetMapping
    public List<TodoItemResponse> list(@RequestParam(value = "all", defaultValue = "false") final boolean all) {
        final List<TodoItem> items = this.service.listItem(all);
        return items.stream().map(TodoItemResponse::new).collect(Collectors.toList());
    }
}





