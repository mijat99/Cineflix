package beans;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.*;

public abstract class SHA1 {
    public static String StringToSha1(String password){
        String sha1="";
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1=byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException nsae){
            return "false";
        }
        catch(UnsupportedEncodingException uee)
        {
            return "false";
        }
        catch(Exception e){
            return "false";
        }
        return sha1;
    }
    private static String byteToHex(final byte[] hash){
         Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
