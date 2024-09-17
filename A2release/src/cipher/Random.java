package cipher;

import java.io.*;
import java.util.Arrays;

public class Random extends Mono{

    private String[] map = randomize();

    public String[] randomize(){
        String[] map = new String[26];
        for(int i = 0; i < 26; i++){
            boolean filled = false;
            while(!filled) {
                int rand = (int) (Math.random() * 26);
                if (map[rand] == null) {
                    map[rand] = alphabet[i];
                    filled = true;
                }
            }
        }
        return map;
    }

    public void encryptFile(InputStream in, OutputStream out) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String plainText = "";

        String currentLine = reader.readLine();
        while (currentLine != null) {
            plainText += currentLine + "\n";
            currentLine = reader.readLine();
        }

        String ciphertext = encrypt(plainText, map);
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

        String plaintext = decrypt(cipherText, map);
        out.write(plaintext.getBytes("UTF-8"));
    }

    public String encrypt(String plainText, String[] map){
        plainText = plainText.toLowerCase();
        String cipherText = "";
        for(int i = 0; i < plainText.length(); i++) {
            char ch = plainText.charAt(i);
            cipherText += map[Arrays.binarySearch(alphabet, ch)];
        }
        return cipherText;
    }

    public String decrypt(String cipherText, String[] map){
        cipherText = cipherText.toLowerCase();
        String plainText = "";
        for(int i = 0; i < cipherText.length(); i++){
            char ch = cipherText.charAt(i);
            plainText += alphabet[Arrays.binarySearch(map, ch)];
        }
        return plainText;
    }

    public void save(OutputStream out) throws IOException {
        out.write("MONO\n".getBytes("UTF-8"));
        String key = "";
        for(int i = 0; i < 26; i++){
            key += map[i];
        }
        out.write(key.toUpperCase().getBytes("UTF-8"));
        out.write("\n".getBytes("UTF-8"));
    }

}
