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
  public static final String VERSION;
  public static volatile String api_key;
  public static volatile String api_url = URL;
  public static volatile String api_version = null;
  public static Session default_session = new Session(null);

  static {
    Package pkg = pl.class.getPackage();
    String implVersion = pkg != null ? pkg.getImplementationVersion() : null;
    VERSION = implVersion != null ? implVersion : "dev";
  }

  public static Map.Entry<String, Object> attr(String key, Object val) {
    return new AbstractMap.SimpleEntry<String, Object>(key, val);
  }

  public static Attr attr(String key) {
    return new Attr(key);
  }

  public static class Filter implements Map.Entry<String, Object> {
    private final String attr;
    private Object opval;

    public Filter(String attr, Object opval) {
      this.attr = attr;
      this.opval = opval;
    }

    @Override
    public String getKey() {
      return attr;
    }

    @Override
    public Object getValue() {
      return opval;
    }

    @Override
    public Object setValue(Object value) {
      Object old = opval;
      opval = value;
      return old;
    }

    public Filter or(Filter other) {
      if (!other.attr.equals(this.attr)) {
        throw new IllegalArgumentException("`or` only works on the same attribute");
      }
      return new Filter(this.attr, String.valueOf(this.opval) + "|" + String.valueOf(other.opval));
    }
  }

  public static class Attr {
    private final String key;

    public Attr(String key) {
      this.key = key;
    }

    @Override
    public String toString() {
      return key;
    }

    public Filter gt(Object val) {
      return new Filter(key, ">" + val);
    }

    public Filter lt(Object val) {
      return new Filter(key, "<" + val);
    }

    public Filter gte(Object val) {
      return new Filter(key, ">=" + val);
    }

    public Filter lte(Object val) {
      return new Filter(key, "<=" + val);
    }

    public Filter contains(Object val) {
      return new Filter(key, "?*" + val);
    }

    public Filter eq(Object val) {
      return new Filter(key, val);
    }

    public Filter ne(Object val) {
      return new Filter(key, "!" + val);
    }

    public Attr desc() {
      return new Attr("desc(" + key + ")");
    }

    public Attr asc() {
      return new Attr("asc(" + key + ")");
    }
  }

  public static class Customer extends ARMObject<Customer> {
    public String getObject() {
      return "customer";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Customer>(Customer.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Customer>(Customer.class).select(args);
    }

    public static List<Customer> create(Customer... args) throws Exceptions.PayloadError {
      return new ARMRequest<Customer>(Customer.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Customer>(Customer.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Customer>(Customer.class).filter_by(attrs);
    }

    public static List<Customer> all() throws Exceptions.PayloadError {
      return new ARMRequest<Customer>(Customer.class).all();
    }

    public static Customer get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Customer>(Customer.class).get(id);
    }
  }

  public static class ProcessingAccount extends ARMObject<ProcessingAccount> {
    public String getObject() {
      return "processing_account";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).select(args);
    }

    public static List<ProcessingAccount> create(ProcessingAccount... args) throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).filter_by(attrs);
    }

    public static List<ProcessingAccount> all() throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).all();
    }

    public static ProcessingAccount get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingAccount>(ProcessingAccount.class).get(id);
    }
  }

  public static class Org extends ARMObject<Org> {
    public String getObject() {
      return "org";
    }

    public String getEndpoint() {
      return "/accounts/orgs";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Org>(Org.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Org>(Org.class).select(args);
    }

    public static List<Org> create(Org... args) throws Exceptions.PayloadError {
      return new ARMRequest<Org>(Org.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Org>(Org.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Org>(Org.class).filter_by(attrs);
    }

    public static List<Org> all() throws Exceptions.PayloadError {
      return new ARMRequest<Org>(Org.class).all();
    }

    public static Org get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Org>(Org.class).get(id);
    }
  }

  public static class Transaction extends ARMObject<Transaction> {
    public String getObject() {
      return "transaction";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Transaction>(Transaction.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Transaction>(Transaction.class).select(args);
    }

    public static List<Transaction> create(Transaction... args) throws Exceptions.PayloadError {
      return new ARMRequest<Transaction>(Transaction.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Transaction>(Transaction.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Transaction>(Transaction.class).filter_by(attrs);
    }

    public static List<Transaction> all() throws Exceptions.PayloadError {
      return new ARMRequest<Transaction>(Transaction.class).all();
    }

    public static Transaction get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Transaction>(Transaction.class).get(id);
    }
  }

  public static class Payment extends ARMObject<Payment> {

    public String getObject() {
      return "transaction";
    }

    public String[] getPoly() {
      return new String[] { "type", "payment" };
    }

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

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Payment>(Payment.class).select(args);
    }

    public static List<Payment> create(Payment... args) throws Exceptions.PayloadError {
      return new ARMRequest<Payment>(Payment.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Payment>(Payment.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Payment>(Payment.class).filter_by(attrs);
    }

    public static Payment get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Payment>(Payment.class).get(id);
    }

    public static List<Payment> all() throws Exceptions.PayloadError {
      return new ARMRequest<Payment>(Payment.class).all();
    }

    public PaymentMethod paymentMethod() {
      final JSONObject outerObj = obj;
      return new pl.PaymentMethod() {
        {
          setJson(outerObj.getJSONObject("payment_method"));
        }
      };
    }
  }

  public static class Refund extends ARMObject<Refund> {
    public String getObject() {
      return "transaction";
    }

    public String[] getPoly() {
      return new String[] { "type", "refund" };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Refund>(Refund.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Refund>(Refund.class).select(args);
    }

    public static List<Refund> create(Refund... args) throws Exceptions.PayloadError {
      return new ARMRequest<Refund>(Refund.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Refund>(Refund.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Refund>(Refund.class).filter_by(attrs);
    }

    public static List<Refund> all() throws Exceptions.PayloadError {
      return new ARMRequest<Refund>(Refund.class).all();
    }

    public static Refund get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Refund>(Refund.class).get(id);
    }
  }

  public static class Deposit extends ARMObject<Deposit> {
    public String getObject() {
      return "transaction";
    }

    public String[] getPoly() {
      return new String[] { "type", "deposit" };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Deposit>(Deposit.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Deposit>(Deposit.class).select(args);
    }

    public static List<Deposit> create(Deposit... args) throws Exceptions.PayloadError {
      return new ARMRequest<Deposit>(Deposit.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Deposit>(Deposit.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Deposit>(Deposit.class).filter_by(attrs);
    }

    public static List<Deposit> all() throws Exceptions.PayloadError {
      return new ARMRequest<Deposit>(Deposit.class).all();
    }

    public static Deposit get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Deposit>(Deposit.class).get(id);
    }
  }

  public static class Credit extends ARMObject<Credit> {
    public String getObject() {
      return "transaction";
    }

    public String[] getPoly() {
      return new String[] { "type", "credit" };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Credit>(Credit.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Credit>(Credit.class).select(args);
    }

    public static List<Credit> create(Credit... args) throws Exceptions.PayloadError {
      return new ARMRequest<Credit>(Credit.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Credit>(Credit.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Credit>(Credit.class).filter_by(attrs);
    }

    public static List<Credit> all() throws Exceptions.PayloadError {
      return new ARMRequest<Credit>(Credit.class).all();
    }

    public static Credit get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Credit>(Credit.class).get(id);
    }
  }

  public static class Ledger extends ARMObject<Ledger> {
    public String getObject() {
      return "transaction_ledger";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Ledger>(Ledger.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Ledger>(Ledger.class).select(args);
    }

    public static List<Ledger> create(Ledger... args) throws Exceptions.PayloadError {
      return new ARMRequest<Ledger>(Ledger.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Ledger>(Ledger.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Ledger>(Ledger.class).filter_by(attrs);
    }

    public static List<Ledger> all() throws Exceptions.PayloadError {
      return new ARMRequest<Ledger>(Ledger.class).all();
    }

    public static Ledger get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Ledger>(Ledger.class).get(id);
    }
  }

  public static class PaymentMethod extends ARMObject<PaymentMethod> {

    public String getObject() {
      return "payment_method";
    }

    public Map<String, String> fieldmap() {
      return new HashMap<String, String>() {
        {
          put("card_number", "card");
          put("track1", "card");
          put("track2", "card");
          put("ksn", "card");
          put("device_sn", "card");
          put("magne_print", "card");
          put("magne_print_status", "card");
          put("card_code", "card");
          put("expiry", "card");
          put("account_number", "bank_account");
          put("routing_number", "bank_account");
          put("account_type", "bank_account");
        }
      };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<PaymentMethod>(PaymentMethod.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<PaymentMethod>(PaymentMethod.class).select(args);
    }

    public static List<PaymentMethod> create(PaymentMethod... args) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentMethod>(PaymentMethod.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<PaymentMethod>(PaymentMethod.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<PaymentMethod>(PaymentMethod.class).filter_by(attrs);
    }

    public static List<PaymentMethod> all() throws Exceptions.PayloadError {
      return new ARMRequest<PaymentMethod>(PaymentMethod.class).all();
    }

    public static PaymentMethod get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentMethod>(PaymentMethod.class).get(id);
    }
  }

  public static class Card extends PaymentMethod {
    public String[] getPoly() {
      return new String[] { "type", "card" };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Card>(Card.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Card>(Card.class).select(args);
    }

    public static List<Card> create(Card... args) throws Exceptions.PayloadError {
      return new ARMRequest<Card>(Card.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Card>(Card.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Card>(Card.class).filter_by(attrs);
    }

    public static Card get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Card>(Card.class).get(id);
    }
  }

  public static class BankAccount extends PaymentMethod {
    public String[] getPoly() {
      return new String[] { "type", "bank_account" };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<BankAccount>(BankAccount.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<BankAccount>(BankAccount.class).select(args);
    }

    public static List<BankAccount> create(BankAccount... args) throws Exceptions.PayloadError {
      return new ARMRequest<BankAccount>(BankAccount.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<BankAccount>(BankAccount.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<BankAccount>(BankAccount.class).filter_by(attrs);
    }

    public static BankAccount get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<BankAccount>(BankAccount.class).get(id);
    }
  }

  public static class BillingSchedule extends ARMObject<BillingSchedule> {
    public String getObject() {
      return "billing_schedule";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<BillingSchedule>(BillingSchedule.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<BillingSchedule>(BillingSchedule.class).select(args);
    }

    public static List<BillingSchedule> create(BillingSchedule... args) throws Exceptions.PayloadError {
      return new ARMRequest<BillingSchedule>(BillingSchedule.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<BillingSchedule>(BillingSchedule.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<BillingSchedule>(BillingSchedule.class).filter_by(attrs);
    }

    public static List<BillingSchedule> all() throws Exceptions.PayloadError {
      return new ARMRequest<BillingSchedule>(BillingSchedule.class).all();
    }

    public static BillingSchedule get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<BillingSchedule>(BillingSchedule.class).get(id);
    }
  }

  public static class BillingCharge extends ARMObject<BillingCharge> {
    public String getObject() {
      return "billing_charge";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<BillingCharge>(BillingCharge.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<BillingCharge>(BillingCharge.class).select(args);
    }

    public static List<BillingCharge> create(BillingCharge... args) throws Exceptions.PayloadError {
      return new ARMRequest<BillingCharge>(BillingCharge.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<BillingCharge>(BillingCharge.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<BillingCharge>(BillingCharge.class).filter_by(attrs);
    }

    public static List<BillingCharge> all() throws Exceptions.PayloadError {
      return new ARMRequest<BillingCharge>(BillingCharge.class).all();
    }

    public static BillingCharge get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<BillingCharge>(BillingCharge.class).get(id);
    }
  }

  public static class Invoice extends ARMObject<Invoice> {
    public String getObject() {
      return "invoice";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Invoice>(Invoice.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Invoice>(Invoice.class).select(args);
    }

    public static List<Invoice> create(Invoice... args) throws Exceptions.PayloadError {
      return new ARMRequest<Invoice>(Invoice.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Invoice>(Invoice.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Invoice>(Invoice.class).filter_by(attrs);
    }

    public static List<Invoice> all() throws Exceptions.PayloadError {
      return new ARMRequest<Invoice>(Invoice.class).all();
    }

    public static Invoice get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Invoice>(Invoice.class).get(id);
    }
  }

  public static class LineItem extends ARMObject<LineItem> {
    public String getObject() {
      return "line_item";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<LineItem>(LineItem.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<LineItem>(LineItem.class).select(args);
    }

    public static List<LineItem> create(LineItem... args) throws Exceptions.PayloadError {
      return new ARMRequest<LineItem>(LineItem.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<LineItem>(LineItem.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<LineItem>(LineItem.class).filter_by(attrs);
    }

    public static List<LineItem> all() throws Exceptions.PayloadError {
      return new ARMRequest<LineItem>(LineItem.class).all();
    }

    public static LineItem get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<LineItem>(LineItem.class).get(id);
    }
  }

  public static class ChargeItem extends LineItem {
    public String[] getPoly() {
      return new String[] { "entry_type", "charge" };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<ChargeItem>(ChargeItem.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<ChargeItem>(ChargeItem.class).select(args);
    }

    public static List<ChargeItem> create(ChargeItem... args) throws Exceptions.PayloadError {
      return new ARMRequest<ChargeItem>(ChargeItem.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<ChargeItem>(ChargeItem.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<ChargeItem>(ChargeItem.class).filter_by(attrs);
    }

    public static ChargeItem get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<ChargeItem>(ChargeItem.class).get(id);
    }
  }

  public static class PaymentItem extends LineItem {
    public String[] getPoly() {
      return new String[] { "entry_type", "payment" };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<PaymentItem>(PaymentItem.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<PaymentItem>(PaymentItem.class).select(args);
    }

    public static List<PaymentItem> create(PaymentItem... args) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentItem>(PaymentItem.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<PaymentItem>(PaymentItem.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<PaymentItem>(PaymentItem.class).filter_by(attrs);
    }

    public static PaymentItem get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentItem>(PaymentItem.class).get(id);
    }
  }

  public static class Webhook extends ARMObject<Webhook> {

    public String getObject() {
      return "webhook";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Webhook>(Webhook.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Webhook>(Webhook.class).select(args);
    }

    public static List<Webhook> create(Webhook... args) throws Exceptions.PayloadError {
      return new ARMRequest<Webhook>(Webhook.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Webhook>(Webhook.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Webhook>(Webhook.class).filter_by(attrs);
    }

    public static List<Webhook> all() throws Exceptions.PayloadError {
      return new ARMRequest<Webhook>(Webhook.class).all();
    }

    public static Webhook get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Webhook>(Webhook.class).get(id);
    }
  }

  public static class WebhookLog extends ARMObject<WebhookLog> {

    public String getObject() {
      return "webhook_log";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<WebhookLog>(WebhookLog.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<WebhookLog>(WebhookLog.class).select(args);
    }

    public static List<WebhookLog> create(WebhookLog... args) throws Exceptions.PayloadError {
      return new ARMRequest<WebhookLog>(WebhookLog.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<WebhookLog>(WebhookLog.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<WebhookLog>(WebhookLog.class).filter_by(attrs);
    }

    public static List<WebhookLog> all() throws Exceptions.PayloadError {
      return new ARMRequest<WebhookLog>(WebhookLog.class).all();
    }

    public static WebhookLog get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<WebhookLog>(WebhookLog.class).get(id);
    }
  }

  public static class PaymentLink extends ARMObject<PaymentLink> {
    public String getObject() {
      return "payment_link";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<PaymentLink>(PaymentLink.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<PaymentLink>(PaymentLink.class).select(args);
    }

    public static List<PaymentLink> create(PaymentLink... args) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentLink>(PaymentLink.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<PaymentLink>(PaymentLink.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<PaymentLink>(PaymentLink.class).filter_by(attrs);
    }

    public static List<PaymentLink> all() throws Exceptions.PayloadError {
      return new ARMRequest<PaymentLink>(PaymentLink.class).all();
    }

    public static PaymentLink get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentLink>(PaymentLink.class).get(id);
    }
  }

  public static class PaymentActivation extends ARMObject<PaymentActivation> {
    public String getObject() {
      return "payment_activation";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<PaymentActivation>(PaymentActivation.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<PaymentActivation>(PaymentActivation.class).select(args);
    }

    public static List<PaymentActivation> create(PaymentActivation... args) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentActivation>(PaymentActivation.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<PaymentActivation>(PaymentActivation.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<PaymentActivation>(PaymentActivation.class).filter_by(attrs);
    }

    public static List<PaymentActivation> all() throws Exceptions.PayloadError {
      return new ARMRequest<PaymentActivation>(PaymentActivation.class).all();
    }

    public static PaymentActivation get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentActivation>(PaymentActivation.class).get(id);
    }
  }

  public static class AccessToken extends ARMObject<AccessToken> {
    public String getObject() {
      return "access_token";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<AccessToken>(AccessToken.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<AccessToken>(AccessToken.class).select(args);
    }

    public static List<AccessToken> create(AccessToken... args) throws Exceptions.PayloadError {
      return new ARMRequest<AccessToken>(AccessToken.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<AccessToken>(AccessToken.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<AccessToken>(AccessToken.class).filter_by(attrs);
    }

    public static List<AccessToken> all() throws Exceptions.PayloadError {
      return new ARMRequest<AccessToken>(AccessToken.class).all();
    }

    public static AccessToken get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<AccessToken>(AccessToken.class).get(id);
    }
  }

  public static class ClientToken extends ARMObject<ClientToken> {
    public String getObject() {
      return "access_token";
    }

    public String[] getPoly() {
      return new String[] { "type", "client" };
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<ClientToken>(ClientToken.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<ClientToken>(ClientToken.class).select(args);
    }

    public static List<ClientToken> create(ClientToken... args) throws Exceptions.PayloadError {
      return new ARMRequest<ClientToken>(ClientToken.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<ClientToken>(ClientToken.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<ClientToken>(ClientToken.class).filter_by(attrs);
    }

    public static List<ClientToken> all() throws Exceptions.PayloadError {
      return new ARMRequest<ClientToken>(ClientToken.class).all();
    }

    public static ClientToken get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<ClientToken>(ClientToken.class).get(id);
    }
  }

  public static class Profile extends ARMObject<Profile> {
    public String getObject() {
      return "profile";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Profile>(Profile.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Profile>(Profile.class).select(args);
    }

    public static List<Profile> create(Profile... args) throws Exceptions.PayloadError {
      return new ARMRequest<Profile>(Profile.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Profile>(Profile.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Profile>(Profile.class).filter_by(attrs);
    }

    public static List<Profile> all() throws Exceptions.PayloadError {
      return new ARMRequest<Profile>(Profile.class).all();
    }

    public static Profile get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Profile>(Profile.class).get(id);
    }
  }

  public static class BillingItem extends ARMObject<BillingItem> {
    public String getObject() {
      return "billing_item";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<BillingItem>(BillingItem.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<BillingItem>(BillingItem.class).select(args);
    }

    public static List<BillingItem> create(BillingItem... args) throws Exceptions.PayloadError {
      return new ARMRequest<BillingItem>(BillingItem.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<BillingItem>(BillingItem.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<BillingItem>(BillingItem.class).filter_by(attrs);
    }

    public static List<BillingItem> all() throws Exceptions.PayloadError {
      return new ARMRequest<BillingItem>(BillingItem.class).all();
    }

    public static BillingItem get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<BillingItem>(BillingItem.class).get(id);
    }
  }

  public static class Intent extends ARMObject<Intent> {

    public String getObject() {
      return "intent";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Intent>(Intent.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Intent>(Intent.class).select(args);
    }

    public static List<Intent> create(Intent... args) throws Exceptions.PayloadError {
      return new ARMRequest<Intent>(Intent.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Intent>(Intent.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Intent>(Intent.class).filter_by(attrs);
    }

    public static List<Intent> all() throws Exceptions.PayloadError {
      return new ARMRequest<Intent>(Intent.class).all();
    }

    public static Intent get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Intent>(Intent.class).get(id);
    }
  }

  public static class InvoiceItem extends ARMObject<InvoiceItem> {
    public String getObject() {
      return "invoice_item";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<InvoiceItem>(InvoiceItem.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<InvoiceItem>(InvoiceItem.class).select(args);
    }

    public static List<InvoiceItem> create(InvoiceItem... args) throws Exceptions.PayloadError {
      return new ARMRequest<InvoiceItem>(InvoiceItem.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<InvoiceItem>(InvoiceItem.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<InvoiceItem>(InvoiceItem.class).filter_by(attrs);
    }

    public static List<InvoiceItem> all() throws Exceptions.PayloadError {
      return new ARMRequest<InvoiceItem>(InvoiceItem.class).all();
    }

    public static InvoiceItem get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<InvoiceItem>(InvoiceItem.class).get(id);
    }
  }

  public static class PaymentAllocation extends ARMObject<PaymentAllocation> {
    public String getObject() {
      return "payment_allocation";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<PaymentAllocation>(PaymentAllocation.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<PaymentAllocation>(PaymentAllocation.class).select(args);
    }

    public static List<PaymentAllocation> create(PaymentAllocation... args) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentAllocation>(PaymentAllocation.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<PaymentAllocation>(PaymentAllocation.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<PaymentAllocation>(PaymentAllocation.class).filter_by(attrs);
    }

    public static List<PaymentAllocation> all() throws Exceptions.PayloadError {
      return new ARMRequest<PaymentAllocation>(PaymentAllocation.class).all();
    }

    public static PaymentAllocation get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<PaymentAllocation>(PaymentAllocation.class).get(id);
    }
  }

  public static class Entity extends ARMObject<Entity> {
    public String getObject() {
      return "entity";
    }

    public String getEndpoint() {
      return "/entities";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Entity>(Entity.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Entity>(Entity.class).select(args);
    }

    public static List<Entity> create(Entity... args) throws Exceptions.PayloadError {
      return new ARMRequest<Entity>(Entity.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Entity>(Entity.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Entity>(Entity.class).filter_by(attrs);
    }

    public static List<Entity> all() throws Exceptions.PayloadError {
      return new ARMRequest<Entity>(Entity.class).all();
    }

    public static Entity get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Entity>(Entity.class).get(id);
    }
  }

  public static class Stakeholder extends ARMObject<Stakeholder> {
    public String getObject() {
      return "stakeholder";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Stakeholder>(Stakeholder.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Stakeholder>(Stakeholder.class).select(args);
    }

    public static List<Stakeholder> create(Stakeholder... args) throws Exceptions.PayloadError {
      return new ARMRequest<Stakeholder>(Stakeholder.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Stakeholder>(Stakeholder.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Stakeholder>(Stakeholder.class).filter_by(attrs);
    }

    public static List<Stakeholder> all() throws Exceptions.PayloadError {
      return new ARMRequest<Stakeholder>(Stakeholder.class).all();
    }

    public static Stakeholder get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Stakeholder>(Stakeholder.class).get(id);
    }
  }

  public static class ProcessingAgreement extends ARMObject<ProcessingAgreement> {
    public String getObject() {
      return "processing_agreement";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<ProcessingAgreement>(ProcessingAgreement.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<ProcessingAgreement>(ProcessingAgreement.class).select(args);
    }

    public static List<ProcessingAgreement> create(ProcessingAgreement... args) throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingAgreement>(ProcessingAgreement.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<ProcessingAgreement>(ProcessingAgreement.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<ProcessingAgreement>(ProcessingAgreement.class).filter_by(attrs);
    }

    public static List<ProcessingAgreement> all() throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingAgreement>(ProcessingAgreement.class).all();
    }

    public static ProcessingAgreement get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingAgreement>(ProcessingAgreement.class).get(id);
    }
  }

  public static class ProcessingRule extends ARMObject<ProcessingRule> {
    public String getObject() {
      return "processing_rule";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<ProcessingRule>(ProcessingRule.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<ProcessingRule>(ProcessingRule.class).select(args);
    }

    public static List<ProcessingRule> create(ProcessingRule... args) throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingRule>(ProcessingRule.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<ProcessingRule>(ProcessingRule.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<ProcessingRule>(ProcessingRule.class).filter_by(attrs);
    }

    public static List<ProcessingRule> all() throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingRule>(ProcessingRule.class).all();
    }

    public static ProcessingRule get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingRule>(ProcessingRule.class).get(id);
    }
  }

  public static class Transfer extends ARMObject<Transfer> {
    public String getObject() {
      return "transfer";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Transfer>(Transfer.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Transfer>(Transfer.class).select(args);
    }

    public static List<Transfer> create(Transfer... args) throws Exceptions.PayloadError {
      return new ARMRequest<Transfer>(Transfer.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Transfer>(Transfer.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Transfer>(Transfer.class).filter_by(attrs);
    }

    public static List<Transfer> all() throws Exceptions.PayloadError {
      return new ARMRequest<Transfer>(Transfer.class).all();
    }

    public static Transfer get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Transfer>(Transfer.class).get(id);
    }
  }

  public static class TransactionOperation extends ARMObject<TransactionOperation> {
    public String getObject() {
      return "transaction_operation";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<TransactionOperation>(TransactionOperation.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<TransactionOperation>(TransactionOperation.class).select(args);
    }

    public static List<TransactionOperation> create(TransactionOperation... args) throws Exceptions.PayloadError {
      return new ARMRequest<TransactionOperation>(TransactionOperation.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<TransactionOperation>(TransactionOperation.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<TransactionOperation>(TransactionOperation.class).filter_by(attrs);
    }

    public static List<TransactionOperation> all() throws Exceptions.PayloadError {
      return new ARMRequest<TransactionOperation>(TransactionOperation.class).all();
    }

    public static TransactionOperation get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<TransactionOperation>(TransactionOperation.class).get(id);
    }
  }

  public static class CheckFront extends ARMObject<CheckFront> {
    public String getObject() {
      return "check_front";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<CheckFront>(CheckFront.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<CheckFront>(CheckFront.class).select(args);
    }

    public static List<CheckFront> create(CheckFront... args) throws Exceptions.PayloadError {
      return new ARMRequest<CheckFront>(CheckFront.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<CheckFront>(CheckFront.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<CheckFront>(CheckFront.class).filter_by(attrs);
    }

    public static List<CheckFront> all() throws Exceptions.PayloadError {
      return new ARMRequest<CheckFront>(CheckFront.class).all();
    }

    public static CheckFront get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<CheckFront>(CheckFront.class).get(id);
    }
  }

  public static class Account extends ARMObject<Account> {
    public String getObject() {
      return "account";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<Account>(Account.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<Account>(Account.class).select(args);
    }

    public static List<Account> create(Account... args) throws Exceptions.PayloadError {
      return new ARMRequest<Account>(Account.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<Account>(Account.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<Account>(Account.class).filter_by(attrs);
    }

    public static List<Account> all() throws Exceptions.PayloadError {
      return new ARMRequest<Account>(Account.class).all();
    }

    public static Account get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<Account>(Account.class).get(id);
    }
  }

  public static class ProcessingSettings extends ARMObject<ProcessingSettings> {
    public String getObject() {
      return "processing_settings";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<ProcessingSettings>(ProcessingSettings.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<ProcessingSettings>(ProcessingSettings.class).select(args);
    }

    public static List<ProcessingSettings> create(ProcessingSettings... args) throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingSettings>(ProcessingSettings.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<ProcessingSettings>(ProcessingSettings.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<ProcessingSettings>(ProcessingSettings.class).filter_by(attrs);
    }

    public static List<ProcessingSettings> all() throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingSettings>(ProcessingSettings.class).all();
    }

    public static ProcessingSettings get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<ProcessingSettings>(ProcessingSettings.class).get(id);
    }
  }

  public static class OAuthToken extends ARMObject<OAuthToken> {
    public String getObject() {
      return "oauth_token";
    }

    public String getEndpoint() {
      return "/oauth/token";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<OAuthToken>(OAuthToken.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<OAuthToken>(OAuthToken.class).select(args);
    }

    public static List<OAuthToken> create(OAuthToken... args) throws Exceptions.PayloadError {
      return new ARMRequest<OAuthToken>(OAuthToken.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<OAuthToken>(OAuthToken.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<OAuthToken>(OAuthToken.class).filter_by(attrs);
    }

    public static List<OAuthToken> all() throws Exceptions.PayloadError {
      return new ARMRequest<OAuthToken>(OAuthToken.class).all();
    }

    public static OAuthToken get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<OAuthToken>(OAuthToken.class).get(id);
    }
  }

  public static class User extends ARMObject<User> {
    public String getObject() {
      return "user";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<User>(User.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<User>(User.class).select(args);
    }

    public static List<User> create(User... args) throws Exceptions.PayloadError {
      return new ARMRequest<User>(User.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<User>(User.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<User>(User.class).filter_by(attrs);
    }

    public static List<User> all() throws Exceptions.PayloadError {
      return new ARMRequest<User>(User.class).all();
    }

    public static User get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<User>(User.class).get(id);
    }
  }

  public static class InvoiceAttachment extends ARMObject<InvoiceAttachment> {
    public String getObject() {
      return "invoice_attachment";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<InvoiceAttachment>(InvoiceAttachment.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<InvoiceAttachment>(InvoiceAttachment.class).select(args);
    }

    public static List<InvoiceAttachment> create(InvoiceAttachment... args) throws Exceptions.PayloadError {
      return new ARMRequest<InvoiceAttachment>(InvoiceAttachment.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<InvoiceAttachment>(InvoiceAttachment.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<InvoiceAttachment>(InvoiceAttachment.class).filter_by(attrs);
    }

    public static List<InvoiceAttachment> all() throws Exceptions.PayloadError {
      return new ARMRequest<InvoiceAttachment>(InvoiceAttachment.class).all();
    }

    public static InvoiceAttachment get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<InvoiceAttachment>(InvoiceAttachment.class).get(id);
    }
  }

  public static class CheckBack extends ARMObject<CheckBack> {
    public String getObject() {
      return "check_back";
    }

    public static ARMRequest select(String... args) {
      return new ARMRequest<CheckBack>(CheckBack.class).select(args);
    }

    public static ARMRequest select(Object... args) {
      return new ARMRequest<CheckBack>(CheckBack.class).select(args);
    }

    public static List<CheckBack> create(CheckBack... args) throws Exceptions.PayloadError {
      return new ARMRequest<CheckBack>(CheckBack.class).create(Arrays.asList(args));
    }

    public static ARMRequest filter_by(String attr, Object val) {
      return new ARMRequest<CheckBack>(CheckBack.class).filter_by(attr, val);
    }

    @SafeVarargs
    public static ARMRequest filter_by(Map.Entry<String, Object>... attrs) {
      return new ARMRequest<CheckBack>(CheckBack.class).filter_by(attrs);
    }

    public static List<CheckBack> all() throws Exceptions.PayloadError {
      return new ARMRequest<CheckBack>(CheckBack.class).all();
    }

    public static CheckBack get(String id) throws Exceptions.PayloadError {
      return new ARMRequest<CheckBack>(CheckBack.class).get(id);
    }
  }

}
