package com.webcanis;

import java.util.HashMap;
import java.util.Map;

public class ImageMetaData {
    private final Map<String, Object> fields = new HashMap<>();

    public void setField(String key, Object value) {
        fields.put(key, value);
    }

    public Object getField(String key) {
        return fields.get(key);
    }

    public boolean hasField(String key) {
        return fields.containsKey(key);
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}

//public static void main(String[] args) {
//    ImageDynamicObject obj = new ImageDynamicObject();
//    obj.setField("name", "John Doe");
//    obj.setField("age", 30);
//    System.out.println(obj.getField("name")); // Output: John Doe
//}

