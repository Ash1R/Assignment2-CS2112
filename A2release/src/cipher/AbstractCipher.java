package cipher;

import java.io.*;

/** A place to put some inherited code? */
public abstract class AbstractCipher implements Cipher {
    //static ascii values for a and z
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
        out.close();
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

    /**
     * Shifts a character forward by specified amt; loops around (ex. z -> a)
     * uses char ASCII values
     * @param inp the character to be shifted
     * @param shiftAmt how many characters inp should be shifted by
     * @return a character shifted forward from inp by shiftAmt
     * */
    public char shifter(char inp, int shiftAmt) {
        if (Character.isWhitespace(inp)){
            return ' ';
        }
        int shifted = inp + shiftAmt;
        if (shifted > z_ASCII){
            shifted -= 26; //length of alphabet
        }
        return (char) shifted;
    }

    /**
     * Shifts a character backwards by specified amt; loops around (ex. a -> z)
     * uses char ASCII values
     * @param inp the character to be shifted
     * @param shiftAmt how many characters inp should be shifted by
     * @return a character shifted back from inp by shiftAmt
     * */
    public char reverseShifter(char inp, int shiftAmt) {
        if (Character.isWhitespace(inp)){
            return ' ';
        }
        int shifted = inp - shiftAmt;
        if (shifted < a_ASCII){
            shifted += 26; //length of alphabet
        }
        return (char)shifted;
    }

    /**
     * Makes a string only have alphabetic and whitespace characters
     * Enforces the class invariant of Mono and Vigenere
     * @param input String to be filtered
     * @return a String without any non-alphabetic, non-whitespace characters from input
     * */
    public static String filterNonAlphabetic(String input) {
        String filtered = "";
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c) || Character.isWhitespace(c)) {
                filtered += c;
            }
        }
        return filtered;
    }


}
