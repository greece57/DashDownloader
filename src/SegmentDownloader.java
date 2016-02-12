import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class SegmentDownloader {

  private int downloadedSegments = 0;
  
  public long getCurrentBandwidth() throws MalformedURLException, IOException {
    
    byte[] buffer = new byte[1000000];
    InputStream is = new URL("http://raphraph.kellerkids-solutions.com/1mbfile.txt").openStream();
    long start = System.nanoTime();
    while (is.read(buffer) != -1) continue;
    long end = System.nanoTime();
    long time = end-start;
    
    System.out.println("Current Bandwidth: " + time);
    
    return time;
  }

  public void downloadSegment(Segment nextSegment, boolean video) throws MalformedURLException, IOException {
    URL website = new URL(nextSegment.getSegmentURL());
    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
    String outputName = "";
    if (video)
    {
      outputName = "segment_" + downloadedSegments + ".mp4";
      downloadedSegments++;      
    }
    else
      outputName = "audio.mp4";

    FileOutputStream fos = new FileOutputStream(outputName);
    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    fos.close();
  }
  
  public int getDownloadedSegments() {
    return downloadedSegments;
  }

  
}
