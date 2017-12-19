package edu.dwes.conecta4_v1;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Victor on 22/11/2017.
 */

public class Music {
    private static MediaPlayer player;

    public static void play(Context context, int id) {
        player = MediaPlayer.create(context, id);
        player.setLooping(true);
        player.start();
    }

    public static void stop(Context context) {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}