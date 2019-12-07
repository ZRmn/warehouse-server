package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.User;

import java.lang.reflect.Type;

public class UserSerializer<T extends User> implements JsonSerializer<T>
{
    @Override
    public JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext)
    {
        JsonObject object = new JsonObject();

        object.addProperty("type", t.getClass().getSimpleName());
        object.addProperty("id", t.getId());
        object.addProperty("login", t.getLogin());
        object.addProperty("password", t.getPassword());
        object.addProperty("fullName", t.getFullName());

        return object;
    }
}
