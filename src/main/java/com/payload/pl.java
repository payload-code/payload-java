package com.payload;

import com.payload.arm.ARMObject;
import com.payload.arm.ARMRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.io.IOException;
import org.json.*;

public class pl {
	public static final String URL = "https://api.payload.co";
	public static volatile String api_key;
	public static volatile String api_url = URL;

	public static Map.Entry<String,Object> attr(String key, Object val) {
		return new AbstractMap.SimpleEntry<String,Object>(key, val);
	}

	public static class Account extends ARMObject<Account> {
		public String getObject(){ return "account"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Account>(Account.class).select(args);
		}

		public static List<Account> create(Account... args) throws IOException {
			return new ARMRequest<Account>(Account.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Account>(Account.class).filter_by(attr, val);
		}

		public static Account get(String id) throws IOException {
			return new ARMRequest<Account>(Account.class).get(id);
		}
	}

	public static class Customer extends ARMObject<Customer> {
		public String getObject(){ return "account"; }
		public String getType(){ return "customer"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Customer>(Customer.class).select(args);
		}

		public static List<Customer> create(Customer... args) throws IOException {
			return new ARMRequest<Customer>(Customer.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Customer>(Customer.class).filter_by(attr, val);
		}

		public static Customer get(String id) throws IOException {
			return new ARMRequest<Customer>(Customer.class).get(id);
		}
	}

	public static class ProcessingAccount extends ARMObject<ProcessingAccount> {
		public String getObject(){ return "account"; }
		public String getType(){ return "processing"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).select(args);
		}

		public static List<ProcessingAccount> create(ProcessingAccount... args) throws IOException {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).filter_by(attr, val);
		}

