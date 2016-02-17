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
import com.karumi.todoapiclient.exception.UnknownErrorException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TodoApiClientTest extends MockWebServerTest {

  private static final String ANY_TASK_ID = "1";
  private static final TaskDto ANY_TASK = new TaskDto("1", "2", "Finish this kata", false);

  private TodoApiClient apiClient;

  @Before public void setUp() throws Exception {
    super.setUp();
    String mockWebServerEndpoint = getBaseEndpoint();
    apiClient = new TodoApiClient(mockWebServerEndpoint);
  }

  @Test public void sendsAcceptAndContentTypeHeaders() throws Exception {
    enqueueMockResponse();

    apiClient.getAllTasks();

    assertRequestContainsHeader("Accept", "application/json");
  }

  @Test public void sendsContentTypeHeader() throws Exception {
    enqueueMockResponse();

    apiClient.getAllTasks();

    assertRequestContainsHeader("Content-Type", "application/json");
  }

  @Test public void sendsGetAllTaskRequestToTheCorrectEndpoint() throws Exception {
    enqueueMockResponse();

    apiClient.getAllTasks();

    assertGetRequestSentTo("/todos");
  }

  @Test public void parsesTasksProperlyGettingAllTheTasks() throws Exception {
    enqueueMockResponse(200, "getTasksResponse.json");

    List<TaskDto> tasks = apiClient.getAllTasks();

    assertEquals(tasks.size(), 200);
    assertTaskContainsExpectedValues(tasks.get(0));
  }

  @Test(expected = UnknownErrorException.class)
  public void throwsUnknownErrorExceptionIfThereIsNotHandledErrorGettingAllTasks()
      throws Exception {
    enqueueMockResponse(418);

    apiClient.getAllTasks();
  }

  @Test public void sendsGetTaskByIdRequestToTheCorrectPath() throws Exception {
    enqueueMockResponse();

    apiClient.getTaskById(ANY_TASK_ID);

    assertGetRequestSentTo("/todos/" + ANY_TASK_ID);
  }

  @Test public void parsesTaskProperlyGettingTaskById() throws Exception {
    enqueueMockResponse(200, "getTaskByIdResponse.json");

    TaskDto task = apiClient.getTaskById(ANY_TASK_ID);

    assertTaskContainsExpectedValues(task);
  }

  @Test(expected = ItemNotFoundException.class)
  public void returnsItemNotFoundGettingTaskByIdIfThereIsNoTaskWithThePassedId() throws Exception {
    enqueueMockResponse(404);

    apiClient.getTaskById(ANY_TASK_ID);
  }

  @Test(expected = UnknownErrorException.class)
  public void throwsUnknownErrorExceptionIfThereIsNotHandledErrorGettingTaskById()
      throws Exception {
    enqueueMockResponse(418);

    apiClient.getTaskById(ANY_TASK_ID);
  }

  @Test public void sendsAddTaskRequestToTheCorrectPath() throws Exception {
    enqueueMockResponse();

    apiClient.addTask(ANY_TASK);

    assertPostRequestSentTo("/todos");
  }

  @Test public void sendsTheCorrectBodyAddingANewTask() throws Exception {
    enqueueMockResponse();

    apiClient.addTask(ANY_TASK);

    assertRequestBodyEquals("addTaskRequest.json");
  }

  @Test public void testParsesTheTaskCreatedProperlyAddingANewTask() throws Exception {
    enqueueMockResponse(201, "addTaskResponse.json");

    TaskDto task = apiClient.addTask(ANY_TASK);

    assertTaskContainsExpectedValues(task);
  }

  @Test(expected = UnknownErrorException.class)
  public void returnsUnknownErrorIfThereIsAnyErrorAddingATask() throws Exception {
    enqueueMockResponse(418);

    apiClient.addTask(ANY_TASK);
  }

  @Test public void sendsTheRequestToTheCorrectPathDeletingATask() throws Exception {
    enqueueMockResponse();

    apiClient.deleteTaskById(ANY_TASK_ID);

    assertDeleteRequestSentTo("/todos/1");
  }

  @Test(expected = ItemNotFoundException.class)
  public void returnsItemNotFoundIfThereIsNoTaskWithIdTheAssociateId() throws Exception {
    enqueueMockResponse(404);

    apiClient.deleteTaskById(ANY_TASK_ID);
  }

  @Test(expected = UnknownErrorException.class)
  public void returnsUnknownErrorIfThereIsAnyErrorDeletingTask() throws Exception {
    enqueueMockResponse(418);

    apiClient.deleteTaskById(ANY_TASK_ID);
  }

  @Test public void sendsTheExpectedBodyUpdatingATask() throws Exception {
    enqueueMockResponse();

    apiClient.updateTaskById(ANY_TASK);

    assertRequestBodyEquals("updateTaskRequest.json");
  }

  @Test public void sendsRequestToTheCorrectPathUpdatingATask() throws Exception {
    enqueueMockResponse();

    apiClient.updateTaskById(ANY_TASK);

    assertRequestSentTo("/todos/1");
  }

  @Test public void parsesTheTaskProperlyUpdatingATask() throws Exception {
    enqueueMockResponse(200, "updateTaskResponse.json");

    TaskDto task = apiClient.updateTaskById(ANY_TASK);

    assertTaskContainsExpectedValues(task);
  }

  @Test(expected = ItemNotFoundException.class)
  public void returnsItemNotFoundErrorIfThereIsNoTaskToUpdateWithTheUsedId() throws Exception {
    enqueueMockResponse(404);

    apiClient.updateTaskById(ANY_TASK);
  }

  @Test(expected = UnknownErrorException.class)
  public void returnsUnknownErrorIfThereIsAnyHandledErrorUpdatingATask() throws Exception {
    enqueueMockResponse(418);

    apiClient.updateTaskById(ANY_TASK);
  }

  private void assertTaskContainsExpectedValues(TaskDto task) {
    assertEquals(task.getId(), "1");
    assertEquals(task.getUserId(), "1");
    assertEquals(task.getTitle(), "delectus aut autem");
    assertFalse(task.isFinished());
  }
}
