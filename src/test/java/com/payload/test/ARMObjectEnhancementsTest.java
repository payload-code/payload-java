package com.payload.test;

import com.payload.pl;
import com.payload.arm.ARMObject;
import org.json.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.List;

public class ARMObjectEnhancementsTest {

  @Test
  public void testGetBoolMethod() throws Exception {
    ARMObject obj = new ARMObject();
    obj.set("active", true);
    obj.set("disabled", false);
    obj.set("missing", null);

    // Test basic boolean retrieval
    assertTrue(obj.getBool("active"));
    assertFalse(obj.getBool("disabled"));

    // Test boolean with default value
    assertTrue(obj.getBool("active", false));
    assertFalse(obj.getBool("disabled", true));
    assertTrue(obj.getBool("missing", true));
    assertFalse(obj.getBool("nonexistent", false));
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
    assertFalse(obj.getBool("nonexistent", true));
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

}
