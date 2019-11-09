package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
    public static String encode(String string)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(StandardCharsets.UTF_8.encode(string));

            return String.format("%032x", new BigInteger(1, md.digest()));
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
