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
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import com.github.javafaker.Faker;

public class Factory {
    Session session;

    public static Faker faker = new Faker();

    public Factory() {
        this.session = pl.default_session;
    }

    public Factory(Session session) {
        this.session = session;
    }

    public pl.Customer createCustomer() throws Exception {
        return this.session.create(new pl.Customer() {
            {
                set("email", faker.internet().emailAddress());
                set("name", "Test Account");
            }
        });
    }

    public pl.ProcessingAccount createProcessingAccount() throws Exception {
        return this.session.create(new pl.ProcessingAccount() {
            {
                set("name", "Processing Account");
                set("legal_entity", new JSONObject() {
                    {
                        put("legal_name", "Test");
                        put("type", "INDIVIDUAL_SOLE_PROPRIETORSHIP");
                        put("ein", "23 423 4234");
                        put("street_address", "123 Example St");
                        put("unit_number", "Suite 1");
                        put("city", "New York");
                        put("state_province", "NY");
                        put("state_incorporated", "NY");
                        put("postal_code", "11238");
                        put("phone_number", "(111) 222-3333");
                        put("website", "https://payload.co");
                        put("start_date", "05/01/2015");
                        put("contact_name", "Test Person");
                        put("contact_email", "test.person@example.com");
                        put("contact_title", "VP");
                        put("owners", new JSONArray() {
                            {
                                put(new JSONObject() {
                                    {
                                        put("full_name", "Test Person");
                                        put("email", "test.person@example.com");
                                        put("ssn", "234 23 4234");
                                        put("birth_date", "06/20/1985");
                                        put("title", "CEO");
                                        put("ownership", "100");
                                        put("street_address", "4455 Carver Woods Drive, Suite 200");
                                        put("unit_number", "2408");
                                        put("city", "Cincinnati");
                                        put("state_province", "OH");
                                        put("postal_code", "45242");
                                        put("phone_number", "(111) 222-3333");
                                        put("type", "owner");
                                    }
                                });
                            }
                        });
                    }
                });
                set("payment_methods", new JSONArray() {
                    {
                        put(new pl.BankAccount() {
                            {
                                set("account_number", "1234567890");
                                set("routing_number", "021000121");
                                set("account_type", "checking");
                                set("default_deposit_method", true);
                            }
                        }.obj);
                    }
                });
            }
        });
    }

}
