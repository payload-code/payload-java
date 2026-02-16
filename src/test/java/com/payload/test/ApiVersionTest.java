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
  }

  @Test
  public void testApiVersionWhenSet() {
    pl.api_version = "2";
    assertEquals("2", pl.api_version);
  }

  @Test
  public void testDifferentApiVersionValues() {
    String[] versions = { "v2.0", "v1.0", "v3.1", "v2.5" };

    for (String version : versions) {
      pl.api_version = version;
      assertEquals(version, pl.api_version);
    }
  }

  @Test
  public void testSessionApiVersionOverridesGlobal() {
    pl.api_version = "1";
    Session session = new Session("test_key", null, "2");
    assertEquals("2", session.getApiVersion());
  }

  @Test
  public void testSessionApiVersionFallsBackToGlobal() {
    pl.api_version = "1";
    Session session = new Session("test_key");
    assertEquals("1", session.getApiVersion());
  }

  @Test
  public void testSessionApiVersionNullWhenBothNull() {
    pl.api_version = null;
    Session session = new Session("test_key");
    assertNull(session.getApiVersion());
  }

  @Test
  public void testSessionWithExplicitNullFallsBackToGlobal() {
    pl.api_version = "1";
    Session session = new Session("test_key", null, null);
    assertEquals("1", session.getApiVersion());
  }

  @Test
  public void testEndpointAppendsS() {
    pl.Customer customer = new pl.Customer();
    assertEquals("/customers", customer.getEndpoint());
  }

  @Test
  public void testEndpointDoesNotDoubleS() {
    pl.ProcessingSettings settings = new pl.ProcessingSettings();
    assertEquals("/processing_settings", settings.getEndpoint());
  }

}
