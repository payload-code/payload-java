package com.payload.arm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.Optional;
import java.util.Base64;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.InvocationTargetException;
import org.json.*;

import com.payload.arm.ARMObject;
import com.payload.Exceptions;
import com.payload.pl;

public class ARMRequest<T> {
	public Class<T> cls;
	private ArrayList<String> _attrs = new ArrayList<String>();
	private HashMap<String, Object> _filters = new HashMap<String, Object>();

	public ARMRequest(Class<T> cls) {
		this.cls = cls;
	}

	public Object _request( String method, String id, String json) throws Exceptions.PayloadError {
		String endpoint = "";

		try {
			ARMObject inst = ((ARMObject)this.cls.getConstructor().newInstance());
			endpoint = inst.getEndpoint();
		} catch ( Exception exc ) {
			return null;
		}

		if (id != null && !id.isEmpty())
			endpoint += "/" + id;

		String query = "";
		for ( int i = 0; i <_attrs.size(); i++ ) {
			if ( query.length() > 0 )
				query += "&";

			try {
				query += String.format(
					"fields["+Integer.toString(i)+"]=%s",
					URLEncoder.encode(_attrs.get(i), "UTF8") );
			} catch( UnsupportedEncodingException exc ) {}
		}

		Set set = _filters.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			if ( query.length() > 0 )
				query += "&";

			try {
				query += String.format("%s=%s",
					mentry.getKey(),
					URLEncoder.encode( String.valueOf(mentry.getValue()), "UTF8")
				);
			} catch( UnsupportedEncodingException exc ) {}
		}

		if ( query.length() > 0 )
			endpoint += "?" + query;

		try {
			URL url = new URL(pl.api_url + endpoint);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);

			String encoded = Base64.getEncoder()
				.encodeToString((pl.api_key+":")
				.getBytes(StandardCharsets.UTF_8));

			con.setRequestProperty("Authorization", "Basic "+encoded);

			if (json != null && !json.isEmpty()) {
				con.setRequestProperty("Content-Type", "application/json");
				con.setDoOutput(true);
				DataOutputStream out = new DataOutputStream(con.getOutputStream());
				out.writeBytes(json);
				out.flush();
				out.close();
			}

			try {
				int status = con.getResponseCode();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder content = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();

				JSONObject obj = new JSONObject(content.toString());

				try {
					if ( obj.getString("object").equals("list") ) {
						List<T> result = new ArrayList<T>();
						JSONArray lst = obj.getJSONArray("values");

						for (int i = 0 ; i < lst.length(); i++) {
							obj = lst.getJSONObject(i);
							result.add((T)((ARMObject)this.cls.newInstance()).setJson(obj));
						}

						return result;
					} else
						return (T)((ARMObject)this.cls.newInstance()).setJson(obj);
				} catch ( InstantiationException | IllegalAccessException exc ) {
					System.out.println(exc);
					return null;
				}

			} catch (IOException exc) {
				try {
					System.out.println(exc);
					int status = con.getResponseCode();
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
					String inputLine;
					StringBuilder content = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						content.append(inputLine);
					}
					in.close();
					System.out.println(content.toString());

					JSONObject err = new JSONObject(content.toString());

					if ( Exceptions.excmap.containsKey(err.getString("error_type")) )
						throw (Exceptions.PayloadError)Exceptions.excmap.get(err.getString("error_type"))
							.getDeclaredConstructor(JSONObject.class)
							.newInstance(err);
				} catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | IOException inst_exc ) {}

				throw new Exceptions.PayloadError(null);
			}

		} catch ( IOException exc ) {
			throw new Exceptions.PayloadError(null);
		}
	}

	public ARMRequest select(String... args) {
		for ( int i=0; i<args.length; i++)
			this._attrs.add(args[i]);
		return this;
	}

	public ARMRequest filter_by(String attr, Object val) {
		_filters.put(attr, val);
		return this;
	}

	public List<T> all() throws Exceptions.PayloadError {
		return (List<T>)this._request("GET", null, null);
	}

	public T get(String id) throws Exceptions.PayloadError {
		if ( id == null )
			throw new NullPointerException();
		return (T)this._request("GET", id, null);
	}

	public T create(T obj) throws Exceptions.PayloadError {
		ARMObject new_obj = (ARMObject)this._request("POST", null, ((ARMObject)obj).obj.toString());
		((ARMObject)obj).obj = new_obj.obj;
		return obj;
	}

	public List<T> create(List<T> objs) throws Exceptions.PayloadError {
		JSONObject req = new JSONObject();
		JSONArray arr = new JSONArray();

		req.put("object", "list");
		req.put("values", arr);

		for ( T obj : objs )
			arr.put(((ARMObject)obj).obj);

		List<ARMObject> resp = (List<ARMObject>)this._request("POST", null, req.toString());

		for ( int i = 0; i < resp.size(); i++ )
			((ARMObject)objs.get(i)).obj = resp.get(i).obj;

		return objs;
	}

	public T update(ARMObject obj, Map.Entry<String,Object>[] upds) throws Exceptions.PayloadError {
		if ( obj.getStr("id") == null )
			throw new NullPointerException();

		JSONObject req = new JSONObject();
		for ( Map.Entry<String,Object> upd : upds )
			req.put(upd.getKey(), String.valueOf(upd.getValue()));

		ARMObject new_obj = (ARMObject)this._request("PUT", obj.getStr("id"), req.toString());
		((ARMObject)obj).obj = new_obj.obj;
		return (T)obj;
	}

	public void delete(ARMObject obj) throws Exceptions.PayloadError {
		if ( obj.getStr("id") == null )
			throw new NullPointerException();
		ARMObject new_obj = (ARMObject)this._request("DELETE", obj.getStr("id"), null);
	}
}
