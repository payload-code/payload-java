package co.payload.test;

import co.payload.pl;
import co.payload.Session;
import co.payload.arm.ARMRequest;
import co.payload.Exceptions;
import java.util.List;
import java.lang.reflect.Field;
import java.io.FileNotFoundException;
import com.github.javafaker.Faker;

public class Fixtures {
    pl.Customer customer;
    pl.ProcessingAccount processing_account;
    Factory factory;

    public Fixtures(Factory factory) throws Exception {
        this.factory = factory;
        Session session = factory.session;

        this.customer = factory.createCustomer();

        List<pl.ProcessingAccount> pas = session.select(pl.ProcessingAccount.class).all();

        if (pas.size() == 0)
            this.processing_account = factory.createProcessingAccount();
        else
            this.processing_account = pas.get(0);
    }

}
