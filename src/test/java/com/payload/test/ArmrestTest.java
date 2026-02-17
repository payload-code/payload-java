package com.payload.test;

import com.payload.pl;
import com.payload.arm.ARMObject;
import org.json.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.List;

public class ArmrestTest {

  @Test
  public void testGetBoolMethod() throws Exception {
    ARMObject obj = new ARMObject();
    obj.set("active", true);
    obj.set("disabled", false);

    assertTrue(obj.getBool("active"));
    assertFalse(obj.getBool("disabled"));

    assertTrue(obj.getBool("active", false));
    assertFalse(obj.getBool("disabled", true));
    assertTrue(obj.getBool("nonexistent", true));
    assertFalse(obj.getBool("another_nonexistent", false));
  }

  @Test
  public void testGetBoolWithJSONExceptionHandling() throws Exception {
    ARMObject obj = new ARMObject();
    obj.set("invalid_bool", "not_a_boolean");
    obj.set("null_bool", JSONObject.NULL);

    assertTrue(obj.getBool("invalid_bool", true));
    assertFalse(obj.getBool("invalid_bool", false));
    assertTrue(obj.getBool("null_bool", true));
    assertTrue(obj.getBool("nonexistent", true));
  }

  @Test
  public void testGetObjMethod() throws Exception {
    ARMObject obj = new ARMObject();

    JSONObject nestedJson = new JSONObject();
    nestedJson.put("name", "Test Account");
    nestedJson.put("balance", 1000.50);
    obj.set("account", nestedJson);

    ARMObject account = obj.getObj("account");
    assertNotNull(account);
    assertEquals("Test Account", account.getStr("name"));
    assertEquals(1000.50, account.getFloat("balance"), 0.001);

    assertNull(obj.getObj("nonexistent"));

    obj.set("invalid", "not_an_object");
    assertNull(obj.getObj("invalid"));
  }

  @Test
  public void testGetListMethod() throws Exception {
    ARMObject obj = new ARMObject();

    JSONArray paymentsArray = new JSONArray();

    JSONObject payment1 = new JSONObject();
    payment1.put("id", "pay_123");
    payment1.put("amount", 100.00);
    payment1.put("status", "completed");

    JSONObject payment2 = new JSONObject();
    payment2.put("id", "pay_456");
    payment2.put("amount", 50.00);
    payment2.put("status", "pending");

    paymentsArray.put(payment1);
    paymentsArray.put(payment2);
    obj.set("payments", paymentsArray);

    List<pl.Payment> payments = obj.getList("payments", pl.Payment.class);
    assertNotNull(payments);
    assertEquals(2, payments.size());

    pl.Payment firstPayment = payments.get(0);
    assertEquals("pay_123", firstPayment.getStr("id"));
    assertEquals(100.00, firstPayment.getFloat("amount"), 0.001);
    assertEquals("completed", firstPayment.getStr("status"));

    pl.Payment secondPayment = payments.get(1);
    assertEquals("pay_456", secondPayment.getStr("id"));
    assertEquals(50.00, secondPayment.getFloat("amount"), 0.001);
    assertEquals("pending", secondPayment.getStr("status"));
  }

  @Test
  public void testGetListWithEmptyArray() throws Exception {
    ARMObject obj = new ARMObject();
    obj.set("empty_list", new JSONArray());

    List<pl.Payment> payments = obj.getList("empty_list", pl.Payment.class);
    assertNotNull(payments);
    assertEquals(0, payments.size());
  }

  @Test
  public void testGetListWithInvalidData() throws Exception {
    ARMObject obj = new ARMObject();

    List<pl.Payment> payments1 = obj.getList("nonexistent", pl.Payment.class);
    assertNotNull(payments1);
    assertEquals(0, payments1.size());

    obj.set("invalid_list", "not_an_array");
    List<pl.Payment> payments2 = obj.getList("invalid_list", pl.Payment.class);
    assertNotNull(payments2);
    assertEquals(0, payments2.size());
  }

  @Test
  public void testPaymentDynamicAccess() throws Exception {
    JSONObject paymentJson = new JSONObject();
    paymentJson.put("id", "pay_123");
    paymentJson.put("amount", 100.00);
    paymentJson.put("finalized", true);
    paymentJson.put("status", "completed");

    pl.Payment payment = new pl.Payment();
    payment.setJson(paymentJson);

    assertEquals("pay_123", payment.getStr("id"));
    assertEquals(100.00, payment.getFloat("amount"), 0.001);
    assertTrue(payment.getBool("finalized"));
    assertEquals("completed", payment.getStr("status"));

    JSONObject partialJson = new JSONObject();
    partialJson.put("id", "pay_456");
    partialJson.put("amount", 50.00);

    pl.Payment partialPayment = new pl.Payment();
    partialPayment.setJson(partialJson);

    assertEquals("pay_456", partialPayment.getStr("id"));
    assertEquals(50.00, partialPayment.getFloat("amount"), 0.001);
    assertFalse(partialPayment.getBool("finalized", false));
    assertNull(partialPayment.getStr("status"));
  }

