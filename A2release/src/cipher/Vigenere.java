package cipher;

import java.io.*;

public class Vigenere implements Cipher {

    public static int a_ASCII = 97;
    public static int z_ASCII = 122;
    private String key;

    public Vigenere(String key) {
        this.key = key;
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String plaintext = "";

        String currentLine = reader.readLine();
        while (currentLine != null) {
            plaintext += currentLine + "\n";
            currentLine = reader.readLine();
        }

        String ciphertext = encrypt(plaintext);
        out.write(ciphertext.getBytes("UTF-8"));
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) throws IOException {
        // Use InputStreamReader with UTF-8 encoding
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String ciphertext = "";

        String currentLine = reader.readLine();
        while (currentLine != null) {
            ciphertext += currentLine + "\n";
            currentLine = reader.readLine();
        }

        String plaintext = decrypt(ciphertext);
        out.write(plaintext.getBytes("UTF-8"));
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
            char encryptedChar = shifter(plainChar, keyChar);
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
            char decryptedChar = reverseShifter(cipherChar, keyChar);
            if (decryptedChar != ' '){
                charParsedCount++;
            }
            plaintext += decryptedChar;
        }
        return plaintext;
    }

    @Override
    public void save(OutputStream out) throws IOException {
        out.write("Vigenere\n".getBytes("UTF-8"));
        out.write(key.toUpperCase().getBytes("UTF-8"));
        out.write("\n".getBytes("UTF-8"));
    }

    // Shift method using ASCII values for encryption
    public char shifter(char inp, char shift) {
        if (inp == ' '){
            return ' ';
        }
        int shiftAmt = (int)shift - (a_ASCII - 1);
        int shifted = inp + shiftAmt;
        if (shifted > z_ASCII){
            shifted -= 26; //length of alphabet
        }
        return (char) shifted;
    }

    // Reverse shift method using ASCII values for decryption
    public char reverseShifter(char inp, char shift) {
        if (inp == ' '){
            return ' ';
        }
        int shiftAmt = (int)shift - (a_ASCII - 1);
        int shifted = inp - shiftAmt;
        if (shifted < a_ASCII){
            shifted += 26; //length of alphabet
        }
        return (char)shifted;
    }
}
