package cipher;

import java.io.*;

public class Vigenere extends AbstractCipher implements Cipher {


    private String key;

    public Vigenere(String key) {
        this.key = key;
    }



    @Override
    public String encrypt(String plaintext) {
        plaintext = plaintext.toLowerCase();
        System.out.println("key");
        System.out.println(key);
        if (key.equals("")){
            return plaintext;
        }

        String ciphertext = "";
        int charParsedCount = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(charParsedCount % key.length());
            System.out.println(charParsedCount % key.length());
            char encryptedChar = shifter(plainChar, (int)keyChar - (a_ASCII - 1));
            if (encryptedChar != ' '){
                charParsedCount++;
            }
            ciphertext += encryptedChar;
        }
        return ciphertext;
    }

    @Override
    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.toLowerCase();
        if (key.equals("")){
            return ciphertext;
        }
        String plaintext = "";
        int charParsedCount = 0;
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(charParsedCount % key.length());
            char decryptedChar = reverseShifter(cipherChar, (int)keyChar - (a_ASCII - 1));
            if (decryptedChar != ' '){
                charParsedCount++;
            }
            plaintext += decryptedChar;
        }
        return plaintext;
    }

    @Override
    public void save(OutputStream out) throws IOException {
        out.write("VIGENERE\n".getBytes("UTF-8"));
        out.write(key.toUpperCase().getBytes("UTF-8"));
        out.write("\n".getBytes("UTF-8"));
    }

    // Shift method using ASCII values for encryption

}
