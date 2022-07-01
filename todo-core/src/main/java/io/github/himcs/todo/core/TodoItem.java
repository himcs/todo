package io.github.himcs.todo.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "todo_items")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private long index;
    @Column
    private String content;
    @Column
    @Setter
    private boolean done;

    public TodoItem(final String content) {
        this.content = content;
        done = false;
    }

}
