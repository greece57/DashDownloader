
public class DashDownloader {

  public static void main(String[] args) {
    // TODO Auto-generated method stub

    MPGParser p = new MPGParser();
    String mpgFile = "";
    String newFile = "";
    
    MPGInfos info = p.parseFile(mpgFile);
    
    Segment nextSegment = info.readNextSegment(SegmentDownloader.getCurrentBandwidth());
    
    while (nextSegment != null) {
      Object downloadedSegment = SegmentDownloader.downloadSegment(nextSegment);
      FileCreater.writeDownloadedSegmentToFile(newFile, downloadedSegment);
    }
  }

}
