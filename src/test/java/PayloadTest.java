package co.payload.test;

import co.payload.pl;
import co.payload.Session;
import co.payload.arm.ARMRequest;
import co.payload.Exceptions;
import java.util.List;
import java.lang.reflect.Field;
import java.io.FileNotFoundException;
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
import com.github.javafaker.Faker;
import co.payload.test.Factory;
import co.payload.test.Fixtures;

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
                        set("card_number", "4242 4242 4242 4242");
                        set("expiry", "12/29");
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

}
