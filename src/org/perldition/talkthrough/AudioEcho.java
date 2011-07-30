package org.perldition.talkthrough;

import android.os.Process;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.MediaRecorder.AudioSource;

public class AudioEcho extends Thread {
    private boolean stopped = false;

    @Override public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

        int bufsiz = AudioRecord.getMinBufferSize(8000,
                                                  AudioFormat.CHANNEL_IN_MONO,
                                                  AudioFormat.ENCODING_PCM_16BIT);

        AudioRecord recorder = new AudioRecord(AudioSource.MIC,
                                               8000,
                                               AudioFormat.CHANNEL_IN_MONO,
                                               AudioFormat.ENCODING_PCM_16BIT,
                                               bufsiz);

        AudioTrack track = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
                                          8000,
                                          AudioFormat.CHANNEL_OUT_MONO,
                                          AudioFormat.ENCODING_PCM_16BIT,
                                          bufsiz,
                                          AudioTrack.MODE_STREAM);

        recorder.startRecording();
        track.play();

        short buf[] = new short[bufsiz];

        while (!stopped) {
            int read = recorder.read(buf, 0, 32);
            track.write(buf, 0, read);
        }

        track.stop();
        track.release();
        recorder.stop();
        recorder.release();
    }

    public void close() {
        stopped = true;
    }
}
