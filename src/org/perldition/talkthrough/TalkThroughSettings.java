package org.perldition.talkthrough;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.content.Intent;

public class TalkThroughSettings extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void startTalkThrough(View view) {
        Intent startIntent = new Intent();
        startIntent.setAction("org.perldition.talkthrough.TalkThrough.start");
        this.startService(startIntent);
    }

    public void stopTalkThrough(View view) {
        Intent startIntent = new Intent();
        startIntent.setAction("org.perldition.talkthrough.TalkThrough.stop");
        this.startService(startIntent);
    }

}
