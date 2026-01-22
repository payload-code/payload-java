package com.payload.test;

import com.payload.Session;
import com.payload.pl;
import com.payload.arm.ARMObject;
import com.payload.arm.ARMRequest;
import com.payload.Exceptions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserAgentTest {

  private static class RecordingHttpURLConnection extends HttpURLConnection {
    private final Map<String, String> headers = new HashMap<>();

    protected RecordingHttpURLConnection(URL u) {
      super(u);
    }

    @Override
    public void setRequestProperty(String key, String value) {
      headers.put(key, value);
    }

    public String getHeader(String key) {
      return headers.get(key);
    }

    @Override
    public int getResponseCode() throws IOException {
      return 200;
    }

    @Override
    public InputStream getInputStream() {
      JSONObject obj = new JSONObject();
      obj.put("object", "list");
      obj.put("values", new JSONArray());
      byte[] bytes = obj.toString().getBytes(StandardCharsets.UTF_8);
      return new ByteArrayInputStream(bytes);
    }

    @Override
    public void disconnect() {}

    @Override
    public boolean usingProxy() {
      return false;
    }

    @Override
    public void connect() throws IOException {}
  }

  private static class TestARMRequest<T> extends ARMRequest<T> {
    private final HttpURLConnection connection;

    public TestARMRequest(Class<T> cls, Session session, HttpURLConnection connection) {
      super(cls, session);
      this.connection = connection;
    }

    @Override
    protected HttpURLConnection openConnection(URL url) throws IOException {
      return connection;
    }
  }

  public static class DummyObject extends ARMObject<DummyObject> {
    @Override
    public String getObject() {
      return "dummy";
    }

    @Override
    public String getEndpoint() {
      return "/dummy";
    }
  }

  @Test
  public void testUserAgentUsesRuntimeVersion() throws Exceptions.PayloadError, IOException {
    URL url = new URL("https://api.payload.com/dummy");
    RecordingHttpURLConnection conn = new RecordingHttpURLConnection(url);
    Session session = new Session("test_key", "https://api.payload.com");

    TestARMRequest<DummyObject> req =
        new TestARMRequest<DummyObject>(DummyObject.class, session, conn);

    // Trigger a GET request which should set headers and read an empty list response
    List<DummyObject> result = (List<DummyObject>) req._request("GET", null, null);
    assertNotNull(result);
    assertTrue(result.isEmpty());

    String userAgent = conn.getHeader("User-Agent");
    assertNotNull(userAgent);
    assertTrue(userAgent.startsWith("payload-java/"));
    assertEquals("payload-java/" + pl.VERSION, userAgent);
  }
}


