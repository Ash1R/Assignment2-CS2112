package cipher;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

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
    ByteArrayOutputStream cipherOutput = new ByteArrayOutputStream();
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
                cipher = factory.getCaesarCipher(Integer.valueOf(args[pos++]));
                break;
            case "--random":
                cipher = factory.getRandomSubstitutionCipher();
                break;
            case "--monoLoad":
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(args[pos++]));
                    String cipherType = reader.readLine();
                    if (cipherType.equals("MONO")){
                        String key = reader.readLine();
                        cipher = factory.getMonoCipher(key);
                    } else{
                        //should have exited from here
                        System.out.println("ERROR: MONO identifier not found");
                    }

                } catch (FileNotFoundException e){
                    //should have exited from here
                    System.out.println("ERROR: Cipher file not found: " + e.getMessage());
                } catch (IOException e){
                    //should have exited from here
                    System.out.println("ERROR reading file: " + e.getMessage());
                }
                break;
            case "--vigenere":
                cipher = factory.getVigenereCipher(args[pos++]);
                break;
            case "--vigenereLoad":
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(args[pos++]));
                    String cipherType = reader.readLine();
                    if (cipherType.equals("VIGENERE")){
                        String key = reader.readLine();
                        cipher = factory.getVigenereCipher(key);
                    } else{
                        //should have exited from here
                        System.out.println("ERROR: VIGENERE identifier not found");
                    }

                } catch (FileNotFoundException e){
                    //should have exited from here
                    System.out.println("ERROR: Cipher file not found: " + e.getMessage());
                } catch (IOException e){
                    //should have exited from here
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


                        cipher = factory.getRSACipher(e, n, d);

                    } else{
                        //should have exited from here
                        System.out.println("ERROR: RSA identifier not found");
                    }

                } catch (FileNotFoundException e){
                    //should have exited from here
                    System.out.println("ERROR: Cipher file not found: " + e.getMessage());
                } catch (IOException e){
                    //should have exited from here
                    System.out.println("ERROR reading file: " + e.getMessage());
                }
                break;
            default:
                //should have exited from here
                System.out.println("ERROR: Please specify a cipher type");
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
                try {
                    cipherOutput.write(cipher.encrypt(args[pos++]).getBytes("UTF-8"));
                } catch (IOException e) {
                    System.out.println("ERROR writing file: " + e.getMessage());
                }
                break;
            case "--ef":
                // TODO encrypt the contents of the given file
                try {
                    InputStream in = new FileInputStream(args[pos++]);
                    cipher.encrypt(in, cipherOutput);


                } catch (FileNotFoundException e) {
                    System.out.println("ERROR file not found: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("ERROR reading input" + e.getMessage());
                }
                break;
            case "--dm":
                try {
                    cipherOutput.write(cipher.decrypt(args[pos++]).getBytes("UTF-8"));
                } catch (IOException e) {
                    System.out.println("ERROR writing file: " + e.getMessage());
                } catch (NullPointerException e){
                    System.out.println("Cipher not found! Make sure you are loading from the right file, or one that exists.");
                }
                break;
            case "--df":
                // TODO decrypt the contents of the given file
                try {
                    InputStream in = new FileInputStream(args[pos++]);

                    cipher.decrypt(in, cipherOutput);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR file not found: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("ERROR reading input" + e.getMessage());
                }
                break;
            default:
                pos--;

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
                    try{
                        System.out.println(cipherOutput.toString("UTF-8"));

                    } catch (UnsupportedEncodingException e) {
                        System.out.println("ERROR UTF-8 not supported: " + e.getMessage());
                    }
                    break;
                case "--out":
                    filename = args[pos++];
                    try {
                        FileOutputStream fos = new FileOutputStream(filename);
                        fos.write(cipherOutput.toByteArray());
                        fos.close();


                    } catch (IOException e) {
                        System.out.println("ERROR writing file:" + e.getMessage());
                    }
                    break;
                case "--save":
                    filename = args[pos++];
                    System.out.println(filename);
                    try {
                        OutputStream out = new FileOutputStream(filename);
                        try {
                            cipher.save(out);
                        } catch (NullPointerException e){
                            System.out.println("ERROR: Cipher not found! Make sure you loaded the cipher correctly.");
                        }
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
