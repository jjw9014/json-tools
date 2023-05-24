package com.evon.jsontools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapJson implements IJsonDiff {
    /**
     * 存储对象为com.alibaba.fastjson.JSONObject
     * 若com.alibaba.fastjson.JSONArray数组，则外部包装一层，转化为com.alibaba.fastjson.JSONObject
     */
    private JSONObject jsonObject;

    /**
     * 存储对象由层级对象转为平级对象
     * 如
     * 层级对象：{"user":{"name":"Evon", "age":20}}
     * 平级对象：{"user.name":"Evon", "user.age":20}
     */
    private Map<String, Object> flatMap;

    public MapJson() {
        initObjectIfNull();
    }

    public MapJson(Map<String, Object> flatMap) {
        this.flatMap = flatMap;

        initObjectIfNull();

        put(flatMap);
    }

    public MapJson(String json) {

        // 默认值
        initObjectIfNull();

        if (json == null || json.trim().isEmpty()) {
            return;
        }

        json = json.trim();
        if (!json.startsWith("{") && !json.startsWith("[")) {
            return;
        }

        if (json.startsWith("{")) {
            initJSONObject(json);
            return;
        }

        if (json.startsWith("[")) {
            initJSONArray(json);
            return;
        }
    }

    private void initJSONArray(String json) {
        if (json == null || !json.startsWith("[")) {
            return;
        }

        JSONArray jsonArray = JSON.parseArray(json);
        jsonObject.put("root", jsonArray);
        jsonObject2FlatMap(jsonObject, null);

        initObjectIfNull();
    }

    private void initJSONObject(String json) {
        if (json == null || !json.startsWith("{")) {
            return;
        }

        jsonObject = JSON.parseObject(json);
        jsonObject2FlatMap(jsonObject, null);

        initObjectIfNull();
    }

    private void jsonObject2FlatMap(JSONObject jsonObject, String prefixKey) {
        if (jsonObject == null) {
            return;
        }

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            String faltMapKey = getFlatMapKey(prefixKey, key);

            if (value instanceof JSONObject) {
                jsonObject2FlatMap((JSONObject) value, faltMapKey);
            } else if (value instanceof JSONArray) {
                jsonArray2FlatMap((JSONArray)value, faltMapKey);
            } else {
                flatMap.put(faltMapKey, value);
            }
        }
    }

    private void jsonArray2FlatMap(JSONArray jsonArray, String prefixKey) {
        if (jsonArray == null) {
            return;
        }

        for (int index = 0; index < jsonArray.size(); index++) {
            Object value = jsonArray.get(index);
            String faltMapKey = getFlatMapKey(prefixKey, index);

            if (value instanceof JSONObject) {
                jsonObject2FlatMap((JSONObject) value, faltMapKey);
            } else if (value instanceof JSONArray) {
                jsonArray2FlatMap((JSONArray)value, faltMapKey);
            } else {
                flatMap.put(faltMapKey, value);
            }
        }
    }

    private String getFlatMapKey(String prefixKey, String key) {
        if (prefixKey == null || prefixKey.trim().isEmpty()) {
            return key;
        }

        key = (key == null) ?"null":key;

        return String.format("%s.%s", prefixKey, key);
    }

    private String getFlatMapKey(String prefixKey, int index) {
        prefixKey = (prefixKey==null)?"null":prefixKey;

        return String.format("%s[%d]", prefixKey, index);
    }

    private void initObjectIfNull() {
        jsonObject = (jsonObject == null)?new JSONObject(true):jsonObject;
        flatMap = (flatMap==null)?new TreeMap<>():flatMap;
    }

    private Map<String, Object> subtract(Map<String, Object> aMap, Map<String, Object> bMap, String... ignoreFields) {
        if (aMap == null || aMap.isEmpty()) {
            return new HashMap<>();
        }

        if (bMap == null || bMap.isEmpty()) {
            return aMap;
        }

        Map<String, Object> map = new TreeMap<>();
        for (String key : aMap.keySet()) {
            if (isIgnoreField(key, ignoreFields)) {
                continue;
            }

            if (!bMap.containsKey(key)
                    || !bMap.get(key).equals(aMap.get(key))) {
                map.put(key, aMap.get(key));
            }
        }

        return map;
    }

    private boolean isIgnoreField(String key, String... ignoreFields) {
        if (ignoreFields == null || key == null) {
            return false;
        }

        for(String ignoreField : ignoreFields) {
            if (ignoreField == null || ignoreField.trim().length() == 0) {
                continue;
            }

            if (key.replaceFirst("\\[\\d+]","").contains(ignoreField)) {
                return true;
            }
        }

        return false;
    }

    private Map<String, Object> addNotCover(Map<String, Object> aMap, Map<String, Object> bMap) {
        bMap = (bMap == null) ? new HashMap<>() : bMap;

        if (aMap == null || aMap.isEmpty()) {
            return bMap ;
        }

        if (bMap == null || bMap.isEmpty()) {
            return aMap;
        }

        Map<String, Object> map = new TreeMap<>();
        map.putAll(aMap);
        for (String key : bMap.keySet()) {
            if (!aMap.containsKey(key)) {
                map.put(key, bMap.get(key));
            }
        }

        return map;
    }

    private Map<String, Object> addAndCover(Map<String, Object> aMap, Map<String, Object> bMap) {
        bMap = (bMap == null) ? new HashMap<>() : bMap;

        if (aMap == null || aMap.isEmpty()) {
            return bMap ;
        }

        if (bMap == null || bMap.isEmpty()) {
            return aMap;
        }

        Map<String, Object> map = new TreeMap<>();
        map.putAll(aMap);
        for (String key : bMap.keySet()) {
            map.put(key, bMap.get(key));
        }

        return map;
    }

    private Map<String, Object> compare(Map<String, Object> aMap, Map<String, Object> bMap, boolean returnTrue, String... ignoreFields) {
        bMap = (bMap == null) ? new HashMap<>() : bMap;

        if (aMap == null || aMap.isEmpty()) {
            return new HashMap<>() ;
        }

        if (bMap == null || bMap.isEmpty()) {
            Map<String, Object> map = new TreeMap<>();
            for (String key : aMap.keySet()) {
                map.put(key, false);
            }
            return map;
        }

        Map<String, Object> map = new TreeMap<>();
        for (String key : aMap.keySet()) {
            if (isIgnoreField(key, ignoreFields)) {
                if (returnTrue) {
                    map.put(key, true);
                }
                continue;
            }

            if (!bMap.containsKey(key) || !bMap.get(key).equals(aMap.get(key))) {
                map.put(key, false);
            } else {
                if (returnTrue) {
                    map.put(key, true);
                }
            }
        }

        return map;
    }

    private Map<String, Object> compareAndReturnValue(Map<String, Object> aMap, Map<String, Object> bMap, String... ignoreFields) {
        bMap = (bMap == null) ? new HashMap<>() : bMap;

        if (aMap == null || aMap.isEmpty()) {
            return new HashMap<>() ;
        }

        if (bMap == null || bMap.isEmpty()) {
            Map<String, Object> map = new TreeMap<>();
            for (String key : aMap.keySet()) {
                map.put(key, String.format("%s->%s", (aMap.get(key)==null)?" ":aMap.get(key).toString(), " "));
            }
            return map;
        }

        Map<String, Object> map = new TreeMap<>();
        for (String key : aMap.keySet()) {
            if (isIgnoreField(key, ignoreFields)) {
                continue;
            }

            if (!bMap.containsKey(key) || !bMap.get(key).equals(aMap.get(key))) {
                map.put(key, String.format("%s->%s", (aMap.get(key)==null)?" ":aMap.get(key).toString(), (bMap.get(key)==null)?" ":bMap.get(key).toString()));
            }
        }

        return map;
    }

    private void put(Map<String, Object> flatMap) {
        if (flatMap == null || flatMap.isEmpty()) {
            return;
        }

        for (String key : flatMap.keySet()) {
            put(key, flatMap.get(key), jsonObject);
        }

    }

    private void put(String key, Object value, JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        if (!key.contains(".")) {
            jsonObject.put(key, value);
            return;
        }

        int cursor = 0;
        int cursorDot = key.indexOf(".", cursor);
        int lastDot = key.lastIndexOf(".");
        JSONObject tmpJsonObject = jsonObject;
        boolean isArrayJSONObject = !(key.charAt(key.length()-1)==']');
        while (cursor <= cursorDot || cursorDot == -1) {
            if (tmpJsonObject == null) {
                break;
            }

            if (cursorDot == -1 && !key.substring(lastDot+1).contains("[")) {
                tmpJsonObject.put(key.substring(lastDot+1), value);
                break;
            }

            String keyprop = key.substring(cursor, (cursorDot==-1)?key.length():cursorDot);
            if (!keyprop.contains("[")) {
                if (!tmpJsonObject.containsKey(keyprop)) {
                    JSONObject keypropJsonObject = new JSONObject(true);
                    tmpJsonObject.put(keyprop, keypropJsonObject);

                    tmpJsonObject = keypropJsonObject;
                } else {
                    tmpJsonObject = tmpJsonObject.getJSONObject(keyprop);
                }
            } else {
                int arrayIndex = Integer.parseInt(keyprop.substring(keyprop.indexOf("[")+1, keyprop.indexOf("]")));
                keyprop = keyprop.substring(0, keyprop.indexOf("["));
                if (!tmpJsonObject.containsKey(keyprop)) {
                    JSONArray jsonArray = new JSONArray();
                    tmpJsonObject.put(keyprop, jsonArray);
                    tmpJsonObject = put(arrayIndex, value, jsonArray, isArrayJSONObject);
                } else {
                    JSONArray jsonArray = tmpJsonObject.getJSONArray(keyprop);
                    tmpJsonObject = put(arrayIndex, value, jsonArray, isArrayJSONObject);
                }
            }

            cursor = cursorDot + 1;
            cursorDot = key.indexOf(".", cursor);
        }
    }

    private JSONObject put(int arrayIndex, Object value, JSONArray jsonArray, boolean isArrayJSONObject) {
        if (jsonArray == null) {
            return null;
        }

        for (int idx=jsonArray.size(); idx <= arrayIndex; idx++) {
            if (isArrayJSONObject) {
                JSONObject keypropJsonObject = new JSONObject(true);
                jsonArray.add(keypropJsonObject);
            } else {
                Object v = (idx<arrayIndex) ? null : value;
                jsonArray.add(v);
            }
        }

        JSONObject jsonObject = null;
        if (isArrayJSONObject) {
            jsonObject = jsonArray.getJSONObject(arrayIndex);
        }

        return jsonObject;
    }

    @Override
    public String subtract(String aJson, String bJson, String... ignoreFields) {
        MapJson aMapJson = new MapJson(aJson);
        MapJson bMapJson = new MapJson(bJson);

        Map<String, Object> flatMap = subtract(aMapJson.flatMap, bMapJson.flatMap, ignoreFields);
        MapJson mapJson = new MapJson(flatMap);

        return mapJson.toString();
    }

    @Override
    public String addNotCover(String aJson, String bJson) {
        MapJson aMapJson = new MapJson(aJson);
        MapJson bMapJson = new MapJson(bJson);

        Map<String, Object> flatMap = addNotCover(aMapJson.flatMap, bMapJson.flatMap);
        MapJson mapJson = new MapJson(flatMap);

        return mapJson.toString();
    }

    @Override
    public String addAndCover(String aJson, String bJson) {
        MapJson aMapJson = new MapJson(aJson);
        MapJson bMapJson = new MapJson(bJson);

        Map<String, Object> flatMap = addAndCover(aMapJson.flatMap, bMapJson.flatMap);
        MapJson mapJson = new MapJson(flatMap);

        return mapJson.toString();
    }

    @Override
    public String compare(String aJson, String bJson, String... ignoreFields) {
        MapJson aMapJson = new MapJson(aJson);
        MapJson bMapJson = new MapJson(bJson);

        Map<String, Object> flatMap = compare(aMapJson.flatMap, bMapJson.flatMap, true, ignoreFields);
        MapJson mapJson = new MapJson(flatMap);

        return mapJson.toString();
    }

    @Override
    public String compareReturnFalse(String aJson, String bJson, String... ignoreFields) {
        MapJson aMapJson = new MapJson(aJson);
        MapJson bMapJson = new MapJson(bJson);

        Map<String, Object> flatMap = compare(aMapJson.flatMap, bMapJson.flatMap, false, ignoreFields);
        MapJson mapJson = new MapJson(flatMap);

        return mapJson.toString();
    }

    @Override
    public String compareReturnValue(String aJson, String bJson, String... ignoreFields) {
        MapJson aMapJson = new MapJson(aJson);
        MapJson bMapJson = new MapJson(bJson);

        Map<String, Object> flatMap = compareAndReturnValue(aMapJson.flatMap, bMapJson.flatMap, ignoreFields);
        MapJson mapJson = new MapJson(flatMap);

        return mapJson.toString();
    }

    public Object getValue(String flatKey) {
        return flatMap.get(flatKey);
    }

    @Override
    public String toString() {
        String json = JSONObject.toJSONString(this.jsonObject)
                .replaceAll("\\[null,", "[").replaceAll(",null,", ",")
                .replaceAll("\\[\\{\\},", "[").replaceAll(",\\{\\},", ",");
        if (json.startsWith("{\"root\":")) {
            json = json.substring(8, json.length()-1);
        }

        return json;
    }
}
