package com.todolist.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itemId")
    private Long itemId;
    @Column(name = "taskName")
    private String taskName;
    @Column(name = "createdDate")
    private Date createdDate;
}

