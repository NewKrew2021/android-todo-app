package com.todolist.room.data.db

import androidx.room.*
import com.todolist.room.data.model.TodoItem
@Dao
interface TodoDao {

    @Query ("SELECT * FROM (SELECT * FROM TodoItem ORDER BY time DESC) ORDER BY complete")
    fun getAllTodoItem() : List<TodoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodo(todoItem: TodoItem)

    @Query("DELETE FROM TodoItem WHERE id = :id")
    fun removeTodo(id: Int)

    @Update
    fun updateTodo(todoItem: TodoItem)


}