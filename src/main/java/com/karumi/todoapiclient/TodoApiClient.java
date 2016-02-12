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

package com.karumi.todoapiclient;

import com.karumi.todoapiclient.dto.TaskDto;
import com.karumi.todoapiclient.exception.ItemNotFoundException;
import com.karumi.todoapiclient.exception.NetworkErrorException;
import com.karumi.todoapiclient.exception.TodoApiClientException;
import com.karumi.todoapiclient.exception.UnknownErrorException;
import com.karumi.todoapiclient.interceptor.DefaultHeadersInterceptor;
import java.io.IOException;
import java.util.List;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.karumi.todoapiclient.TodoApiClientConfig.BASE_ENDPOINT;

public class TodoApiClient {

  private final TodoService todoService;

  public TodoApiClient() {
    this(BASE_ENDPOINT);
  }

  public TodoApiClient(String baseEndpoint) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(baseEndpoint)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    retrofit.client().interceptors().add(new DefaultHeadersInterceptor());
    this.todoService = retrofit.create(TodoService.class);
  }

  public List<TaskDto> getAllTasks() throws TodoApiClientException {
    try {
      Response<List<TaskDto>> response = todoService.getAll().execute();
      inspectResponseForErrors(response);
      return response.body();
    } catch (IOException e) {
      throw new NetworkErrorException();
    }
  }

  public TaskDto getTaskById(String taskId) throws TodoApiClientException {
    try {
      Response<TaskDto> response = todoService.getById(taskId).execute();
      inspectResponseForErrors(response);
      return response.body();
    } catch (IOException e) {
      throw new NetworkErrorException();
    }
  }

  public TaskDto addTask(TaskDto task) throws TodoApiClientException {
    try {
      Response<TaskDto> response = todoService.add(task).execute();
      inspectResponseForErrors(response);
      return response.body();
    } catch (IOException e) {
      throw new NetworkErrorException();
    }
  }

  public TaskDto updateTaskById(TaskDto task) throws TodoApiClientException {
    try {
      Response<TaskDto> response = todoService.updateById(task.getId(), task).execute();
      inspectResponseForErrors(response);
      return response.body();
    } catch (IOException e) {
      throw new NetworkErrorException();
    }
  }

  public void deleteTaskById(String taskId) throws TodoApiClientException {
    try {
      Response<Void> response = todoService.deleteById(taskId).execute();
      inspectResponseForErrors(response);
    } catch (IOException e) {
      throw new NetworkErrorException();
    }
  }

  private void inspectResponseForErrors(Response response) throws TodoApiClientException {
    int code = response.code();
    if (code == 404) {
      throw new ItemNotFoundException();
    } else if (code >= 400) {
      throw new UnknownErrorException(code);
    }
  }
}
