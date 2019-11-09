package utils;

import com.google.gson.Gson;

public class JsonParser
{
    public static String jsonFromObject(Object obj)
    {
        Gson gson = new Gson();
        return gson.toJson(obj, obj.getClass());
    }

    public static <T> T objectFromJson(String json, Class<T> cls)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }
}
