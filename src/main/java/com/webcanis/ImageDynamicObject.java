package com.webcanis;

import java.util.HashMap;
import java.util.Map;

public class ImageDynamicObject {
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

//Can I add this ImageDynamicObject to a list/array to save it?
//Deduplicating the images by name?
//Saving the image path into the object for later duplication
//Sorting the list on date-image-taken
//Adding the date-image-taken to file name (to sort the images in file explorer automatically)
//Copying image into output folder on month/day scheme

//public static void main(String[] args) {
//    DynamicObject obj = new DynamicObject();
//    obj.setField("name", "John Doe");
//    obj.setField("age", 30);
//    System.out.println(obj.getField("name")); // Output: John Doe
//}

