package com.todolist.demo;

import com.todolist.demo.model.TodoItem;
import com.todolist.demo.repository.TodoItemRepository;
import com.todolist.demo.service.TodoItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class TodoItemServiceTest {

    @Autowired
    private TodoItemService todoItemService;

    @MockBean
    private TodoItemRepository todoItemRepository;

    @Test
    void find_todoItem_by_id() {
        TodoItem todoItem = createdToDoItem(1L, "shopping");

        doReturn(Optional.of(todoItem)).when(todoItemRepository).findById(1L);

        Optional<TodoItem> returnedTodoItem = todoItemService.getTodoItemById(1L);

        assertTrue(returnedTodoItem.isPresent());
        assertSame(returnedTodoItem.get(), todoItem);
    }

    @Test
    void find_todoItem_by_id_not_found_returns_empty() {
        doReturn(Optional.empty()).when(todoItemRepository).findById(1L);
        Optional<TodoItem> returnedTodoItem = todoItemService.getTodoItemById(1L);
        assertFalse(returnedTodoItem.isPresent());
    }

    @Test
    void find_all_todoItems() {
        TodoItem todoItem1 = createdToDoItem(1L, "shopping");
        TodoItem todoItem2 = createdToDoItem(2L, "buy a new computer");

        when(todoItemRepository.findAll()).thenReturn(Arrays.asList(todoItem1, todoItem2));

        List<TodoItem> todoItemList = todoItemService.getAllTodoItems();

        assertEquals(2, todoItemList.size());
    }

    @Test
    void save_new_todoItem() {
        TodoItem todoItem = createdToDoItem(1L, "go for a walk");

        doReturn(todoItem).when(todoItemRepository).save(any());

        TodoItem returnedTodoItem = todoItemService.createOrUpdateTodoItem(todoItem);

        assertNotNull(returnedTodoItem);
        assertEquals("go for a walk", returnedTodoItem.getTaskName());
    }

    private TodoItem createdToDoItem(Long itemId, String itemTask) {
        TodoItem todoItem = new TodoItem();
        todoItem.setItemId(itemId);
        todoItem.setTaskName(itemTask);
        return todoItem;
    }
}
