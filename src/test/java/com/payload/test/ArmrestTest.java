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
    // Set a key that doesn't exist to test default behavior

    // Test basic boolean retrieval
    assertTrue(obj.getBool("active"));
    assertFalse(obj.getBool("disabled"));

    // Test boolean with default value
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

    // Should return default when JSON is invalid
    assertTrue(obj.getBool("invalid_bool", true));
    assertFalse(obj.getBool("invalid_bool", false));
    assertTrue(obj.getBool("null_bool", true));
    assertTrue(obj.getBool("nonexistent", true));
  }

  @Test
  public void testGetObjMethod() throws Exception {
    ARMObject obj = new ARMObject();

    // Create nested JSON object
    JSONObject nestedJson = new JSONObject();
    nestedJson.put("name", "Test Account");
    nestedJson.put("balance", 1000.50);

    obj.set("account", nestedJson);

    // Test retrieving nested object
    ARMObject account = obj.getObj("account");
    assertNotNull(account);
    assertEquals("Test Account", account.getStr("name"));
    assertEquals(1000.50, account.getFloat("balance"), 0.001);

    // Test with nonexistent key
    assertNull(obj.getObj("nonexistent"));

    // Test with invalid JSON
    obj.set("invalid", "not_an_object");
    assertNull(obj.getObj("invalid"));
  }

  @Test
  public void testGetListMethod() throws Exception {
    ARMObject obj = new ARMObject();

    // Create JSON array of payment objects
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

    // Test retrieving typed list
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

    // Test with non-existent key
    List<pl.Payment> payments1 = obj.getList("nonexistent", pl.Payment.class);
    assertNotNull(payments1);
    assertEquals(0, payments1.size());

    // Test with invalid data type
    obj.set("invalid_list", "not_an_array");
    List<pl.Payment> payments2 = obj.getList("invalid_list", pl.Payment.class);
    assertNotNull(payments2);
    assertEquals(0, payments2.size());
  }

  @Test
  public void testPaymentSetJson() throws Exception {
    JSONObject paymentJson = new JSONObject();
    paymentJson.put("id", "pay_123");
    paymentJson.put("amount", 100.00);
    paymentJson.put("finalized", true);
    paymentJson.put("status", "completed");

    pl.Payment payment = new pl.Payment();
    payment.setJson(paymentJson);

    assertEquals("pay_123", payment.getStr("id"));
    assertEquals(100.00, payment.getFloat("amount"), 0.001);
    assertTrue(payment.finalized);
    assertEquals("completed", payment.status);

    // Test with missing optional fields
    JSONObject partialJson = new JSONObject();
    partialJson.put("id", "pay_456");
    partialJson.put("amount", 50.00);

    pl.Payment partialPayment = new pl.Payment();
    partialPayment.setJson(partialJson);

    assertEquals("pay_456", partialPayment.getStr("id"));
    assertEquals(50.00, partialPayment.getFloat("amount"), 0.001);
    assertNull(partialPayment.finalized);
    assertNull(partialPayment.status);
  }

  @Test
  public void testPaymentMethodSetJson() throws Exception {
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
    assertEquals("ach", paymentMethod.transfer_type);

    // Test synthetic account
    assertNotNull(paymentMethod.synthetic);
    assertNotNull(paymentMethod.synthetic.balance);
    assertEquals(500.00, paymentMethod.synthetic.balance.available, 0.001);
    assertEquals(100.00, paymentMethod.synthetic.balance.pending, 0.001);

    // Test with missing synthetic data
    JSONObject basicJson = new JSONObject();
    basicJson.put("id", "pm_456");
    basicJson.put("type", "bank");

    pl.PaymentMethod basicPaymentMethod = new pl.PaymentMethod();
    basicPaymentMethod.setJson(basicJson);

    assertEquals("pm_456", basicPaymentMethod.getStr("id"));
    assertEquals("bank", basicPaymentMethod.getStr("type"));
    assertNull(basicPaymentMethod.transfer_type);
    assertNull(basicPaymentMethod.synthetic);
  }

  @Test
  public void testWebhookSetJson() throws Exception {
    JSONObject webhookJson = new JSONObject();
    webhookJson.put("id", "wh_123");
    webhookJson.put("url", "https://example.com/webhook");
    webhookJson.put("trigger", "payment.created");

    pl.Webhook webhook = new pl.Webhook();
    webhook.setJson(webhookJson);

    assertEquals("wh_123", webhook.getStr("id"));
    assertEquals("https://example.com/webhook", webhook.url);
    assertEquals("payment.created", webhook.trigger);

    // Test with missing fields
    JSONObject partialJson = new JSONObject();
    partialJson.put("id", "wh_456");

    pl.Webhook partialWebhook = new pl.Webhook();
    partialWebhook.setJson(partialJson);

    assertEquals("wh_456", partialWebhook.getStr("id"));
    assertNull(partialWebhook.url);
    assertNull(partialWebhook.trigger);
  }

  @Test
  public void testWebhookLogSetJson() throws Exception {
    JSONObject webhookLogJson = new JSONObject();
    webhookLogJson.put("id", "whl_123");
    webhookLogJson.put("url", "https://example.com/webhook");

    JSONObject triggeredOnJson = new JSONObject();
    triggeredOnJson.put("id", "pay_123");
    triggeredOnJson.put("amount", 100.00);
    webhookLogJson.put("triggered_on", triggeredOnJson);

    pl.WebhookLog webhookLog = new pl.WebhookLog();
    webhookLog.setJson(webhookLogJson);

    // Test basic fields
    assertEquals("whl_123", webhookLog.getStr("id"));
    assertEquals("https://example.com/webhook", webhookLog.url);

    // Test nested triggered_on object
    assertNotNull(webhookLog.triggered_on);
    assertEquals("pay_123", webhookLog.triggered_on.getStr("id"));
    assertEquals(100.00, webhookLog.triggered_on.getFloat("amount"), 0.001);

    // Test with missing triggered_on
    JSONObject basicJson = new JSONObject();
    basicJson.put("id", "whl_456");

    pl.WebhookLog basicWebhookLog = new pl.WebhookLog();
    basicWebhookLog.setJson(basicJson);

    assertEquals("whl_456", basicWebhookLog.getStr("id"));
    assertNull(basicWebhookLog.url);
    assertNull(basicWebhookLog.triggered_on);
  }

  @Test
  public void testSyntheticBalance() throws Exception {
    JSONObject balanceJson = new JSONObject();
    balanceJson.put("available", 750.25);
    balanceJson.put("pending", 150.75);

    pl.SyntheticBalance balance = new pl.SyntheticBalance(balanceJson);

    assertEquals(750.25, balance.available, 0.001);
    assertEquals(150.75, balance.pending, 0.001);

    // Test with missing fields (should default to 0)
    JSONObject partialJson = new JSONObject();
    partialJson.put("available", 500.00);

    pl.SyntheticBalance partialBalance = new pl.SyntheticBalance(partialJson);

    assertEquals(500.00, partialBalance.available, 0.001);
    assertEquals(0.00, partialBalance.pending, 0.001);
  }

  @Test
  public void testSyntheticAccount() throws Exception {
    JSONObject accountJson = new JSONObject();
    JSONObject balanceJson = new JSONObject();
    balanceJson.put("available", 1000.00);
    balanceJson.put("pending", 200.00);
    accountJson.put("balance", balanceJson);

    pl.SyntheticAccount account = new pl.SyntheticAccount(accountJson);

    assertNotNull(account.balance);
    assertEquals(1000.00, account.balance.available, 0.001);
    assertEquals(200.00, account.balance.pending, 0.001);

    // Test with missing balance
    JSONObject partialJson = new JSONObject();

    pl.SyntheticAccount partialAccount = new pl.SyntheticAccount(partialJson);

    assertNull(partialAccount.balance);
  }

}
