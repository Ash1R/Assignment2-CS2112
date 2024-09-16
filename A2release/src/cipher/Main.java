package cipher;

import java.io.*;
import java.math.BigInteger;

/**
 * Command line interface to allow users to interact with your ciphers.
 *
 * <p>We have provided some infrastructure to parse most of the arguments. It is your responsibility
 * to implement the appropriate actions according to the assignment specifications. You may choose
 * to "fill in the blanks" or rewrite this class.
 *
 * <p>Regardless of which option you choose, remember to minimize repetitive code. You are welcome
 * to add additional methods or alter the provided code to achieve this.
 */
public class Main {
    Cipher cipher;
    String cipherOutput;
    public static void main(String[] args) {
        int pos = 0;
        Main program = new Main();
        pos = program.parseCipherType(args, pos);
        pos = program.parseCipherFunction(args, pos);
        pos = program.parseOutputOptions(args, pos);
    }

    /**
     * Set up the cipher type based on the options found in args starting at position pos, and
     * return the index into args just past any cipher type options.
     */
    private int parseCipherType(String[] args, int pos) throws IllegalArgumentException {
        CipherFactory factory = new CipherFactory();
        // check if arguments are exhausted
        if (pos == args.length) return pos;

        String cmdFlag = args[pos++];
        switch (cmdFlag) {
            case "--caesar":
                // TODO create a Caesar cipher object with the given shift parameter
                break;
            case "--random":
                // TODO create a random substitution cipher object
                break;
            case "--monoLoad":
                // TODO load a monoaphabetic substitution cipher from a file
                break;
            case "--vigenere":
                cipher = factory.getVigenereCipher(args[pos++]);
                break;
            case "--vigenereLoad":
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(args[pos++]));
                    String cipherType = reader.readLine();
                    if (cipherType.equals("VIGNERE")){
                        cipher = factory.getVigenereCipher(reader.readLine());
                    } else{
                        throw new IllegalArgumentException("Vignere not found");
                    }

                } catch (FileNotFoundException e){
                    System.out.println("ERROR: Cipher file not found: " + e.getMessage());
                } catch (IOException e){
                    System.out.println("ERROR reading file: " + e.getMessage());
                }


                break;
            case "--rsa":
                cipher = factory.getRSACipher();
                break;
            case "--rsaLoad":
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(args[pos++]));
                    String cipherType = reader.readLine();
                    if (cipherType.equals("RSA")){
                        BigInteger d = new BigInteger(reader.readLine());
                        BigInteger e = new BigInteger(reader.readLine());
                        BigInteger n = new BigInteger(reader.readLine());
                        factory.getRSACipher(e, n, d);

                    } else{
                        throw new IllegalArgumentException("RSA not found");
                    }

                } catch (FileNotFoundException e){
                    System.out.println("ERROR: Cipher file not found: " + e.getMessage());
                } catch (IOException e){
                    System.out.println("ERROR reading file: " + e.getMessage());
                }
                break;
            default:
                throw new IllegalArgumentException("Please specify a cipher type");
        }
        return pos;
    }

    /**
     * Parse the operations to be performed by the program from the command-line arguments in args
     * starting at position pos. Return the index into args just past the parsed arguments.
     */
    private int parseCipherFunction(String[] args, int pos) throws IllegalArgumentException {
        // check if arguments are exhausted
        if (pos == args.length) return pos;

        switch (args[pos++]) {
            case "--em":
                cipherOutput = cipher.encrypt(args[pos++]);
                break;
            case "--ef":
                // TODO encrypt the contents of the given file
                try {
                    InputStream in = new FileInputStream(args[pos++]);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    cipher.encrypt(in, out);
                    cipherOutput = out.toString("UTF-8");
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR file not found: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("ERROR reading input" + e.getMessage());
                }
                break;
            case "--dm":
                cipherOutput = cipher.decrypt(args[pos++]);
                break;
            case "--df":
                // TODO decrypt the contents of the given file
                try {
                    InputStream in = new FileInputStream(args[pos++]);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    cipher.decrypt(in, out);
                    cipherOutput = out.toString("UTF-8");
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR file not found: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("ERROR reading input" + e.getMessage());
                }
                break;
            default:
                // TODO
        }
        return pos;
    }

    /**
     * Parse options for output, starting within {@code args} at index {@code argPos}. Return the
     * index in args just past such options.
     */
    private int parseOutputOptions(String[] args, int pos) throws IllegalArgumentException {
        // check if arguments are exhausted
        if (pos == args.length) return pos;

        String cmdFlag;

        //potential file to write to
        String filename;

        while (pos < args.length) {
            switch (cmdFlag = args[pos++]) {
                case "--print":
                    // TODO print result of applying the cipher to the console -- substitution
                    // ciphers only
                    System.out.println(cipherOutput);
                    break;
                case "--out":
                    filename = args[pos++];
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                        writer.write(cipherOutput);
                    } catch (IOException e) {
                        System.out.println("ERROR writing file:" + e.getMessage());
                    }
                    break;
                case "--save":
                    filename = args[pos++];
                    try {
                        OutputStream out = new FileOutputStream(filename);
                        cipher.save(out);
                    } catch (FileNotFoundException e){
                        System.out.println("ERROR: File not found: " + e.getMessage());
                    } catch (IOException e) {
                       System.out.println("ERROR reading file:" + e.getMessage());
                    }
                    break;
                default:
                    // TODO
            }
        }
        return pos;
    }
}
