package utils;

import com.google.gson.*;
import models.*;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User>
{
    @Override
    public User deserialize(JsonElement jsonElement, Type t,
                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        JsonObject object = jsonElement.getAsJsonObject();

        String type = object.get("type").getAsString();
        Integer id = object.get("id").getAsInt();
        String login = object.get("login").getAsString();
        String password = object.get("password").getAsString();
        String fullName = object.get("fullName").getAsString();

        switch (type)
        {
            case "Admin":
            {
                return new Admin(id, login, password, fullName);
            }

            case "CheckMan":
            {
                return new CheckMan(id, login, password, fullName);
            }

            case "Customer":
            {
                return new Customer(id, login, password, fullName);
            }

            default:
            {
                return new StockMan(id, login, password, fullName);
            }
        }
    }
}
