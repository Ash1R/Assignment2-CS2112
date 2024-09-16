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

    @Override
    public void encrypt(InputStream in, OutputStream out) throws IOException {

        byte[] currentChunk = new byte[127];
        InputReader reader = new InputReader(in, 127);
        while (reader.hasNext()){
            int bytesRead = reader.nextChunk(currentChunk);


            BigInteger chunkToEncrypt = new BigInteger(Arrays.copyOfRange(currentChunk, 1, ((int)currentChunk[0]) + 2));
            BigInteger ciphertext = chunkToEncrypt.modPow(e, n);
            out.write(ciphertext.toByteArray());
        }
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) throws IOException {

        byte[] currentChunk = new byte[127];
        InputReader reader = new InputReader(in, 127);

        while (reader.hasNext()){
            int bytesRead = reader.nextChunk(currentChunk);
            BigInteger chunkToDecrypt = new BigInteger(Arrays.copyOfRange(currentChunk, 1, (int)currentChunk[0]));
            BigInteger cipherBigInt = chunkToDecrypt.modPow(d, n);
            out.write(cipherBigInt.toByteArray());
        }
    }

    @Override
    public String encrypt(String plaintext){
        /*String encrypted = "";
        byte[] currentChunk = new byte[127];
        InputReader reader = new InputReader(new ByteArrayInputStream((plaintext.getBytes())), 127);
        while (reader.hasNext()){
            int bytesRead = reader.nextChunk(currentChunk);
            BigInteger chunkToEncrypt = new BigInteger(Arrays.copyOfRange(currentChunk, 1, (int)currentChunk[0]));
            BigInteger cipherBigInt = chunkToEncrypt.modPow(e, n);
            encrypted += cipherBigInt.toString();
        }
        return encrypted; */
        return "";
    }

    @Override
    public String decrypt(String plaintext){
       /* String decrypted = "";
        byte[] currentChunk = new byte[127];
        InputReader reader = new InputReader(new ByteArrayInputStream((plaintext.getBytes())), 127);
        while (reader.hasNext()){
            int bytesRead = reader.nextChunk(currentChunk);
            BigInteger chunkToDecrypt = new BigInteger(Arrays.copyOfRange(currentChunk, 1, (int)currentChunk[0]));
            BigInteger cipherBigInt = chunkToDecrypt.modPow(d, n);
            decrypted += cipherBigInt.toString();
        }
        return decrypted; */
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
