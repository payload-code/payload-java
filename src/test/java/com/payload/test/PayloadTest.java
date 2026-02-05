package com.payload.test;

import com.payload.pl;
import com.payload.Session;
import com.payload.arm.ARMObject;
import com.payload.arm.ARMRequest;
import com.payload.Exceptions;
import java.util.List;
import java.time.LocalDate;
import org.json.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import com.github.javafaker.Faker;
import com.payload.test.Factory;
import com.payload.test.Fixtures;

public class PayloadTest {

  public static Faker faker = new Faker();
  public static Fixtures fixtures;
  public static Factory factory;

  @BeforeClass
  public static void setUpClass() throws Exception {
    if (System.getenv("API_URL") != null)
      pl.api_url = System.getenv("API_URL");
    pl.api_key = System.getenv("API_KEY");

    factory = new Factory();
    fixtures = new Fixtures(factory);
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    pl.api_url = pl.URL;
    pl.api_key = null;
  }

  @Test
  public void testCardPayment() throws Exception {
    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.Card() {
          {
            set("card", new JSONObject() {
              {
                put("card_number", "4242 4242 4242 4242");
                put("expiry", "12/29");
                put("card_code", "123");
              }
            });
            set("billing_address", new JSONObject() {
              {
                put("postal_code", "12345");
              }
            });
          }
        });
        create();
      }
    };

    assertNotNull(pmt.getStr("id"));
    assertEquals(pmt.getFloat("amount"), 100., 0.0001);
    assertEquals(pmt.getJObj("payment_method").getJSONObject("card").get("card_number"), "xxxxxxxxxxxx4242");
  }

  @Test
  public void testCardPaymentMapping() throws Exception {
    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.Card() {
          {
            set("card", new JSONObject() {
              {
                put("card_number", "4242 4242 4242 4242");
                put("expiry", "12/29");
                put("card_code", "123");
              }
            });
            set("billing_address", new JSONObject() {
              {
                put("postal_code", "12345");
              }
            });
          }
        });
        create();
      }
    };

    assertNotNull(pmt.getStr("id"));
    assertEquals(pmt.getFloat("amount"), 100., 0.0001);
    assertEquals(pmt.paymentMethod().getStr("card_number"), "xxxxxxxxxxxx4242");
  }

  @Test
  public void testBankAccountPayment() throws Exception {
    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.BankAccount() {
          {
            set("account_number", "1234567890");
            set("routing_number", "036001808");
            set("account_type", "checking");
            set("account_holder", "Test Account");
          }
        });
        create();
      }
    };

    assertNotNull(pmt.getStr("id"));
    assertEquals(pmt.getFloat("amount"), 100., 0.0001);
    assertEquals(pmt.paymentMethod().getStr("account_number"), "xxxxxx7890");
    assertEquals(pmt.paymentMethod().getStr("routing_number"), "xxxxx1808");
    assertEquals(pmt.paymentMethod().getStr("account_type"), "checking");
  }

  @Test
  public void testCreateCust() throws Exception {
    pl.Customer accnt = new pl.Customer() {
      {
        set("email", "test@gmail.com");
        set("name", "Test Account");
        create();
      }
    };

    assertNotNull(accnt.getStr("id"));
    assertEquals(accnt.getStr("email"), "test@gmail.com");
    assertEquals(accnt.getStr("name"), "Test Account");
  }

  @Test
  public void testMultiCreate() throws Exception {
    pl.Customer[] custs = new pl.Customer[] { new pl.Customer() {
      {
        set("email", "test3@gmail.com");
        set("name", "Test Account");
      }
    }, new pl.Customer() {
      {
        set("email", "test4@gmail.com");
        set("name", "Test Account");
      }
    } };

    List<pl.Customer> out = pl.Customer.create(custs);

    assertNotNull(out.get(0).getStr("id"));
    assertEquals(out.get(0).getStr("email"), "test3@gmail.com");
    assertEquals(out.get(0).getStr("name"), "Test Account");

    assertNotNull(out.get(1).getStr("id"));
    assertEquals(out.get(1).getStr("email"), "test4@gmail.com");
    assertEquals(out.get(1).getStr("name"), "Test Account");
  }

  @Test
  public void testSelectCust() throws Exception {

    List<pl.Customer> out = pl.Customer.filter_by("email", fixtures.customer.getStr("email")).all();
    assertEquals(out.get(0).getStr("id"), fixtures.customer.getStr("id"));
    assertEquals(out.size(), 1);
  }

  @Test
  public void testGetCust() throws Exception {
    pl.Customer cust = pl.Customer.get(fixtures.customer.getStr("id"));
    assertEquals(cust.getStr("id"), fixtures.customer.getStr("id"));
    assertEquals(cust.getStr("email"), fixtures.customer.getStr("email"));
  }

  @Test(expected = NullPointerException.class)
  public void testBadGet() throws Exception {
    pl.Customer.get(null);
  }

  @Test
  public void testUpdateCust() throws Exception {
    final String origEmail = faker.internet().emailAddress();

    pl.Customer c = new pl.Customer() {
      {
        set("email", origEmail);
        set("name", "Test Account");
        create();
      }
    };

    assertEquals(c.getStr("email"), origEmail);

    String newEmail = faker.internet().emailAddress();

    c.update(pl.attr("email", newEmail));

    assertEquals(c.getStr("email"), newEmail);

    c = pl.Customer.get(c.getStr("id")); // Refresh

    assertEquals(c.getStr("email"), newEmail);
  }

  @Test(expected = Exceptions.NotFound.class)
  public void testDelCust() throws Exception {
    pl.Customer cust = new pl.Customer() {
      {
        set("email", faker.internet().emailAddress());
        set("name", "Test Account");
        create();
      }
    };

    cust.delete();

    cust = pl.Customer.get(cust.getStr("id"));
  }

  @Test
  public void testCreateInvPaymentLink() throws Exception {
    final pl.Invoice inv = new pl.Invoice() {
      {
        set("description", "Test Invoice");
        set("type", "Bill");
        set("due_date", "2021-06-15");
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("customer", new pl.Customer() {
          {
            set("email", "test2@gmail.com");
            set("name", "Test Account");
          }
        });
        set("items", new pl.LineItem[] { new pl.ChargeItem() {
          {
            set("description", "Test Charge");
            set("amount", "29.99");
          }
        } });
      }
    }.create();

    pl.PaymentLink lnk = new pl.PaymentLink() {
      {
        set("invoice_id", inv.getStr("id"));
      }
    }.create();

    assertEquals(lnk.getStr("description"), "Test Invoice");
    assertEquals(lnk.getFloat("amount"), 29.99, 0.0001);
  }

  @Test
  public void testCreateClientToken() throws Exception {
    pl.ClientToken clientToken = new pl.ClientToken().create();
    assertNotNull(clientToken.getStr("id"));
    assertEquals(clientToken.getStr("type"), "client");
  }

  @Test
  public void testCreateClientTokenWithCheckoutPageIntent() throws Exception {
    pl.ClientToken clientToken = new pl.ClientToken() {
      {
        set("intent", new JSONObject() {
          {
            put("checkout_page", new JSONObject() {
              {
                put("amount", 100);
                put("description", "Test Payment");
                put("redirects", new JSONObject() {
                  {
                    put("completed_url", "http://localhost/payment-complete");
                    put("return_url", "http://localhost/cart");
                  }
                });
              }
            });
          }
        });
      }
    }.create();
    assertNotNull(clientToken.getStr("id"));
    assertNotNull(clientToken.getJObj("intent").getJSONObject("checkout_page").getString("url"));
  }

  @Test
  public void testPaymentFilters() throws Exception {
    final String randDescription = faker.lorem().sentence();

    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("description", randDescription);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.Card() {
          {
            set("card", new JSONObject() {
              {
                put("card_number", "4242 4242 4242 4242");
                put("expiry", "12/29");
                put("card_code", "123");
              }
            });
            set("billing_address", new JSONObject() {
              {
                put("postal_code", "12345");
              }
            });
          }
        });
        create();
      }
    };

    List<pl.Payment> payments = (List<pl.Payment>) pl.Payment
        .filter_by(
            pl.attr("amount").gt(99),
            pl.attr("amount").lt(200),
            pl.attr("description").contains(randDescription),
            pl.attr("created_at").gt(LocalDate.of(2019, 2, 1)))
        .all();

    assertEquals(1, payments.size());
    assertEquals(pmt.getStr("id"), payments.get(0).getStr("id"));
  }

  @Test
  public void testVoidPayment() throws Exception {
    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.Card() {
          {
            set("card", new JSONObject() {
              {
                put("card_number", "4242 4242 4242 4242");
                put("expiry", "12/29");
                put("card_code", "123");
              }
            });
            set("billing_address", new JSONObject() {
              {
                put("postal_code", "12345");
              }
            });
          }
        });
        create();
      }
    };

    pmt.update(pl.attr("status", "voided"));

    assertEquals("voided", pmt.getStr("status"));
  }

  @Test
  public void testRefundCardPayment() throws Exception {
    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.Card() {
          {
            set("card", new JSONObject() {
              {
                put("card_number", "4242 4242 4242 4242");
                put("expiry", "12/29");
                put("card_code", "123");
              }
            });
            set("billing_address", new JSONObject() {
              {
                put("postal_code", "12345");
              }
            });
          }
        });
        create();
      }
    };

    final String pmtId = pmt.getStr("id");

    pl.Refund refund = new pl.Refund() {
      {
        set("amount", 100);
        set("ledger", new JSONObject[] { new JSONObject() {
          {
            put("assoc_transaction_id", pmtId);
          }
        } });
        create();
      }
    };

    assertEquals("refund", refund.getStr("type"));
    assertEquals(100., refund.getFloat("amount"), 0.0001);
    assertEquals("approved", refund.getStr("status_code"));
  }

  @Test
  public void testPartialRefundCardPayment() throws Exception {
    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.Card() {
          {
            set("card", new JSONObject() {
              {
                put("card_number", "4242 4242 4242 4242");
                put("expiry", "12/29");
                put("card_code", "123");
              }
            });
            set("billing_address", new JSONObject() {
              {
                put("postal_code", "12345");
              }
            });
          }
        });
        create();
      }
    };

    final String pmtId = pmt.getStr("id");

    pl.Refund refund = new pl.Refund() {
      {
        set("amount", 10);
        set("ledger", new JSONObject[] { new JSONObject() {
          {
            put("assoc_transaction_id", pmtId);
          }
        } });
        create();
      }
    };

    assertEquals("refund", refund.getStr("type"));
    assertEquals(10., refund.getFloat("amount"), 0.0001);
    assertEquals("approved", refund.getStr("status_code"));
  }

  @Test
  public void testRefundBankPayment() throws Exception {
    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.BankAccount() {
          {
            set("account_number", "1234567890");
            set("routing_number", "036001808");
            set("account_type", "checking");
            set("account_holder", "Test Account");
          }
        });
        create();
      }
    };

    final String pmtId = pmt.getStr("id");

    pl.Refund refund = new pl.Refund() {
      {
        set("amount", 100);
        set("ledger", new JSONObject[] { new JSONObject() {
          {
            put("assoc_transaction_id", pmtId);
          }
        } });
        create();
      }
    };

    assertEquals("refund", refund.getStr("type"));
    assertEquals(100., refund.getFloat("amount"), 0.0001);
    assertEquals("approved", refund.getStr("status_code"));
  }

  @Test
  public void testPartialRefundBankPayment() throws Exception {
    pl.Payment pmt = new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.BankAccount() {
          {
            set("account_number", "1234567890");
            set("routing_number", "036001808");
            set("account_type", "checking");
            set("account_holder", "Test Account");
          }
        });
        create();
      }
    };

    final String pmtId = pmt.getStr("id");

    pl.Refund refund = new pl.Refund() {
      {
        set("amount", 10);
        set("ledger", new JSONObject[] { new JSONObject() {
          {
            put("assoc_transaction_id", pmtId);
          }
        } });
        create();
      }
    };

    assertEquals("refund", refund.getStr("type"));
    assertEquals(10., refund.getFloat("amount"), 0.0001);
    assertEquals("approved", refund.getStr("status_code"));
  }

  @Test
  public void testConvenienceFee() throws Exception {
    pl.Payment pmt = (pl.Payment) pl.Payment
        .select("*", "fee", "conv_fee")
        .create(new pl.Payment() {
          {
            set("amount", 100);
            set("processing_id", fixtures.processing_account.getStr("id"));
            set("payment_method", new pl.Card() {
              {
                set("card", new JSONObject() {
                  {
                    put("card_number", "4242 4242 4242 4242");
                    put("expiry", "12/29");
                    put("card_code", "123");
                  }
                });
                set("billing_address", new JSONObject() {
                  {
                    put("postal_code", "12345");
                  }
                });
              }
            });
          }
        });

    assertNotNull(pmt);
    assertTrue(pmt.getFloat("fee") >= 0);
    assertTrue(pmt.getFloat("conv_fee") >= 0);
  }

  @Test(expected = Exceptions.InvalidAttributes.class)
  public void testInvalidPaymentMethodTypeInvalidAttributes() throws Exception {
    new pl.Payment() {
      {
        set("amount", 100);
        set("processing_id", fixtures.processing_account.getStr("id"));
        set("payment_method", new pl.PaymentMethod() {
          {
            set("type", "bank_account");
            set("card", new JSONObject() {
              {
                put("card_number", "4242 4242 4242 4242");
                put("expiry", "12/29");
                put("card_code", "123");
              }
            });
          }
        });
        create();
      }
    };
  }

  @Test
  public void testPayments() throws Exception {
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
  public void testPaymentMethods() throws Exception {
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
  public void testWebhooks() throws Exception {
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
  public void testWebhookLog() throws Exception {
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
  public void testSyntheticAccounts() throws Exception {
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
