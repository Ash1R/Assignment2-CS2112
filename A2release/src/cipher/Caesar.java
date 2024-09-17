package cipher;

import java.io.*;
import java.util.Arrays;

public class Caesar extends Mono implements Cipher{

    private int shifter;

    public Caesar(int shifter){
        super("");
        this.key = generateKey(shifter);


        }
    public String generateKey(int shift){
        String caesarKey = "";
        for (int i = 0; i < alphabet.length; i++){
            if (shift >= 0){
                caesarKey += shifter(alphabet[i], shift);
            } else {
                caesarKey += reverseShifter(alphabet[i], -shift);
            }
        }
        return caesarKey;
    }




}
