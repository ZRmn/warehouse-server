package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;

public class JsonParser
{
    private static Gson gson;

    static
    {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(User.class, new UserDeserializer())
                .registerTypeAdapter(Admin.class, new UserSerializer<Admin>())
                .registerTypeAdapter(CheckMan.class, new UserSerializer<CheckMan>())
                .registerTypeAdapter(Customer.class, new UserSerializer<Customer>())
                .registerTypeAdapter(StockMan.class, new UserSerializer<StockMan>());

        gson = builder.create();
    }

    public static String jsonFromObject(Object obj)
    {
        return gson.toJson(obj, obj.getClass());
    }

    public static <T> T objectFromJson(String json, Class<T> cls)
    {
        return gson.fromJson(json, cls);
    }
}
