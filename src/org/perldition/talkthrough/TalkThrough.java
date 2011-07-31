package org.perldition.talkthrough;

import android.util.Log;
import android.os.Binder;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import org.perldition.talkthrough.AudioEcho;

public class TalkThrough extends Service {
    private AudioEcho mEchoer;

    public class TalkThroughBinder extends Binder {
        TalkThrough getService() {
            return TalkThrough.this;
        }
    }

    private final IBinder mBinder = new TalkThroughBinder();

    @Override public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("org.perldition.talkthrough.TalkThrough.start")) {
            startService();
        } else if (intent.getAction().equals("org.perldition.talkthrough.TalkThrough.stop")) {
            stopService();
        }

        return START_STICKY;
    }

    public void startService() {
        if (mEchoer != null)
            return;

        mEchoer = new AudioEcho();
        mEchoer.start();
    }

    public void stopService() {
        if (mEchoer != null) {
            mEchoer.close();

            try {
                mEchoer.join();
            } catch (InterruptedException e) {
                Log.d("TalkThrough", "intr fail " + e);
            }

            mEchoer = null;
        }

        stopSelf();
    }
}
