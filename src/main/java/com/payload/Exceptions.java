package com.payload;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.io.IOException;
import java.util.HashMap;
import org.json.*;

public class Exceptions {
	public static class PayloadError extends Exception {
		public static int http_code = 0;
		public JSONObject data = null;

		public PayloadError(JSONObject data) {
			super();
			this.data = data;
		}

		public PayloadError(String msg) {
			super(msg);
		}

		public PayloadError(String msg, Exception e) {
			super(msg, e);
		}
	}

	public static class UnknownResponse extends PayloadError {
		public UnknownResponse(JSONObject details) {
			super(details);
		}
	}

	public static class BadRequest extends PayloadError {
		public static int http_code = 400;
		public BadRequest(JSONObject details) {
			super(details);
		}
	}

	public static class InvalidAttributes extends BadRequest {
		public InvalidAttributes(JSONObject details) {
			super(details);
		}
	}

	public static class Unauthorized extends PayloadError {
		public static int http_code = 401;
		public Unauthorized(JSONObject details) {
			super(details);
		}
	}

	public static class NotPermitted extends PayloadError {
		public static int http_code = 403;
		public NotPermitted(JSONObject details) {
			super(details);
		}
	}

	public static class NotFound extends PayloadError {
		public static int http_code = 404;
		public NotFound(JSONObject details) {
			super(details);
		}
	}

	public static class TooManyRequests extends PayloadError {
		public static int http_code = 429;
		public TooManyRequests(JSONObject details) {
			super(details);
		}
	}

	public static class InternalServerError extends PayloadError {
		public static int http_code = 500;
		public InternalServerError(JSONObject details) {
			super(details);
		}
	}

	public static class ServiceUnavailable extends PayloadError {
		public static int http_code = 503;
		public ServiceUnavailable(JSONObject details) {
			super(details);
		}
	}

	public static Map<String, Class> excmap = new HashMap<String, Class>() {{
		put("BadRequest", BadRequest.class);
		put("InvalidAttributes", InvalidAttributes.class);
		put("Unauthorized", Unauthorized.class);
		put("NotPermitted", NotPermitted.class);
		put("NotFound", NotFound.class);
		put("TooManyRequests", TooManyRequests.class);
		put("InternalServerError", InternalServerError.class);
		put("ServiceUnavailable", ServiceUnavailable.class);
	}};
}
