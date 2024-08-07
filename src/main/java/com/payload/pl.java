package com.payload;

import com.payload.arm.ARMObject;
import com.payload.arm.ARMRequest;
import com.payload.Exceptions;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;
import org.json.*;

public class pl {
	public static final String URL = "https://api.payload.com";
	public static volatile String api_key;
	public static volatile String api_url = URL;
	public static Session default_session = new Session(null);

	public static Map.Entry<String,Object> attr(String key, Object val) {
		return new AbstractMap.SimpleEntry<String,Object>(key, val);
	}

	public static class Customer extends ARMObject<Customer> {
		public String getObject(){ return "customer"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Customer>(Customer.class).select(args);
		}

		public static List<Customer> create(Customer... args) throws Exceptions.PayloadError {
			return new ARMRequest<Customer>(Customer.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Customer>(Customer.class).filter_by(attr, val);
		}

		public static List<Customer> all() throws Exceptions.PayloadError {
			return new ARMRequest<Customer>(Customer.class).all();
		}

		public static Customer get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Customer>(Customer.class).get(id);
		}
	}

	public static class ProcessingAccount extends ARMObject<ProcessingAccount> {
		public String getObject(){ return "processing_account"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).select(args);
		}

		public static List<ProcessingAccount> create(ProcessingAccount... args) throws Exceptions.PayloadError {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).filter_by(attr, val);
		}

		public static List<ProcessingAccount> all() throws Exceptions.PayloadError {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).all();
		}

		public static ProcessingAccount get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).get(id);
		}
	}

	public static class Org extends ARMObject<Org> {
		public String getObject(){ return "org"; }
		public String getEndpoint(){ return "/accounts/orgs"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Org>(Org.class).select(args);
		}

		public static List<Org> create(Org... args) throws Exceptions.PayloadError {
			return new ARMRequest<Org>(Org.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Org>(Org.class).filter_by(attr, val);
		}

		public static List<Org> all() throws Exceptions.PayloadError {
			return new ARMRequest<Org>(Org.class).all();
		}

		public static Org get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Org>(Org.class).get(id);
		}
	}

	public static class Transaction extends ARMObject<Transaction> {
		public String getObject(){ return "transaction"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Transaction>(Transaction.class).select(args);
		}

		public static List<Transaction> create(Transaction... args) throws Exceptions.PayloadError {
			return new ARMRequest<Transaction>(Transaction.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Transaction>(Transaction.class).filter_by(attr, val);
		}

		public static List<Transaction> all() throws Exceptions.PayloadError {
			return new ARMRequest<Transaction>(Transaction.class).all();
		}

		public static Transaction get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Transaction>(Transaction.class).get(id);
		}
	}

	public static class Payment extends ARMObject<Payment> {
		public String getObject(){ return "transaction"; }
		public String[] getPoly(){ return new String[]{"type", "payment"}; }

		public Payment() {
			super();
		}

		public Payment(float amount) {
			super();
			set("amount", amount);
		}

		public static ARMRequest select(String... args) {
			return new ARMRequest<Payment>(Payment.class).select(args);
		}

		public static List<Payment> create(Payment... args) throws Exceptions.PayloadError {
			return new ARMRequest<Payment>(Payment.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Payment>(Payment.class).filter_by(attr, val);
		}

		public static Payment get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Payment>(Payment.class).get(id);
		}

		public static List<Payment> all() throws Exceptions.PayloadError {
			return new ARMRequest<Payment>(Payment.class).all();
		}

		public PaymentMethod paymentMethod() {
			final JSONObject outerObj = obj;
			return new pl.PaymentMethod(){{
				setJson(outerObj.getJSONObject("payment_method"));
			}};
		}
	}

	public static class Refund extends ARMObject<Refund> {
		public String getObject(){ return "transaction"; }
		public String[] getPoly(){ return new String[]{"type", "refund"}; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Refund>(Refund.class).select(args);
		}

		public static List<Refund> create(Refund... args) throws Exceptions.PayloadError {
			return new ARMRequest<Refund>(Refund.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Refund>(Refund.class).filter_by(attr, val);
		}

		public static List<Refund> all() throws Exceptions.PayloadError {
			return new ARMRequest<Refund>(Refund.class).all();
		}

		public static Refund get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Refund>(Refund.class).get(id);
		}
	}

	public static class Deposit extends ARMObject<Deposit> {
		public String getObject(){ return "transaction"; }
		public String[] getPoly(){ return new String[]{"type", "deposit"}; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Deposit>(Deposit.class).select(args);
		}

		public static List<Deposit> create(Deposit... args) throws Exceptions.PayloadError {
			return new ARMRequest<Deposit>(Deposit.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Deposit>(Deposit.class).filter_by(attr, val);
		}

		public static List<Deposit> all() throws Exceptions.PayloadError {
			return new ARMRequest<Deposit>(Deposit.class).all();
		}

		public static Deposit get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Deposit>(Deposit.class).get(id);
		}
	}

	public static class Credit extends ARMObject<Credit> {
		public String getObject(){ return "transaction"; }
		public String[] getPoly(){ return new String[]{"type", "credit"}; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Credit>(Credit.class).select(args);
		}

		public static List<Credit> create(Credit... args) throws Exceptions.PayloadError {
			return new ARMRequest<Credit>(Credit.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Credit>(Credit.class).filter_by(attr, val);
		}

		public static List<Credit> all() throws Exceptions.PayloadError {
			return new ARMRequest<Credit>(Credit.class).all();
		}

		public static Credit get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Credit>(Credit.class).get(id);
		}
	}

	public static class Ledger extends ARMObject<Ledger> {
		public String getObject(){ return "transaction"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Ledger>(Ledger.class).select(args);
		}

		public static List<Ledger> create(Ledger... args) throws Exceptions.PayloadError {
			return new ARMRequest<Ledger>(Ledger.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Ledger>(Ledger.class).filter_by(attr, val);
		}

		public static List<Ledger> all() throws Exceptions.PayloadError {
			return new ARMRequest<Ledger>(Ledger.class).all();
		}

		public static Ledger get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Ledger>(Ledger.class).get(id);
		}
	}

	public static class PaymentMethod extends ARMObject<PaymentMethod> {
		public String getObject(){ return "payment_method"; }
		public Map<String,String> fieldmap() {
			return new HashMap<String, String>() {{
				put("card_number", "card");
				put("track1", "card");
				put("track2", "card");
				put("ksn", "card");
				put("device_sn", "card");
				put("magne_print", "card");
				put("magne_print_status", "card");
				put("card_number", "card");
				put("expiry", "card");
				put("account_number", "bank_account");
				put("routing_number", "bank_account");
				put("account_type", "bank_account");
			}};
		}

		public static ARMRequest select(String... args) {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).select(args);
		}

		public static List<PaymentMethod> create(PaymentMethod... args) throws Exceptions.PayloadError {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).filter_by(attr, val);
		}

		public static List<PaymentMethod> all() throws Exceptions.PayloadError {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).all();
		}

		public static PaymentMethod get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<PaymentMethod>(PaymentMethod.class).get(id);
		}
	}

	public static class Card extends PaymentMethod {
		public String[] getPoly(){ return new String[]{"type", "card"}; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Card>(Card.class).select(args);
		}

		public static List<Card> create(Card... args) throws Exceptions.PayloadError {
			return new ARMRequest<Card>(Card.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Card>(Card.class).filter_by(attr, val);
		}

		public static Card get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Card>(Card.class).get(id);
		}
	}

	public static class BankAccount extends PaymentMethod {
		public String[] getPoly(){ return new String[]{"type", "bank_account"}; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<BankAccount>(BankAccount.class).select(args);
		}

		public static List<BankAccount> create(BankAccount... args) throws Exceptions.PayloadError {
			return new ARMRequest<BankAccount>(BankAccount.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<BankAccount>(BankAccount.class).filter_by(attr, val);
		}

		public static BankAccount get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<BankAccount>(BankAccount.class).get(id);
		}
	}

	public static class BillingSchedule extends ARMObject<BillingSchedule> {
		public String getObject(){ return "billing_schedule"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).select(args);
		}

		public static List<BillingSchedule> create(BillingSchedule... args) throws Exceptions.PayloadError {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).filter_by(attr, val);
		}

		public static List<BillingSchedule> all() throws Exceptions.PayloadError {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).all();
		}

		public static BillingSchedule get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<BillingSchedule>(BillingSchedule.class).get(id);
		}
	}

	public static class BillingCharge extends ARMObject<BillingCharge> {
		public String getObject(){ return "billing_charge"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<BillingCharge>(BillingCharge.class).select(args);
		}

		public static List<BillingCharge> create(BillingCharge... args) throws Exceptions.PayloadError {
			return new ARMRequest<BillingCharge>(BillingCharge.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<BillingCharge>(BillingCharge.class).filter_by(attr, val);
		}

		public static List<BillingCharge> all() throws Exceptions.PayloadError {
			return new ARMRequest<BillingCharge>(BillingCharge.class).all();
		}

		public static BillingCharge get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<BillingCharge>(BillingCharge.class).get(id);
		}
	}

	public static class Invoice extends ARMObject<Invoice> {
		public String getObject(){ return "invoice"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Invoice>(Invoice.class).select(args);
		}

		public static List<Invoice> create(Invoice... args) throws Exceptions.PayloadError {
			return new ARMRequest<Invoice>(Invoice.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Invoice>(Invoice.class).filter_by(attr, val);
		}

		public static List<Invoice> all() throws Exceptions.PayloadError {
			return new ARMRequest<Invoice>(Invoice.class).all();
		}

		public static Invoice get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Invoice>(Invoice.class).get(id);
		}
	}

	public static class LineItem extends ARMObject<LineItem> {
		public String getObject(){ return "line_item"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<LineItem>(LineItem.class).select(args);
		}

		public static List<LineItem> create(LineItem... args) throws Exceptions.PayloadError {
			return new ARMRequest<LineItem>(LineItem.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<LineItem>(LineItem.class).filter_by(attr, val);
		}

		public static List<LineItem> all() throws Exceptions.PayloadError {
			return new ARMRequest<LineItem>(LineItem.class).all();
		}

		public static LineItem get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<LineItem>(LineItem.class).get(id);
		}
	}

	public static class ChargeItem extends LineItem {
		public String[] getPoly(){ return new String[]{"entry_type", "charge"}; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<ChargeItem>(ChargeItem.class).select(args);
		}

		public static List<ChargeItem> create(ChargeItem... args) throws Exceptions.PayloadError {
			return new ARMRequest<ChargeItem>(ChargeItem.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<ChargeItem>(ChargeItem.class).filter_by(attr, val);
		}

		public static ChargeItem get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<ChargeItem>(ChargeItem.class).get(id);
		}
	}

	public static class PaymentItem extends LineItem {
		public String[] getPoly(){ return new String[]{"entry_type", "payment"}; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<PaymentItem>(PaymentItem.class).select(args);
		}

		public static List<PaymentItem> create(PaymentItem... args) throws Exceptions.PayloadError {
			return new ARMRequest<PaymentItem>(PaymentItem.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<PaymentItem>(PaymentItem.class).filter_by(attr, val);
		}

		public static PaymentItem get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<PaymentItem>(PaymentItem.class).get(id);
		}
	}

	public static class Reader extends ARMObject<LineItem> {
		public String getObject(){ return "reader"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Reader>(Reader.class).select(args);
		}

		public static List<Reader> create(Reader... args) throws Exceptions.PayloadError {
			return new ARMRequest<Reader>(Reader.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Reader>(Reader.class).filter_by(attr, val);
		}

		public static Reader get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Reader>(Reader.class).get(id);
		}
	}

	public static class Webhook extends ARMObject<Webhook> {
		public String getObject(){ return "webhook"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<Webhook>(Webhook.class).select(args);
		}

		public static List<Webhook> create(Webhook... args) throws Exceptions.PayloadError {
			return new ARMRequest<Webhook>(Webhook.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<Webhook>(Webhook.class).filter_by(attr, val);
		}

		public static List<Webhook> all() throws Exceptions.PayloadError {
			return new ARMRequest<Webhook>(Webhook.class).all();
		}

		public static Webhook get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<Webhook>(Webhook.class).get(id);
		}
	}

	public static class PaymentLink extends ARMObject<PaymentLink> {
		public String getObject(){ return "payment_link"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<PaymentLink>(PaymentLink.class).select(args);
		}

		public static List<PaymentLink> create(PaymentLink... args) throws Exceptions.PayloadError {
			return new ARMRequest<PaymentLink>(PaymentLink.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<PaymentLink>(PaymentLink.class).filter_by(attr, val);
		}

		public static List<PaymentLink> all() throws Exceptions.PayloadError {
			return new ARMRequest<PaymentLink>(PaymentLink.class).all();
		}

		public static PaymentLink get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<PaymentLink>(PaymentLink.class).get(id);
		}
	}

	public static class PaymentActivation extends ARMObject<PaymentActivation> {
		public String getObject(){ return "payment_activation"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<PaymentActivation>(PaymentActivation.class).select(args);
		}

		public static List<PaymentActivation> create(PaymentActivation... args) throws Exceptions.PayloadError {
			return new ARMRequest<PaymentActivation>(PaymentActivation.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<PaymentActivation>(PaymentActivation.class).filter_by(attr, val);
		}

		public static List<PaymentActivation> all() throws Exceptions.PayloadError {
			return new ARMRequest<PaymentActivation>(PaymentActivation.class).all();
		}

		public static PaymentActivation get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<PaymentActivation>(PaymentActivation.class).get(id);
		}
	}

	public static class AccessToken extends ARMObject<AccessToken> {
		public String getObject(){ return "access_token"; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<AccessToken>(AccessToken.class).select(args);
		}

		public static List<AccessToken> create(AccessToken... args) throws Exceptions.PayloadError {
			return new ARMRequest<AccessToken>(AccessToken.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<AccessToken>(AccessToken.class).filter_by(attr, val);
		}

		public static List<AccessToken> all() throws Exceptions.PayloadError {
			return new ARMRequest<AccessToken>(AccessToken.class).all();
		}

		public static AccessToken get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<AccessToken>(AccessToken.class).get(id);
		}
	}

	public static class ClientToken extends ARMObject<ClientToken> {
		public String getObject(){ return "access_token"; }
		public String[] getPoly(){ return new String[]{"type", "client"}; }

		public static ARMRequest select(String... args) {
			return new ARMRequest<ClientToken>(ClientToken.class).select(args);
		}

		public static List<ClientToken> create(ClientToken... args) throws Exceptions.PayloadError {
			return new ARMRequest<ClientToken>(ClientToken.class).create(Arrays.asList(args));
		}

		public static ARMRequest filter_by(String attr, Object val) {
			return new ARMRequest<ClientToken>(ClientToken.class).filter_by(attr, val);
		}

		public static List<ClientToken> all() throws Exceptions.PayloadError {
			return new ARMRequest<ClientToken>(ClientToken.class).all();
		}

		public static ClientToken get(String id) throws Exceptions.PayloadError {
			return new ARMRequest<ClientToken>(ClientToken.class).get(id);
		}
	}

}