  @Test
  public void testPaymentMethodDynamicAccess() throws Exception {
    JSONObject paymentMethodJson = new JSONObject();
    paymentMethodJson.put("id", "pm_123");
    paymentMethodJson.put("type", "card");
    paymentMethodJson.put("transfer_type", "ach");

    JSONObject syntheticJson = new JSONObject();
    JSONObject balanceJson = new JSONObject();
    balanceJson.put("available", 500.00);
    balanceJson.put("pending", 100.00);
    syntheticJson.put("balance", balanceJson);
    paymentMethodJson.put("synthetic", syntheticJson);

    pl.PaymentMethod paymentMethod = new pl.PaymentMethod();
    paymentMethod.setJson(paymentMethodJson);

    assertEquals("pm_123", paymentMethod.getStr("id"));
    assertEquals("card", paymentMethod.getStr("type"));
    assertEquals("ach", paymentMethod.getStr("transfer_type"));

    ARMObject synthetic = paymentMethod.getObj("synthetic");
    assertNotNull(synthetic);
    ARMObject balance = synthetic.getObj("balance");
    assertNotNull(balance);
    assertEquals(500.00, balance.getFloat("available"), 0.001);
    assertEquals(100.00, balance.getFloat("pending"), 0.001);

    JSONObject basicJson = new JSONObject();
    basicJson.put("id", "pm_456");
    basicJson.put("type", "bank");

    pl.PaymentMethod basicPaymentMethod = new pl.PaymentMethod();
    basicPaymentMethod.setJson(basicJson);

    assertEquals("pm_456", basicPaymentMethod.getStr("id"));
    assertEquals("bank", basicPaymentMethod.getStr("type"));
    assertNull(basicPaymentMethod.getStr("transfer_type"));
    assertNull(basicPaymentMethod.getObj("synthetic"));
  }

  @Test
  public void testWebhookDynamicAccess() throws Exception {
    JSONObject webhookJson = new JSONObject();
    webhookJson.put("id", "wh_123");
    webhookJson.put("url", "https://example.com/webhook");
    webhookJson.put("trigger", "payment.created");

    pl.Webhook webhook = new pl.Webhook();
    webhook.setJson(webhookJson);

    assertEquals("wh_123", webhook.getStr("id"));
    assertEquals("https://example.com/webhook", webhook.getStr("url"));
    assertEquals("payment.created", webhook.getStr("trigger"));

    JSONObject partialJson = new JSONObject();
    partialJson.put("id", "wh_456");

    pl.Webhook partialWebhook = new pl.Webhook();
    partialWebhook.setJson(partialJson);

    assertEquals("wh_456", partialWebhook.getStr("id"));
    assertNull(partialWebhook.getStr("url"));
    assertNull(partialWebhook.getStr("trigger"));
  }

  @Test
  public void testWebhookLogDynamicAccess() throws Exception {
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
    assertEquals("https://example.com/webhook", webhookLog.getStr("url"));

    ARMObject triggeredOn = webhookLog.getObj("triggered_on");
    assertNotNull(triggeredOn);
    assertEquals("pay_123", triggeredOn.getStr("id"));
    assertEquals(100.00, triggeredOn.getFloat("amount"), 0.001);

    JSONObject basicJson = new JSONObject();
    basicJson.put("id", "whl_456");

    pl.WebhookLog basicWebhookLog = new pl.WebhookLog();
    basicWebhookLog.setJson(basicJson);

    assertEquals("whl_456", basicWebhookLog.getStr("id"));
    assertNull(basicWebhookLog.getStr("url"));
    assertNull(basicWebhookLog.getObj("triggered_on"));
  }

  @Test
  public void testNestedBalanceAccess() throws Exception {
    JSONObject pmJson = new JSONObject();
    pmJson.put("id", "pm_789");

    JSONObject syntheticJson = new JSONObject();
    JSONObject balanceJson = new JSONObject();
    balanceJson.put("available", 750.25);
    balanceJson.put("pending", 150.75);
    syntheticJson.put("balance", balanceJson);
    pmJson.put("synthetic", syntheticJson);

    pl.PaymentMethod pm = new pl.PaymentMethod();
    pm.setJson(pmJson);

    double available = pm.getObj("synthetic").getObj("balance").getFloat("available");
    double pending = pm.getObj("synthetic").getObj("balance").getFloat("pending");

    assertEquals(750.25, available, 0.001);
    assertEquals(150.75, pending, 0.001);
  }

}
