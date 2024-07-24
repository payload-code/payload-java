package com.payload.test;

import com.payload.pl;
import com.payload.Session;
import com.payload.arm.ARMRequest;
import com.payload.Exceptions;
import java.util.List;
import java.lang.reflect.Field;
import java.io.FileNotFoundException;
import org.json.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import com.github.javafaker.Faker;
import com.payload.test.Factory;
import com.payload.test.Fixtures;

public class PayloadSessionTest {

    public static Faker faker = new Faker();
    public static Fixtures fixtures;
    public static Session session;

    @BeforeClass
    public static void setUpClass() throws Exception {
        if (System.getenv("API_URL") != null)
            session = new Session(System.getenv("API_KEY"), System.getenv("API_URL"));
        else
            session = new Session(System.getenv("API_KEY"));

        fixtures = new Fixtures(new Factory(session));
    }

    @Test(expected = Exceptions.Unauthorized.class)
    public void testNoGlobalApiKey() throws Exception {
        pl.Customer accnt = new pl.Customer() {
            {
                set("email", "test@gmail.com");
                set("name", "Test Account");
            }
        }.create();
    }

    @Test
    public void testCreateCust() throws Exception {
        pl.Customer accnt = this.session.create(new pl.Customer() {
            {
                set("email", "test@gmail.com");
                set("name", "Test Account");
            }
        });

        assertNotNull(accnt.getStr("id"));
        assertEquals(accnt.getStr("email"), "test@gmail.com");
        assertEquals(accnt.getStr("name"), "Test Account");
        assertSame(accnt.session, this.session);
    }

    @Test
    public void testGetCust() throws Exception {
        pl.Customer cust = this.session.select(pl.Customer.class).get(fixtures.customer.getStr("id"));
        assertEquals(cust.getStr("id"), fixtures.customer.getStr("id"));
        assertEquals(cust.getStr("email"), fixtures.customer.getStr("email"));
        assertSame(cust.session, this.session);

    }

    @Test(expected = NullPointerException.class)
    public void testBadGet() throws Exception {
        this.session.select(pl.Customer.class).get(null);
    }

    @Test
    public void testUpdateCust() throws Exception {
        final String origEmail = faker.internet().emailAddress();

        pl.Customer c = this.session.create(new pl.Customer() {
            {
                set("email", origEmail);
                set("name", "Test Account");
            }
        });

        assertEquals(c.getStr("email"), origEmail);

        String newEmail = faker.internet().emailAddress();

        c.update(pl.attr("email", newEmail));

        assertEquals(c.getStr("email"), newEmail);

        c = this.session.select(pl.Customer.class).get(c.getStr("id")); // Refresh

        assertEquals(c.getStr("email"), newEmail);
    }

    @Test(expected = Exceptions.NotFound.class)
    public void testDelCust() throws Exception {
        pl.Customer cust = this.session.create(new pl.Customer() {
            {
                set("email", faker.internet().emailAddress());
                set("name", "Test Account");
            }
        });

        cust.delete();

        cust = this.session.select(pl.Customer.class).get(cust.getStr("id"));
    }

}
