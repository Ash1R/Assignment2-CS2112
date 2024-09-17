package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cipher.Cipher;
import cipher.CipherFactory;
import org.junit.jupiter.api.Test;

import java.io.*;

public class BasicCipherTests {

    private final CipherFactory cipherFactory = new CipherFactory();

    @Test
    void testBasicCaesar() {
        Cipher caesar = cipherFactory.getCaesarCipher(5);

        assertEquals("wmnst", caesar.encrypt("rhino"));
        assertEquals("btrgfy", caesar.encrypt("wombat"));
        assertEquals("cajgg", caesar.encrypt("xvebb"));
        assertEquals("fyyfhp", caesar.encrypt("attack"));
        assertEquals("mjqqt btwqi", caesar.encrypt("hello, world"));

        Cipher caesarNegative = cipherFactory.getCaesarCipher(-5);
        assertEquals("mcdij", caesarNegative.encrypt("rhino"));
        assertEquals("", caesar.encrypt(""));

        Cipher caesarLargeShift = cipherFactory.getCaesarCipher(30);
        assertEquals("vlmrs", caesarLargeShift.encrypt("rhino"));
        assertEquals("mjqqt", caesar.encrypt("hello"));

        Cipher caesarZeroShift = cipherFactory.getCaesarCipher(0);
        assertEquals("hello", caesarZeroShift.encrypt("hello"));

        assertEquals("rhino", caesar.decrypt("wmnst"));
        assertEquals("wombat", caesar.decrypt("btrgfy"));
        assertEquals("xvebb", caesar.decrypt("cajgg"));
        assertEquals("attack", caesar.decrypt("fyyfhp"));
        assertEquals("hello world", caesar.decrypt("mjqqt btwqi"));

        assertEquals("rhino", caesarNegative.decrypt("mcdij"));
        assertEquals("", caesar.decrypt(""));

        assertEquals("rhino", caesarLargeShift.decrypt("vlmrs"));
        assertEquals("hello", caesar.decrypt("mjqqt"));

        assertEquals("hello", caesarZeroShift.decrypt("hello"));

        //obfuscation test
        assertEquals("hello", caesarZeroShift.decrypt("he3974638784765*&*&^*&^4653llo"));

    }

    @Test
    void testBasicVigenere() {
        Cipher vigenere = cipherFactory.getVigenereCipher("abc");
        assertEquals("agesc", vigenere.encrypt("zebra"));
        assertEquals("zebra", vigenere.decrypt("agesc"));

        assertEquals("uqpptupy", vigenere.encrypt("tomorrow"));
        assertEquals("tomorrow", vigenere.decrypt("uqpptupy"));

        Cipher vigenere2 = cipherFactory.getVigenereCipher("abc");
        assertEquals("", vigenere2.encrypt(""));
        assertEquals("", vigenere2.decrypt(""));

        Cipher vigenere3 = cipherFactory.getVigenereCipher("");
        assertEquals("hello", vigenere3.encrypt("hello"));
        assertEquals("hello", vigenere3.decrypt("hello"));

        Cipher vigenere4 = cipherFactory.getVigenereCipher("bqxoshh");
        assertEquals("vyc fnqkm spdpv nqo hjfxa qmcg eiha umvl", vigenere4.encrypt("the quick brown fox jumps over lazy dogs"));
        assertEquals("the quick brown fox jumps over lazy dogs", vigenere4.decrypt("vyc fnqkm spdpv nqo hjfxa qmcg eiha umvl"));

        //obfuscation test
        assertEquals("vyc fnqkm spdpv nqo hjfxa qmcg eiha umvl", vigenere4.encrypt("the 79848736*$&#&#$*^quick brown fox jumps over lazy dogs"));
        assertEquals("the quick brown fox jumps over lazy dogs", vigenere4.decrypt("vyc fnqkm spd#$(*&983498347pv nqo hjfxa qmcg eiha umvl"));

    }

    @Test
    void testBasicRandom() {
        Cipher random = cipherFactory.getRandomSubstitutionCipher();
        String s1 = "albatross";
        assertEquals(s1, random.decrypt(random.encrypt(s1)));
        assertEquals(s1, random.encrypt(random.decrypt(s1)));

        String s2 = "Albat^*($^98439847389ross";
        assertEquals(s1, random.decrypt(random.encrypt(s2)));
        assertEquals(s1, random.encrypt(random.decrypt(s2)));

        String s3 = "";
        assertEquals(s3, random.decrypt(random.encrypt(s3)));
        assertEquals(s3, random.encrypt(random.decrypt(s3)));
    }

    @Test
    void testBasicRSA() throws Exception {
        Cipher rsa = cipherFactory.getRSACipher();
        String s = "dog";
        File f = new File("examples/temp.rsa");
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        f.createNewFile();
        rsa.encrypt(new ByteArrayInputStream(s.getBytes()), fileOutputStream);
        fileOutputStream.flush();
        FileInputStream fileInputStream = new FileInputStream(f);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(3);
        rsa.decrypt(fileInputStream, arrayOutputStream);
        arrayOutputStream.flush();
        assertEquals(s, arrayOutputStream.toString().trim());
        f.delete();
    }
}
