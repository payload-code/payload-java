package com.payload.arm;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.HashMap;
import net.jodah.typetools.TypeResolver;
import org.json.*;
import com.payload.Exceptions;
import com.payload.Session;

public class ARMObject<T> {
	public String getObject(){ return ""; }
	public String[] getPoly(){ return null; }
	public Map<String,String> fieldmap(){ return null; }
	public String getEndpoint() { return "/"+getObject()+"s"; }
	public JSONObject obj;
	public Session session;

	public ARMObject() {
		this.obj = new JSONObject();

		if ( getPoly() != null )
			this.set(getPoly()[0], getPoly()[1]);
	}

	public T setJson(JSONObject obj) {
		this.obj = obj;
		return (T)this;
	}

	public String getStr(String key) {
		return this.getStr(key, null);
	}

	public String getStr(String key, String default_val) {
		try {
			return mappedObj(key).getString(key);
		} catch( JSONException exc ) {
			return default_val;
		}
	}

	private JSONObject mappedObj(String key) {
		if ( fieldmap() != null && fieldmap().containsKey(key) ) {
			String val = fieldmap().get(key);
			if ( !obj.has(val) )
				obj.put(val, new JSONObject());
			return obj.getJSONObject(val);
		} else
			return obj;
	}

	public int getInt(String key) {
		return mappedObj(key).getInt(key);
	}

	public float getFloat(String key) {
		return (float)mappedObj(key).getDouble(key);
	}

	public JSONObject getJObj(String key) {
		return mappedObj(key).getJSONObject(key);
	}

	public T set(String key, Object value) {
		mappedObj(key).put(key, value);
		return (T)this;
	}

	public T set(String key, ARMObject value) {
		obj.put(key, value.obj);
		return (T)this;
	}

	public T set(String key, ARMObject[] values) {
		obj.put(key, new JSONArray());
		int i;
		for (i = 0; i < values.length; i++) {
			obj.getJSONArray(key).put(values[i].obj);
		}
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getCls() {
		return (Class<T>)TypeResolver.resolveRawArgument(ARMObject.class, getClass());
	}

	public T create() throws Exceptions.PayloadError {
		Class<T> cls = getCls();
		return new ARMRequest<T>(cls, this.session).create((T)this);
	}

	public T create(Session session) throws Exceptions.PayloadError {
		Class<T> cls = getCls();
		return new ARMRequest<T>(cls, session).create((T)this);
	}

	public T update(Map.Entry<String,Object>... args) throws Exceptions.PayloadError {
		Class<T> cls = getCls();
		new ARMRequest<T>(cls, this.session).update((ARMObject)this, args);
		return (T)this;
	}

	public void delete() throws Exceptions.PayloadError {
		Class<T> cls = getCls();
		new ARMRequest<T>(cls, this.session).delete((ARMObject)this);
	}
}

