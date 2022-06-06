/* Class taken from Geeks For Geeks website:
https://www.geeksforgeeks.org/sha-1-hash-in-java/?ref=lbp
https://www.geeksforgeeks.org/sha-256-hash-in-java/?ref=lbp
 */
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptMe {
    public static String encryptThisString(String input)
    {
        //Salting - Add some padding
        input = "salt" + input + "bae";

        try {
            // getInstance() method is called with algorithm SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 64 bit
            while (hashtext.length() < 64) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
