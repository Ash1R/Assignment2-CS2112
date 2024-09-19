package cipher;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.Reader;


public class EncryptedBytesReader implements ChunkReader {

    private final InputStream stream;
    private final int chunkSize;
    private boolean moreBytes = true;


    public EncryptedBytesReader(InputStream stream, int chunkSize) {
        this.stream = stream;
        this.chunkSize = chunkSize;
    }

    @Override
    public int chunkSize() {
        return chunkSize;
    }

    @Override
    public boolean hasNext() {
        return moreBytes;
    }

    @Override
    public int nextChunk(byte[] data) throws EOFException, IOException {
        if (!hasNext()) {
            throw new EOFException("No more bytes available.");
        }

        //read entire 128 byte chunk
        int bytesRead = stream.read(data, 0, chunkSize);

        if (bytesRead == -1) {
            moreBytes = false;
            return 0;
        }

        //slim chance of 127 bytes, in that case, we pad on the left side
        if (bytesRead < chunkSize && moreBytes){
            System.out.println("WE PAD HERE");
            byte[] newArray = new byte[chunkSize];
            for (int i = 0; i < chunkSize - bytesRead; i++){
                newArray[i] = 0;
            }
            System.arraycopy(data, 0, newArray, chunkSize - bytesRead, bytesRead);
            System.arraycopy(newArray, 0, data, 0, chunkSize);
        }

        return bytesRead;
    }


}
