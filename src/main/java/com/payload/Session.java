package com.payload;

import java.util.List;
import com.payload.pl;
import com.payload.arm.ARMObject;
import com.payload.arm.ARMRequest;

public class Session {

  private volatile String api_key;
  private volatile String api_url;

  public Session(String api_key) {
    this.api_key = api_key;
  }

  public Session(String api_key, String api_url) {
    this.api_key = api_key;
    this.api_url = api_url;
  }

  public String getApiKey() { return this.api_key != null ? this.api_key : pl.api_key; }
  public String getApiUrl() { return this.api_url != null ? this.api_url : pl.api_url; }
  public String getApiVersion() { return pl.api_version; }

  public <T> ARMRequest<T> select(Class<T> cls) throws Exceptions.PayloadError {
      return new ARMRequest<T>(cls, this);
  }

  public pl.AccessToken create(pl.AccessToken obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.ClientToken create(pl.ClientToken obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Customer create(pl.Customer obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.ProcessingAccount create(pl.ProcessingAccount obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Org create(pl.Org obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Transaction create(pl.Transaction obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Payment create(pl.Payment obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Refund create(pl.Refund obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Credit create(pl.Credit obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Deposit create(pl.Deposit obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Ledger create(pl.Ledger obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.PaymentMethod create(pl.PaymentMethod obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Card create(pl.Card obj) throws Exceptions.PayloadError {
     return (pl.Card)obj.create(this);
  }

  public pl.BankAccount create(pl.BankAccount obj) throws Exceptions.PayloadError {
     return (pl.BankAccount)obj.create(this);
  }

  public pl.BillingSchedule create(pl.BillingSchedule obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.BillingCharge create(pl.BillingCharge obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Invoice create(pl.Invoice obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.LineItem create(pl.LineItem obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.ChargeItem create(pl.ChargeItem obj) throws Exceptions.PayloadError {
     return (pl.ChargeItem)obj.create(this);
  }

  public pl.PaymentItem create(pl.PaymentItem obj) throws Exceptions.PayloadError {
     return (pl.PaymentItem)obj.create(this);
  }

  public pl.Webhook create(pl.Webhook obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.PaymentLink create(pl.PaymentLink obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.PaymentActivation create(pl.PaymentActivation obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Intent create(pl.Intent obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Entity create(pl.Entity obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Stakeholder create(pl.Stakeholder obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Profile create(pl.Profile obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.BillingItem create(pl.BillingItem obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.InvoiceItem create(pl.InvoiceItem obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.PaymentAllocation create(pl.PaymentAllocation obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.ProcessingAgreement create(pl.ProcessingAgreement obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.ProcessingRule create(pl.ProcessingRule obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Transfer create(pl.Transfer obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.TransactionOperation create(pl.TransactionOperation obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.WebhookLog create(pl.WebhookLog obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.CheckFront create(pl.CheckFront obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.CheckBack create(pl.CheckBack obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.Account create(pl.Account obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.ProcessingSettings create(pl.ProcessingSettings obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public pl.InvoiceAllocation create(pl.InvoiceAllocation obj) throws Exceptions.PayloadError {
     return obj.create(this);
  }

  public void delete(ARMObject obj) throws Exceptions.PayloadError {
     obj.session = this;
     obj.delete();
  }

}
