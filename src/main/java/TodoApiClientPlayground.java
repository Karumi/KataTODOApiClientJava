/*
 *   Copyright (C) 2016 Karumi.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.karumi.todoapiclient.TodoApiClient;
import com.karumi.todoapiclient.dto.TaskDto;
import com.karumi.todoapiclient.exception.TodoApiClientException;
import java.util.List;

public class TodoApiClientPlayground {

  public static void main(String[] args) {
    TodoApiClient todoApiClient = new TodoApiClient();
    try {

      // Get all tasks.
      List<TaskDto> tasks = todoApiClient.getAllTasks();
      print(tasks);

      // Get task by id.
      TaskDto task = todoApiClient.getTaskById("1");
      print(task);

      //Delete task by id.
      todoApiClient.deleteTaskById("1");

      //Update task by id.
      TaskDto taskToUpdate = new TaskDto("1", "1", "Finish this kata", false);
      TaskDto updatedTask = todoApiClient.updateTaskById(taskToUpdate);
      print(updatedTask);

      //Add task.
      TaskDto taskToAdd = new TaskDto("1", "1", "Finish this kata", false);
      TaskDto addedTask = todoApiClient.addTask(taskToAdd);
      print(addedTask);

    } catch (TodoApiClientException e) {
      e.printStackTrace();
    }
  }

  private static void print(Object object) {
      System.out.println(object);
  }
}
