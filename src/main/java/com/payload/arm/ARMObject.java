package com.payload.arm;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.io.IOException;
import org.json.*;

public class ARMObject<T> {
	public String getObject(){ return ""; }
	public String getType(){ return null; }
	public String getEndpoint() { return "/"+getObject()+"s"; }
	public JSONObject obj;

	public ARMObject() {
		this.obj = new JSONObject();

		if ( getType() != null )
			this.set("type", getType());
	}

	public T setJson(JSONObject obj) {
		this.obj = obj;
		return (T)this;
	}

	public String getStr(String key) {
		try {
			return obj.getString(key);
		} catch( JSONException exc ) {
			return null;
		}
	}

	public int getInt(String key) {
		return obj.getInt(key);
	}

	public float getFloat(String key) {
		return obj.getFloat(key);
	}

	public T set(String key, Object value) {
		obj.put(key, value);
		return (T)this;
	}

	public T set(String key, ARMObject value) {
		obj.put(key, value.obj);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getCls() {
		Class supercls = (Class)getClass().getGenericSuperclass();

		return (Class<T>)((ParameterizedType)supercls.getGenericSuperclass())
			.getActualTypeArguments()[0];
	}

	public T create() throws IOException {
		Class<T> cls = getCls();
		return new ARMRequest<T>(cls).create((T)this);
	}

	public T update(Map.Entry<String,Object>... args) throws IOException {
		Class<T> cls = getCls();
		new ARMRequest<T>(cls).update((ARMObject)this, args);
		return (T)this;
	}

	public void delete() throws IOException {
		Class<T> cls = getCls();
		new ARMRequest<T>(cls).delete((ARMObject)this);
	}
}

