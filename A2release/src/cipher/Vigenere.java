package cipher;

import java.io.*;

public class Vigenere implements Cipher{

    private String key;

    public Vigenere(String key){
        this.key = key;
    }

    public void encrypt(InputStream in, OutputStream out) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String plaintext = "";

        String currentLine = reader.readLine();
        while (currentLine != null){
            plaintext += currentLine + "\n";
            currentLine = reader.readLine();
        }

        String ciphertext = encrypt(plaintext);
        out.write(ciphertext.getBytes());
    }

    public void decrypt(InputStream in, OutputStream out) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String plaintext = "";

        String currentLine = reader.readLine();
        while (currentLine != null){
            plaintext += currentLine + "\n";
            currentLine = reader.readLine();
        }

        String ciphertext = decrypt(plaintext);
        out.write(ciphertext.getBytes());
    }


    @Override
    public String encrypt(String plaintext) {
        plaintext = plaintext.toLowerCase();
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char encryptedChar = shifter(plainChar, keyChar);
            ciphertext += encryptedChar;

        }
        return ciphertext;
    }

    @Override
    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.toLowerCase();
        String plaintext = "";

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char decryptedChar = reverseShifter(cipherChar, keyChar);
            plaintext += decryptedChar;

        }
        return plaintext;
    }

    @Override
    public void save(OutputStream out) throws IOException {

        out.write("Vigenere\n".getBytes());
        out.write(key.toUpperCase().getBytes());
        out.write("\n".getBytes());

    }

    //using ASCII values detailed in table from CMU website
    public char shifter(char inp, char shift) {
        int shiftAmt = (((int) inp - (int) 'a') + ((int) shift - (int) 'a')) % 26;
        return (char) ((int) 'a' + shiftAmt);
    }

    public char reverseShifter(char inp, char shift) {
        int shiftAmt = (((int) inp - (int) 'a') - (((int) shift - (int) 'a')) + 26) % 26;
        return (char) ((int) 'a' + shiftAmt);
    }
}
