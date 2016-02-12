import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class DashDownloader {

  public static void main(String[] args) throws MalformedURLException, ParserConfigurationException, SAXException, IOException {
    // TODO Auto-generated method stub

    MPGParser p = new MPGParser();
    String mpgFile = "http://dash.edgesuite.net/dash264/TestCasesHD/2a/qualcomm/1/MultiResMPEG2.mpd";
    
    MPGInfos info = p.parseFile(mpgFile);
    
    SegmentDownloader sg = new SegmentDownloader();
    
    Segment nextSegment = info.readNextSegment(0);//sg.getCurrentBandwidth());

    while (nextSegment != null) {
      sg.downloadSegment(nextSegment, true);
      nextSegment = info.readNextSegment(sg.getCurrentBandwidth());
    }
    sg.downloadSegment(info.getAudioSegment(), false);
    
    FileCreator.combineDownloadedSegments(sg.getDownloadedSegments());
  }

}
