import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class FileCreator {

  public static void combineDownloadedSegments(int downloadedSegments) throws IOException {
    // TODO Write downloadedSegment to File with fileName
    String outputName = "output.mp4";
    FileOutputStream fos = new FileOutputStream(outputName);
    long transferedByte = 0;
    
    for (int i = 0; i < downloadedSegments; i++) {
      FileInputStream in = new FileInputStream("segment_" + i + ".mp4");
      ReadableByteChannel rbc = Channels.newChannel(in);
      transferedByte += fos.getChannel().transferFrom(rbc, transferedByte, Long.MAX_VALUE);
      in.close();
      new File("segment_" + i + ".mp4").delete();
    }
    
    fos.close();
  }

}
