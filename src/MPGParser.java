import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class MPGParser {
  
  public MPGInfos parseFile(String mpgFile) throws MalformedURLException, ParserConfigurationException, SAXException, IOException
  {
    MPGInfos info = new MPGInfos();
    
    // parse mpgFile and return info
    
    String baseAddress = getBaseAddress(mpgFile);
    
    info = readXMLDoc(mpgFile, baseAddress);
    
    return info;
  }

  private MPGInfos readXMLDoc(String mpgFile, String baseAddress) throws MalformedURLException, ParserConfigurationException, SAXException, IOException {
    
    MPGInfos info = new MPGInfos();
    
    Document doc = getXML(mpgFile);
    NodeList nl = doc.getChildNodes();
    Node mpd = null;

    // find MPD Node
    for (int i = 0; i < nl.getLength(); i++)
    {
      if (nl.item(i).getNodeName().equals("MPD"))
      {
        mpd = nl.item(i);
      }
    }
    
    nl = mpd.getChildNodes();
    ArrayList<Node> adaptionSets = new ArrayList<Node>();
    
    // find ADAPTION SET Nodes
    for (int i = 0; i < nl.getLength(); i++){
      if (nl.item(i).getNodeName().equals("Period"))
      {
        NodeList innerNodeList = nl.item(i).getChildNodes();
        for (int j = 0; j < innerNodeList.getLength(); j++) {
          if (innerNodeList.item(j).getNodeName().equals("AdaptationSet")){
            adaptionSets.add(innerNodeList.item(j));
          }
        }
      }
    }
    
    ArrayList<Node> videoRepresentations = new ArrayList<Node>();
    Node audioRepresentation = null;
    
    // Find Video Representation Nodes
    nl = adaptionSets.get(0).getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      if (nl.item(i).getNodeName().equals("Representation")){
        videoRepresentations.add(nl.item(i));
      }
    }
    
    // Find Audio Representation Node
    nl = adaptionSets.get(1).getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      if (nl.item(i).getNodeName().equals("Representation")){
        audioRepresentation = nl.item(i);
      }
    }
    
    // set Bandwiths and URLs
    int bandwiths[] = new int[4];
    String baseUrls[] = new String[4];
    for (int i = 0; i < videoRepresentations.size(); i++) {
      bandwiths[i] = Integer.parseInt(videoRepresentations.get(i).getAttributes().getNamedItem("bandwidth").getNodeValue());
      nl = videoRepresentations.get(i).getChildNodes();
      for (int j = 0; j < nl.getLength(); j++) {
        if (nl.item(j).getNodeName().equals("BaseURL")) {
          baseUrls[i] = nl.item(j).getTextContent();
        }
      }
    }
    
    info.setMinimumBandwidths(bandwiths[3], bandwiths[2], bandwiths[1]);
     
    //For each Segment Insert the URL to the 4 Quality Segments
    // since only 1 Segment...
    info.insertNewSegments(baseAddress+baseUrls[3], baseAddress+baseUrls[2], baseAddress+baseUrls[1], baseAddress+baseUrls[0]);
    
    // Set AudioURL
    String audioUrl = "";
    nl = audioRepresentation.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      if (nl.item(i).getNodeName().equals("BaseURL")) {
        audioUrl = nl.item(i).getTextContent();      
      }
    }
    
    info.setAudioSegment(baseAddress+audioUrl);
    
    return info;
  }

  private String getBaseAddress(String mpgFile) {
    String baseAddress = mpgFile;
    while (!baseAddress.endsWith("/")) {
      baseAddress = baseAddress.substring(0, baseAddress.length()-1);
    }
    return baseAddress;
  }

  private Document getXML(String mpgFile) throws ParserConfigurationException, MalformedURLException, SAXException, IOException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(new URL(mpgFile).openStream());
    
    return doc;
  }
}
