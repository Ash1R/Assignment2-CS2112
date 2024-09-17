package cipher;

import java.io.*;
import java.util.Arrays;

public class Caesar extends Mono{

    private int shifter;

    public Caesar(int shifter){
        this.shifter = shifter;
        if(shifter > 25){
            this.shifter = shifter % 26;
        }
    }

    public void encryptFile(InputStream in, OutputStream out) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String plainText = "";

        String currentLine = reader.readLine();
        while (currentLine != null) {
            plainText += currentLine + "\n";
            currentLine = reader.readLine();
        }

        String ciphertext = encrypt(plainText, shifter);
        out.write(ciphertext.getBytes("UTF-8"));
    }

    public void decryptFile(InputStream in, OutputStream out) throws IOException {
        // Use InputStreamReader with UTF-8 encoding
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String cipherText = "";

        String currentLine = reader.readLine();
        while (currentLine != null) {
            cipherText += currentLine + "\n";
            currentLine = reader.readLine();
        }

        String plaintext = decrypt(cipherText, shifter);
        out.write(plaintext.getBytes("UTF-8"));
    }

    public String encrypt(String plainText, int shifter){
        plainText = plainText.toLowerCase();
        String cipherText = "";
        for (int i = 0; i < plainText.length(); i++){
            char ch = plainText.charAt(i);
            cipherText += alphabet[(Arrays.binarySearch(alphabet, ch) + shifter) % 26];
        }
        return cipherText;
    }

    public String decrypt(String cipherText, int shifter){
        cipherText = cipherText.toLowerCase();
        String plainText = "";
        for(int i = 0; i < cipherText.length(); i++){
            char ch = cipherText.charAt(i);
            int shiftLength = Arrays.binarySearch(alphabet, ch) - shifter;
            if(shiftLength < 0){
                shiftLength = 26 + shiftLength;
                plainText += alphabet[Arrays.binarySearch(alphabet, ch) + shiftLength];
            } else {
                plainText += alphabet[Arrays.binarySearch(alphabet, ch) - shiftLength];
            }
        }
        return plainText;
    }

    public void save(OutputStream out) throws IOException {
        out.write("MONO\n".getBytes("UTF-8"));
        String key = "";
        for(int i = 0; i < alphabet.length; i++){
            if(i + shifter > 26){
                key += alphabet[(i + shifter) % 26];
            } else {
                key += alphabet[(i + shifter) % 26];
            }
        }
        out.write(key.toUpperCase().getBytes("UTF-8"));
        out.write("\n".getBytes("UTF-8"));
    }

}
