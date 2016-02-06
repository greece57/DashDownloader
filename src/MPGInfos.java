import java.util.LinkedList;
import java.util.Queue;


public class MPGInfos {
  private Queue<Segment> segmentsTopQuali = new LinkedList<Segment>();
  private Queue<Segment> segmentsHighQuali = new LinkedList<Segment>();
  private Queue<Segment> segmentsMediumQuali = new LinkedList<Segment>();
  private Queue<Segment> segmentsLowQuali = new LinkedList<Segment>();
  
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
  
  public Segment readNextSegment(int bandwidth) {
    if (bandwidth > bandwidthTopQuali) { 
      return segmentsTopQuali.poll();
    } else if (bandwidth > bandwidthHighQuali) {
      return segmentsHighQuali.poll();
    } else if (bandwidth > bandwidthMediumQuali) {
      return segmentsMediumQuali.poll();
    } else {
      return segmentsLowQuali.poll();
    }        
  }
}