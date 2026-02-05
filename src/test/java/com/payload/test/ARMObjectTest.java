package com.payload.test;

import com.payload.pl;
import com.payload.arm.ARMObject;
import org.json.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ARMObjectTest {

  @Test
  public void testGetBoolMethod() throws Exception {
    ARMObject obj = new ARMObject();
    obj.set("active", (Object) true);
    obj.set("disabled", (Object) false);

    assertTrue(obj.getBool("active"));
    assertFalse(obj.getBool("disabled"));

    // Test with default values
    assertTrue(obj.getBool("active", false));
    assertFalse(obj.getBool("disabled", true));
    assertTrue(obj.getBool("nonexistent", true));
    assertFalse(obj.getBool("nonexistent", false));
  }

  @Test
  public void testGetObjMethod() throws Exception {
    ARMObject obj = new ARMObject();

    JSONObject nestedJson = new JSONObject();
    nestedJson.put("name", "Test Account");
    nestedJson.put("balance", 1000.50);

    obj.set("account", (Object) nestedJson);

    ARMObject account = obj.getObj("account");
    assertNotNull(account);
    assertEquals("Test Account", account.getStr("name"));
    assertEquals(1000.50, account.getFloat("balance"), 0.001);

    assertNull(obj.getObj("nonexistent"));
  }

  @Test
  public void testPaymentClass() throws Exception {
    JSONObject paymentJson = new JSONObject();
    paymentJson.put("id", "pay_123");
    paymentJson.put("amount", 100.00);
    paymentJson.put("finalized", true);
    paymentJson.put("status", "completed");

    pl.Payment payment = new pl.Payment();
    payment.setJson(paymentJson);

    assertEquals("pay_123", payment.getStr("id"));
    assertTrue(payment.finalized);
    assertEquals("completed", payment.status);
  }

  @Test
  public void testWebhookLogClass() throws Exception {
    JSONObject webhookLogJson = new JSONObject();
    webhookLogJson.put("id", "whl_123");
    webhookLogJson.put("url", "https://example.com/webhook");

    JSONObject triggeredOnJson = new JSONObject();
    triggeredOnJson.put("id", "pay_123");
    triggeredOnJson.put("amount", 100.00);
    webhookLogJson.put("triggered_on", triggeredOnJson);

    pl.WebhookLog webhookLog = new pl.WebhookLog();
    webhookLog.setJson(webhookLogJson);

    assertEquals("whl_123", webhookLog.getStr("id"));
    assertEquals("https://example.com/webhook", webhookLog.url);

    assertNotNull(webhookLog.triggered_on);
    assertEquals("pay_123", webhookLog.triggered_on.getStr("id"));
    assertEquals(100.00, webhookLog.triggered_on.getFloat("amount"), 0.001);
  }
}
