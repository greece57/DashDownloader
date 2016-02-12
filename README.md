# DashDownloader

This DashDownloader is able to download the video behind the following mpd file:
http://dash.edgesuite.net/dash264/TestCasesHD/2a/qualcomm/1/MultiResMPEG2.mpd

It analyzes the xml structure in the mpd file and saves the video and audio segments into a intern datamodel.

After that it chooses each segment which shall be downloaded according to the current bandwidth.
The Bandwidth is measured by downloading a selfuploaded 1 000 000 byte file from a self-owned server and keeping the time.

Then it downloads the file to a file on the harddrive of the owner naming it "segment_x.mp4" with upcounting x
After downloading all video segments in downloads the audio segment (in this mpd file there is only 1 audiofile so the program does 
not mesure the bandwidth)

In the end it shall combine all downloaded video and audio segments to one file.
Concatinating 2 video files was not necessary in that mpd file but can easily be added to the functionality of that program by just
combining the inputstreams of each saved sagment.
We tried to combining audio and video but it is not so easy possible without using other programs like FFmpeg: http://ffmpeg.zeranoe.com/builds/

Even though the programm runs only with this specific mpd file it is easy possible to extend the functionality to another mpd file with
more then 1 segment since the inner data structure is created to support more then 1 segment.
