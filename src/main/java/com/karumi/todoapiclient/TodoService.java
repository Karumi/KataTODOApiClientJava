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

interface TodoService {

  @GET("todos") Call<List<TaskDto>> getAll();
  @GET("todos/{taskId}") Call<TaskDto> getById(@Path("taskId") String taskId);
  @POST("todos") Call<TaskDto> addTask(@Body TaskDto task);
  @PUT("todos/{taskId}") Call<TaskDto> getById(@Path("taskId") String taskId, @Body TaskDto task);
  @DELETE("todos/{taskId}") Call<List<TaskDto>> deleteById(@Path("taskId") String taskId);

}