		public static ProcessingAccount get(String id) throws IOException {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).get(id);
		}
	}

	public static class Transaction extends ARMObject<Transaction> {
		public String getObject(){ return "transaction"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Transaction>(Transaction.class).select(args);
		}

		public static List<Transaction> create(Transaction... args) throws IOException {
			return new ARMRequest<Transaction>(Transaction.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Transaction>(Transaction.class).filter_by(attr, val);
		}

		public static Transaction get(String id) throws IOException {
			return new ARMRequest<Transaction>(Transaction.class).get(id);
		}
	}

	public static class Payment extends ARMObject<Payment> {
		public String getObject(){ return "transaction"; }
		public String getType(){ return "payment"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Payment>(Payment.class).select(args);
		}

		public static List<Payment> create(Payment... args) throws IOException {
			return new ARMRequest<Payment>(Payment.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Payment>(Payment.class).filter_by(attr, val);
		}

		public static Payment get(String id) throws IOException {
			return new ARMRequest<Payment>(Payment.class).get(id);
		}
	}

	public static class Refund extends ARMObject<Refund> {
		public String getObject(){ return "transaction"; }
		public String getType(){ return "refund"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Refund>(Refund.class).select(args);
		}

		public static List<Refund> create(Refund... args) throws IOException {
			return new ARMRequest<Refund>(Refund.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Refund>(Refund.class).filter_by(attr, val);
		}

		public static Refund get(String id) throws IOException {
			return new ARMRequest<Refund>(Refund.class).get(id);
		}
	}

	public static class Ledger extends ARMObject<Ledger> {
		public String getObject(){ return "transaction"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Ledger>(Ledger.class).select(args);
		}

		public static List<Ledger> create(Ledger... args) throws IOException {
			return new ARMRequest<Ledger>(Ledger.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Ledger>(Ledger.class).filter_by(attr, val);
		}

		public static Ledger get(String id) throws IOException {
			return new ARMRequest<Ledger>(Ledger.class).get(id);
		}
	}

	public static class PaymentMethod extends ARMObject<PaymentMethod> {
		public String getObject(){ return "payment_method"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).select(args);
		}

		public static List<PaymentMethod> create(PaymentMethod... args) throws IOException {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).filter_by(attr, val);
		}

		public static PaymentMethod get(String id) throws IOException {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).get(id);
		}
	}

	public static class Card extends PaymentMethod {
		public String getType(){ return "card"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Card>(Card.class).select(args);
		}

		public static List<Card> create(Card... args) throws IOException {
			return new ARMRequest<Card>(Card.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Card>(Card.class).filter_by(attr, val);
		}

		public static Card get(String id) throws IOException {
			return new ARMRequest<Card>(Card.class).get(id);
		}
	}

	public static class BankAccount extends PaymentMethod {
		public String getType(){ return "bank_account"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<BankAccount>(BankAccount.class).select(args);
		}

		public static List<BankAccount> create(BankAccount... args) throws IOException {
			return new ARMRequest<BankAccount>(BankAccount.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<BankAccount>(BankAccount.class).filter_by(attr, val);
		}

		public static BankAccount get(String id) throws IOException {
			return new ARMRequest<BankAccount>(BankAccount.class).get(id);
		}
	}

	public static class BillingSchedule extends ARMObject<BillingSchedule> {
		public String getObject(){ return "billing_schedule"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).select(args);
		}

		public static List<BillingSchedule> create(BillingSchedule... args) throws IOException {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).filter_by(attr, val);
		}

		public static BillingSchedule get(String id) throws IOException {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).get(id);
		}
	}

	public static class BillingCharge extends ARMObject<BillingCharge> {
		public String getObject(){ return "billing_charge"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<BillingCharge>(BillingCharge.class).select(args);
		}

		public static List<BillingCharge> create(BillingCharge... args) throws IOException {
			return new ARMRequest<BillingCharge>(BillingCharge.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<BillingCharge>(BillingCharge.class).filter_by(attr, val);
		}

		public static BillingCharge get(String id) throws IOException {
			return new ARMRequest<BillingCharge>(BillingCharge.class).get(id);
		}
	}

	public static class Invoice extends ARMObject<Invoice> {
		public String getObject(){ return "invoice"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Invoice>(Invoice.class).select(args);
		}

		public static List<Invoice> create(Invoice... args) throws IOException {
			return new ARMRequest<Invoice>(Invoice.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Invoice>(Invoice.class).filter_by(attr, val);
		}

		public static Invoice get(String id) throws IOException {
			return new ARMRequest<Invoice>(Invoice.class).get(id);
		}
	}

	public static class LineItem extends ARMObject<LineItem> {
		public String getObject(){ return "line_item"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<LineItem>(LineItem.class).select(args);
		}

		public static List<LineItem> create(LineItem... args) throws IOException {
			return new ARMRequest<LineItem>(LineItem.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<LineItem>(LineItem.class).filter_by(attr, val);
		}

		public static LineItem get(String id) throws IOException {
			return new ARMRequest<LineItem>(LineItem.class).get(id);
		}
	}

	public static class ChargeItem extends LineItem {
		public String getType(){ return "charge"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<ChargeItem>(ChargeItem.class).select(args);
		}

		public static List<ChargeItem> create(ChargeItem... args) throws IOException {
			return new ARMRequest<ChargeItem>(ChargeItem.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<ChargeItem>(ChargeItem.class).filter_by(attr, val);
		}

		public static ChargeItem get(String id) throws IOException {
			return new ARMRequest<ChargeItem>(ChargeItem.class).get(id);
		}
	}

	public static class PaymentItem extends LineItem {
		public String getType(){ return "payment"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<PaymentItem>(PaymentItem.class).select(args);
		}

		public static List<PaymentItem> create(PaymentItem... args) throws IOException {
			return new ARMRequest<PaymentItem>(PaymentItem.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<PaymentItem>(PaymentItem.class).filter_by(attr, val);
		}

		public static PaymentItem get(String id) throws IOException {
			return new ARMRequest<PaymentItem>(PaymentItem.class).get(id);
		}
	}
}
