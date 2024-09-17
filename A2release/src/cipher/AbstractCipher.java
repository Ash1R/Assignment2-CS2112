package cipher;

import java.io.*;

/** A place to put some inherited code? */
public abstract class AbstractCipher implements Cipher {
    public static int a_ASCII = 97;
    public static int z_ASCII = 122;

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

    public char shifter(char inp, int shiftAmt) {
        if (inp == ' '){
            return ' ';
        }
        int shifted = inp + shiftAmt;
        if (shifted > z_ASCII){
            shifted -= 26; //length of alphabet
        }
        return (char) shifted;
    }

    // Reverse shift method using ASCII values for decryption
    public char reverseShifter(char inp, int shiftAmt) {
        if (inp == ' '){
            return ' ';
        }
        int shifted = inp - shiftAmt;
        if (shifted < a_ASCII){
            shifted += 26; //length of alphabet
        }
        return (char)shifted;
    }

}
