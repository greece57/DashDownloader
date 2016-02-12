#DashDownloader

This DashDownloader is able to download the video behind the following mpd file:
http://dash.edgesuite.net/dash264/TestCasesHD/2a/qualcomm/1/MultiResMPEG2.mpd

It analyzes the xml structure in the mpd file and saves the video and audio segments into an intern data model.

After that it chooses each segment which shall be downloaded according to the current bandwidth.
The Bandwidth is measured by downloading a self-uploaded 1 000 000 byte file from a self-owned server and keeping the time.

Then it downloads the file to a file on the hard drive of the owner naming it "segment_x.mp4" while increasing x after each Segment.
After downloading all video segments it downloads the audio segment (in this mpd file there is only 1 audio file so the program does not measure the bandwidth).

In the end it shall combine all downloaded video and audio segments to one file.
Concatenating 2 video files was not necessary in that mpd file but we added the functionality by just combining the streams of each saved segment and concatenating them. We couldn’t test the function though because of our mpd file.
We tried to combining audio and video but it is not so easy possible without using other programs like FFmpeg: http://ffmpeg.zeranoe.com/builds/

Even though the program runs only with this specific mpd file it is easily possible to extend the functionality to another mpd file with more than 1 segment, since the inner data structure is created to support more than 1 segment.
