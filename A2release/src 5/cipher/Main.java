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
    OutputStream encrDecrOutputStream;
    InputStream textInputStream;
    String outputFileName;

    //booleans to save command line arguments to be acted upon in execute metho
    boolean encrypt;
    boolean decrypt;
    boolean savingToFile;
    boolean printOutput;

    public static void main(String[] args) {
        //parse command line args
        int pos = 0;
        Main program = new Main();
        pos = program.parseCipherType(args, pos);
        pos = program.parseCipherFunction(args, pos);
        pos = program.parseOutputOptions(args, pos);
        program.execute();
    }

    /**
     * Reads boolean variables edited in response to command line argyments and carries out
     * the correspoding operations, specifically encryption, decryption, and printing
     * */
    private void execute() {
        //if not saving to a file, create a ByteArrayOutputStream
        //otherwise we would have already created a FileOutputStream (see case for --out)
        if (!savingToFile){
            encrDecrOutputStream = new ByteArrayOutputStream();
        }

        //call encrypt if encrypting
        if (encrypt) {
            try {
                cipher.encrypt(textInputStream, encrDecrOutputStream);

            }  catch (IOException e) {
                System.out.println("ERROR reading input" + e.getMessage());
            }
        }

        //call decrypt if decrypting
        if (decrypt) {
            try {cipher.decrypt(textInputStream, encrDecrOutputStream);
            } catch (IOException e) {
                System.out.println("ERROR reading input" + e.getMessage());
            } catch (NullPointerException e){
                System.out.println("ERROR: Cipher was not created");
            }
        }

        //if we saved to a FileOutputStream, call a method to print from a file
        if (savingToFile && printOutput){
            printOutputFile();

        } else if (printOutput){ //otherwise, just use System to print from ByteArray
            try{
                System.out.println(((ByteArrayOutputStream)encrDecrOutputStream).toString("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println("ERROR UTF-8 not supported for this output: " + e.getMessage());
            }
        }
    }

    /**
     * Prints output that was saved by reading using BufferedReader
     * */
    private void printOutputFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
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
                    //only create cipher if first line of file matches with intended ciphertype
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
                    //only create cipher if first line of file matches with intended ciphertype
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

                    //only create cipher if first line of file matches with intended ciphertype
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
                encrypt = true;
                try {
                    textInputStream = new ByteArrayInputStream(args[pos++].getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    System.out.println("UTF-8 not supported for provided characters");
                }

                break;
            case "--ef":

                try {
                    textInputStream = new FileInputStream(args[pos++]);
                    //records flag for encryption
                    encrypt = true;
                    //cipher.encrypt(in, cipherOutput);


                } catch (FileNotFoundException e) {
                    System.out.println("ERROR file not found: " + e.getMessage());
                }
                break;
            case "--dm":
                decrypt = true;
                try {
                    textInputStream = new ByteArrayInputStream(args[pos++].getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    System.out.println("UTF-8 not supported for provided characters");
                }
                break;
            case "--df":
                try {
                    textInputStream = new FileInputStream(args[pos++]);
                    //records flag for decryption
                    decrypt = true;
                    //cipher.decrypt(in, cipherOutput);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR file not found: " + e.getMessage());
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
                    // ciphers only
                    //records desire to print
                    printOutput = true;
                    break;
                case "--out":
                    savingToFile = true;
                    outputFileName = args[pos++];
                    try {
                        //create a FileOutputStream because we're writing to a file
                        encrDecrOutputStream = new FileOutputStream(outputFileName);
                    } catch (IOException e) {
                        System.out.println("ERROR writing file:" + e.getMessage());
                    }
                    break;
                case "--save":
                    try {
                        filename = args[pos++];

                        OutputStream out = new FileOutputStream(filename);
                        try {
                            cipher.save(out);
                        } catch (NullPointerException e){
                            System.out.println("ERROR: Cipher not found! Make sure you loaded the cipher correctly.");
                        }
                    } catch (FileNotFoundException e){
                        System.out.println("ERROR: File not found: " + e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("ERROR: File not found to save to");
                    } catch (IOException e) {
                        System.out.println("ERROR reading file:" + e.getMessage());
                    }
                    break;
                default:

            }
        }
        return pos;
    }
}