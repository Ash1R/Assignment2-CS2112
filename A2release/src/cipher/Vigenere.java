package cipher;

public class Vigenere implements Cipher{

    private String key;

    public Vigenere(String key){
        this.key = key;
    }


    @Override
    public String encrypt(String plaintext) {
        String ciphertext = "";

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            if (Character.isLetter(plainChar)) {
                char encryptedChar = shifter(plainChar, keyChar);
                ciphertext += encryptedChar;
            } else {
                ciphertext += plainChar;
            }
        }
        return ciphertext;
    }

    // Method to decrypt the given ciphertext
    @Override
    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.toLowerCase();
        String plaintext = "";

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            if (Character.isLetter(cipherChar)) {
                char decryptedChar = reverseShifter(cipherChar, keyChar);
                plaintext += decryptedChar;
            } else {
                plaintext += cipherChar;
            }
        }
        return plaintext;
    }

    
    public char shifter(char inp, char shift) {
        int shiftedValue = ((int) inp - (int) 'a' + (int) shift - (int) 'a') % 26;
        return (char) ((int) 'a' + shiftedValue);  // Cast back to char after shifting
    }

    public char reverseShifter(char inp, char shift) {
        int shiftedValue = ((int) inp - (int) 'a' - ((int) shift - (int) 'a') + 26) % 26;
        return (char) ((int) 'a' + shiftedValue);  // Cast back to char after reverse shifting
    }
}
