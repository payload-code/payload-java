package co.payload;

import org.json.*;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static Map<String, Object> flattenJSON(JSONObject obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        return flattenJSON(obj, map, null);
    }

    static Map<String, Object> flattenJSON(JSONObject obj, Map<String, Object> map, String prefix) {
        for (String keyStr: obj.keySet()) {
            Object keyvalue = obj.get(keyStr);

            if ( prefix != null )
                keyStr = prefix + "[" + keyStr + "]";

            mergeJSON(keyvalue, map, keyStr);
        }

        return map;
    }

    static void mergeJSON(Object keyvalue, Map<String, Object> map, String keyStr) {

        if (keyvalue instanceof JSONArray) {
            for ( int i = 0; i < ((JSONArray)keyvalue).length(); i++) {
                mergeJSON(((JSONArray) keyvalue).getJSONObject(i), map, keyStr + "[" + i + "]");
            }
        } else if (keyvalue instanceof JSONObject) {
            flattenJSON((JSONObject) keyvalue, map, keyStr);
        } else {
            map.put(keyStr, keyvalue);
        }

    }

    public static int getVersion() {
        String version = System.getProperty("java.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);
    }
}
