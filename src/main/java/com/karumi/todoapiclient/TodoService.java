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
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import static com.karumi.todoapiclient.TodoApiClientConfig.TASKS_ENDPOINT;

interface TodoService {

  @GET(TASKS_ENDPOINT) Call<List<TaskDto>> getAll();

  @GET(TASKS_ENDPOINT + "/{taskId}") Call<TaskDto> getById(@Path("taskId") String taskId);

  @POST(TASKS_ENDPOINT) Call<TaskDto> add(@Body TaskDto task);

  @PUT(TASKS_ENDPOINT + "/{taskId}") Call<TaskDto> updateById(@Path("taskId") String taskId,
      @Body TaskDto task);

  @DELETE(TASKS_ENDPOINT + "/{taskId}") Call<List<TaskDto>> deleteById(
      @Path("taskId") String taskId);
}
