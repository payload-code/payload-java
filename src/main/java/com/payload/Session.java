package com.payload;

import java.util.List;
import java.util.Arrays;
import com.payload.pl;
import com.payload.arm.ARMObject;
import com.payload.arm.ARMRequest;

public class Session {

  private volatile String api_key;
  private volatile String api_url;
  private volatile String api_version;

  public Session(String api_key) {
    this.api_key = api_key;
  }

  public Session(String api_key, String api_version) {
    this.api_key = api_key;
    this.api_version = api_version;
  }

  public Session apiUrl(String api_url) {
    this.api_url = api_url;
    return this;
  }

  public Session apiVersion(String api_version) {
    this.api_version = api_version;
    return this;
  }

  public String getApiKey() { return this.api_key != null ? this.api_key : pl.api_key; }
  public String getApiUrl() { return this.api_url != null ? this.api_url : pl.api_url; }
  public String getApiVersion() { return this.api_version != null ? this.api_version : pl.api_version; }

  public <T> ARMRequest<T> select(Class<T> cls) throws Exceptions.PayloadError {
      return new ARMRequest<T>(cls, this);
  }

  public <T> ARMRequest<T> select(Class<T> cls, String... args) {
    return new ARMRequest<T>(cls).select(args);
  }

  @SuppressWarnings("unchecked")
  public <T extends ARMObject> T create(T obj) throws Exceptions.PayloadError {
     return (T) obj.create(this);
  }

  @SafeVarargs
  @SuppressWarnings("unchecked")
  public final <T extends ARMObject> List<T> create(T... args) throws Exceptions.PayloadError {
     if (args.length == 0)
        throw new IllegalArgumentException("create requires at least one argument");
     Class<T> cls = (Class<T>) args[0].getClass();
     return new ARMRequest<T>(cls, this).create(Arrays.asList(args));
  }

  public void delete(ARMObject obj) throws Exceptions.PayloadError {
     obj.session = this;
     obj.delete();
  }

}
