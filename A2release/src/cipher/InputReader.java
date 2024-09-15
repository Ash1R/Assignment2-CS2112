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
        this.stream = new InputStream(stream);
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
    public int nextChunk(byte[] chunk) throws EOFException, IOException {
        if (hasNext()) {
            throw new EOFException("No more bytes available.");
        }

        int bytesRead = stream.read(chunk, 0, chunkSize);

        if (bytesRead == -1) {
            moreBytes = false;
            throw new EOFException("End of stream reached.");
        }

        if (bytesRead < chunkSize) {
            moreBytes = false;
        }

        return bytesRead;
    }

    public void close() throws IOException {
        inputStream.close();
    }

}
