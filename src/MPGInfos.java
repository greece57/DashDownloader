import java.util.LinkedList;
import java.util.Queue;


public class MPGInfos {
  private Queue<Segment> segmentsTopQuali = new LinkedList<Segment>();
  private Queue<Segment> segmentsHighQuali = new LinkedList<Segment>();
  private Queue<Segment> segmentsMediumQuali = new LinkedList<Segment>();
  private Queue<Segment> segmentsLowQuali = new LinkedList<Segment>();
  
  private Segment audioSegment = null;
  
  private int bandwidthTopQuali;
  private int bandwidthHighQuali;
  private int bandwidthMediumQuali;
  
  public MPGInfos() {

  }
  
  public void setMinimumBandwidths(int topQuali, int highQuali, int mediumQuali) {
    bandwidthTopQuali = topQuali;
    bandwidthHighQuali = highQuali;
    bandwidthMediumQuali = mediumQuali;
  }
  
  public void insertNewSegments(String topQuali, String highQuali, String mediumQuali, String lowQuali) {
    segmentsTopQuali.add(new Segment(topQuali));
    segmentsHighQuali.add(new Segment(highQuali));
    segmentsMediumQuali.add(new Segment(mediumQuali));
    segmentsLowQuali.add(new Segment(lowQuali));
  }
  
  public void setAudioSegment(String audioUrl) {
    audioSegment = new Segment(audioUrl);
  }
  
  public Segment getAudioSegment() {
    return audioSegment;
  }
  
  public Segment readNextSegment(long l) {
    Segment topQuali = segmentsTopQuali.poll();
    Segment highQuali = segmentsHighQuali.poll();
    Segment mediumQuali = segmentsMediumQuali.poll();
    Segment lowQuali = segmentsLowQuali.poll();
    
    if (l > bandwidthTopQuali) { 
      System.out.println("Downloading Segment in Top Quality");
      return topQuali;
    } else if (l > bandwidthHighQuali) {
      System.out.println("Downloading Segment in High Quality");
      return highQuali;
    } else if (l > bandwidthMediumQuali) {
      System.out.println("Downloading Segment in Medium Quality");
      return mediumQuali;
    } else {
      System.out.println("Downloading Segment in Low Quality");
      return lowQuali;
    }        
  }
}