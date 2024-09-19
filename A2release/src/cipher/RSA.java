package cipher;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;


public class RSA implements Cipher{

    public BigInteger n;
    public BigInteger e;
    private BigInteger d;


    public RSA(){
        //p * q will be 1021-1022 bits
        BigInteger q = new BigInteger(511, 20, new Random());
        BigInteger p = new BigInteger(511, 20, new Random());
        n = q.multiply(p);

        BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        //if e is prime and larger than p and q, it cannot be a factor of (p-1)(q-1)
        e = new BigInteger(512, 20, new Random());

        //implements extended euclidean algorithm
        d = e.modInverse(totient);

    }

    public RSA(BigInteger e, BigInteger n, BigInteger d){
        this.e = e;
        this.n = n;
        this.d = d;
    }

    public BigInteger getD(){
        return d;
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) throws IOException {

        byte[] currentChunk = new byte[127];
        InputReader reader = new InputReader(in, 127);
        int counter = 0;
        //read 126 bytes of data at a time plus the length byte, encrypt and write to out
        while (reader.hasNext()){
            System.out.println(counter++);
            int bytesRead = reader.nextChunk(currentChunk);
            if (bytesRead == 0){
                break;
            }

            BigInteger chunkToEncrypt = new BigInteger(currentChunk);
            System.out.println("Pre-Encryption Integer");
            System.out.println(chunkToEncrypt);

            BigInteger ciphertext = chunkToEncrypt.modPow(e, n);
            BigInteger instantD = ciphertext.modPow(d, n);
            System.out.println("After Encryption Integer");
            System.out.println(ciphertext);
            out.write(ciphertext.toByteArray());
        }
        out.close();

    }

    @Override
    public void decrypt(InputStream in, OutputStream out) throws IOException {

        byte[] currentChunk = new byte[128];
        EncryptedBytesReader reader = new EncryptedBytesReader(in, 128);
        int counter = 0;

        //read 128 bytes of data at a time plus the length byte, encrypt and write to out
        while (reader.hasNext()){
            System.out.println(counter++);
            int bytesRead = reader.nextChunk(currentChunk);
            if (bytesRead == 0){
                break;
            }
            BigInteger chunkToDecrypt = new BigInteger(currentChunk);
            System.out.println("chunk before decrypting");
            System.out.println(chunkToDecrypt);
            BigInteger decodedBigInteger = chunkToDecrypt.modPow(d, n);
            System.out.println("Decoded Integer");
            System.out.println(decodedBigInteger);
            byte[] decodedByteArray = decodedBigInteger.toByteArray();

            out.write(Arrays.copyOfRange(decodedByteArray, 1, (int)decodedByteArray[0] + 1));
        }
    }

    @Override
    public String encrypt(String plaintext){
        System.out.println("ERROR: RSA input must be read from a file! Returning empty string...");
        return "";
    }

    @Override
    public String decrypt(String plaintext){
        System.out.println("ERROR: RSA input must be read from a file! Returning empty string...");
        return "";

    }

    @Override
    public void save(OutputStream out) throws IOException {

        out.write("RSA\n".getBytes("UTF-8"));

        out.write(d.toString().getBytes("UTF-8"));
        out.write("\n".getBytes("UTF-8"));

        out.write(e.toString().getBytes("UTF-8"));
        out.write("\n".getBytes("UTF-8"));

        out.write(n.toString().getBytes("UTF-8"));
        out.write("\n".getBytes("UTF-8"));
        out.flush();
        out.close();
    }


}
