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

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.StringContains.containsString;

public class MockWebServerTest {

  private static final String FILE_ENCODING = "UTF-8";

  private MockWebServer server;

  @Before
  public void setUp() throws Exception {
    this.server = new MockWebServer();
    this.server.start();
  }

  @After
  public void tearDown() throws Exception {
    server.shutdown();
  }

  void enqueueMockResponse() {
    enqueueMockResponse(200);
  }

  void enqueueMockResponse(int code) {
    enqueueMockResponse(code, "{}");
  }

  void enqueueMockResponse(int code, String body) {
    MockResponse mockResponse = new MockResponse();
    mockResponse.setResponseCode(code);
    mockResponse.setBody(body);
    server.enqueue(mockResponse);
  }


  void assertRequestSentTo(String url) throws InterruptedException {
    RecordedRequest request = server.takeRequest();
    assertEquals(url, request.getPath());
  }

  void assertGetRequestSentTo(String url) throws InterruptedException {
    RecordedRequest request = server.takeRequest();
    assertEquals(url, request.getPath());
    assertEquals("GET", request.getMethod());
  }

  void assertPostRequestSentTo(String url) throws InterruptedException {
    RecordedRequest request = server.takeRequest();
    assertEquals(url, request.getPath());
    assertEquals("POST", request.getMethod());
  }

  protected void assertPutRequestSentTo(String url) throws InterruptedException {
    RecordedRequest request = server.takeRequest();
    assertEquals(url, request.getPath());
    assertEquals("PUT", request.getMethod());
  }

  void assertDeleteRequestSentTo(String url) throws InterruptedException {
    RecordedRequest request = server.takeRequest();
    assertEquals(url, request.getPath());
    assertEquals("DELETE", request.getMethod());
  }

  protected void assertRequestSentToContains(String... paths) throws InterruptedException {
    RecordedRequest request = server.takeRequest();

    for (String path : paths) {
      Assert.assertThat(request.getPath(), containsString(path));
    }
  }

  void assertRequestContainsHeader(String key, String expectedValue)
      throws InterruptedException {
    assertRequestContainsHeader(key, expectedValue, 0);
  }

  private void assertRequestContainsHeader(String key, String expectedValue, int requestIndex)
      throws InterruptedException {
    RecordedRequest recordedRequest = getRecordedRequestAtIndex(requestIndex);
    String value = recordedRequest.getHeader(key);
    assertEquals(expectedValue, value);
  }

  String getBaseEndpoint() {
    return server.url("/").toString();
  }

  void assertRequestBodyEquals(String jsonFile) throws InterruptedException, IOException {
    RecordedRequest request = server.takeRequest();
    assertEquals(getContentFromFile(jsonFile), request.getBody().readUtf8());
  }

  String getContentFromFile(String fileName) throws IOException {
    if (fileName == null) {
      return "";
    }
    fileName = getClass().getResource("/" + fileName).getFile();
    File file = new File(fileName);
    List<String> lines = FileUtils.readLines(file, FILE_ENCODING);
    StringBuilder stringBuilder = new StringBuilder();
    for (String line : lines) {
      stringBuilder.append(line);
    }
    return stringBuilder.toString();
  }

  private RecordedRequest getRecordedRequestAtIndex(int requestIndex) throws InterruptedException {
    RecordedRequest request = null;
    for (int i = 0; i <= requestIndex; i++) {
      request = server.takeRequest();
    }
    return request;
  }

}