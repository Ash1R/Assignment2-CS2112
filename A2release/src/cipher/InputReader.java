package cipher;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.Reader;


public class InputReader implements ChunkReader {

    private final InputStream stream;
    private final int chunkSize;
    private boolean moreBytes = true;


    public InputReader(InputStream stream, int chunkSize) {
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

        //leave space to add the size of what was read as first byte
        int bytesRead = stream.read(data, 1, chunkSize - 1);

        //add the amount of bytes read into first byte
        data[0] = (byte)bytesRead;


        if (bytesRead == -1) {
            moreBytes = false;
            return 0;
        }

        //add padding on the right side if needed
        if (bytesRead < chunkSize - 1 && moreBytes) {
            for (int i = bytesRead + 1; i < chunkSize; i++) {
                data[i] = 0;
            }
            moreBytes = false;
        }

        return bytesRead;
    }


}
