package com.todolist.demo.service;

import com.todolist.demo.exception.RecordNotFoundException;
import com.todolist.demo.model.TodoItem;
import com.todolist.demo.repository.TodoItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;

    public TodoItemService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItemList = todoItemRepository.findAll();
        if (!todoItemList.isEmpty()) {
            return todoItemList;
        } else {
            return List.of();
        }
    }

    public Optional<TodoItem> getTodoItemById(Long itemId) {
        return todoItemRepository.findById(itemId);

    }

    public TodoItem createOrUpdateTodoItem(TodoItem todoItem) {
        if (StringUtils.isEmpty(todoItem.getItemId())) {
            todoItem.setCreatedDate(new Date());
            todoItemRepository.save(todoItem);
        } else {
            Optional<TodoItem> todoItemToUpdate = todoItemRepository.findById(todoItem.getItemId());
            if (todoItemToUpdate.isPresent()) {
                TodoItem newTodoItem = todoItemToUpdate.get();
                newTodoItem.setTaskName(todoItem.getTaskName());
                newTodoItem.setCreatedDate(new Date());
                todoItemRepository.save(newTodoItem);
            } else {
                todoItemRepository.save(todoItem);
            }
        }
        return todoItem;
    }

    public void deleteTodoItemById(Long itemId) throws RecordNotFoundException {
        Optional<TodoItem> todoItem = todoItemRepository.findById(itemId);
        if (todoItem.isPresent()) {
            todoItemRepository.deleteById(itemId);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
