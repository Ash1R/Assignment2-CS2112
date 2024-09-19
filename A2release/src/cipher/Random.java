package cipher;

import java.io.*;
import java.util.Arrays;

public class Random extends Mono{

    public Random(){
        super("");
        this.key = randomize();
    }

    //create a new random key
    public String randomize(){
        char[] map = new char[26];
        for(int i = 0; i < 26; i++){
            boolean filled = false;
            while(!filled) {
                int rand = (int) (Math.random() * 26);
                if (map[rand] == '\u0000') {
                    map[rand] = alphabet[i];
                    filled = true;
                }
            }
        }
        String stringKey =new String(map);
        return stringKey;
    }

}
