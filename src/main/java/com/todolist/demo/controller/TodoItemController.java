package com.todolist.demo.controller;

import com.todolist.demo.exception.RecordNotFoundException;
import com.todolist.demo.model.TodoItem;
import com.todolist.demo.service.TodoItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class TodoItemController {

    private final TodoItemService todoItemService;
    private static final String TODO_ITEM = "todoItem";

    @Inject
    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @RequestMapping
    public String getAllTodoItems(Model model) {
        List<TodoItem> todoItemList = todoItemService.getAllTodoItems();
        model.addAttribute("todoItems", todoItemList);
        return "list-todoItems";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editTodoItemById(Model model, @PathVariable("id") Optional<Long> itemId) {
        if (itemId.isPresent()) {
            Optional<TodoItem> todoItem = todoItemService.getTodoItemById(itemId.get());
            model.addAttribute(TODO_ITEM, todoItem);
        } else {
            model.addAttribute(TODO_ITEM, new TodoItem());
        }
        return "add-edit-todoItem";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteTodoItemById(@PathVariable("id") Long id) throws RecordNotFoundException {
        todoItemService.deleteTodoItemById(id);
        return "redirect:/";
    }

    @PostMapping(path = "/createTodoItem")
    public String createOrUpdateTodoItem(TodoItem todoItem) {
        if (todoItem.getTaskName().isBlank()) {
            return "redirect:/";
        }
        todoItemService.createOrUpdateTodoItem(todoItem);
        return "redirect:/";
    }
}
