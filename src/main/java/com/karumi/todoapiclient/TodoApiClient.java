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

import retrofit2.Retrofit;

import static com.karumi.todoapiclient.TodoApiClientConfig.*;

public class TodoApiClient {

  private final TodoService todoService;

  public TodoApiClient() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_ENDPOINT)
        .build();
    this.todoService = retrofit.create(TodoService.class);
  }
}
