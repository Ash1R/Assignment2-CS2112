package cipher;

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

    public static void main(String[] args) {
        // TODO implement
    }

    /**
     * Set up the cipher type based on the options found in args starting at position pos, and
     * return the index into args just past any cipher type options.
     */
    private int parseCipherType(String[] args, int pos) throws IllegalArgumentException {
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
                // TODO create a new Vigenere Cipher with the given key
                break;
            case "--vigenereLoad":
                // TODO create a Vigenere cipher with key loaded from the given file
                break;
            case "--rsa":
                // TODO create new RSA cipher
                break;
            case "--rsaLoad":
                // TODO load an RSA key from the given file
                break;
            default:
                // TODO
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
                // TODO encrypt the given string
                break;
            case "--ef":
                // TODO encrypt the contents of the given file
                break;
            case "--dm":
                // TODO decrypt the given string -- substitution ciphers only
                break;
            case "--df":
                // TODO decrypt the contents of the given file
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
        while (pos < args.length) {
            switch (cmdFlag = args[pos++]) {
                case "--print":
                    // TODO print result of applying the cipher to the console -- substitution
                    // ciphers only
                    break;
                case "--out":
                    // TODO output result of applying the cipher to a file
                    break;
                case "--save":
                    // TODO save the cipher key to a file
                    break;
                default:
                    // TODO
            }
        }
        return pos;
    }
}
