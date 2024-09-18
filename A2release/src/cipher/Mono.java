package cipher;

import java.io.*;
import java.util.HashMap;

public class Mono extends AbstractCipher implements Cipher{
    public String key;

    public Mono(String encrAlph){
        this.key = encrAlph.toLowerCase();
    }

    @Override
    public String encrypt(String plaintext){
        plaintext = filterNonAlphabetic(plaintext.toLowerCase());
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++){
            if (Character.isWhitespace(plaintext.charAt(i))){
                ciphertext += ' ';
            } else{
                //convenient use of ascii values
                ciphertext += key.charAt((int)plaintext.charAt(i) - 97);
            }
        }
        return ciphertext;
    }

    @Override
    public String decrypt(String ciphertext){
        ciphertext = filterNonAlphabetic(ciphertext.toLowerCase());
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++){
            if (Character.isWhitespace(ciphertext.charAt(i))){
                plaintext += ' ';
            } else{
                 //find position of character in encrAlph
                int j;
                for (j = 0; j < key.length(); j++){
                    if (key.charAt(j) == ciphertext.charAt(i)){
                        break;
                    }
                }
                plaintext += alphabet[j];
            }
        }
        return plaintext;
    }

    @Override
    public void save(OutputStream out) throws IOException {
        out.write("MONO\n".getBytes("UTF-8"));
        out.write(key.toUpperCase().getBytes("UTF-8"));
        out.write("\n".getBytes("UTF-8"));
    }

    public static char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'};


}
