package com.payload.test;

import com.payload.pl;
import com.payload.Session;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ApiVersionTest {

  private String originalApiVersion;

  @Before
  public void setUp() {
    originalApiVersion = pl.api_version;
  }

  @After
  public void tearDown() {
    pl.api_version = originalApiVersion;
  }

  @Test
  public void testApiVersionDefaultsToNull() {
    assertNull(pl.api_version);
    Session session = new Session("test_key");
    assertNull(session.getApiVersion());
  }

  @Test
  public void testApiVersionWhenSet() {
    pl.api_version = "2";
    Session session = new Session("test_key");
    assertEquals("2", session.getApiVersion());
  }

  @Test
  public void testDifferentApiVersionValues() {
    String[] versions = { "v2.0", "v1.0", "v3.1", "v2.5" };

    for (String version : versions) {
      pl.api_version = version;
      Session session = new Session("test_key");
      assertEquals(version, session.getApiVersion());
    }
  }

  @Test
  public void testGlobalApiVersionAffectsAllSessions() {
    pl.api_version = "2";

    Session session1 = new Session("key1");
    Session session2 = new Session("key2", "https://api.payload.com");

    assertEquals("2", session1.getApiVersion());
    assertEquals("2", session2.getApiVersion());
  }

  @Test
  public void testDefaultSessionUsesGlobalApiVersion() {
    pl.api_version = "2";
    assertEquals("2", pl.default_session.getApiVersion());
  }

}
