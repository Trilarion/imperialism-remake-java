/*
 * Copyright (C) 2013 Trilarion
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tools.sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.tools.io.Resource;

/**
 *
 */
public class JukeBox implements PlayEventListener {

    private static final Logger LOG = Logger.getLogger(JukeBox.class.getName());

    private volatile boolean autoRewind = false;
    private volatile boolean autoContinue = true;
    private volatile List<Resource> list = new ArrayList<>(0);
    private volatile int nextItem = 0;
    private final StreamPlayer player;
    private volatile PlayEventListener listener;

    private JukeBox(StreamPlayer player) {
       this.player = player;
    }

    public static JukeBox create(StreamPlayer player) {
        JukeBox jukebox = new JukeBox(player);
        player.setSongOverListener(jukebox);
        return jukebox;
    }

    public StreamPlayer getPlayer() {
        return player;
    }

    public void setSongBeginListener(PlayEventListener l) {
        listener = l;
    }

    /**
     * Definitely stops playing.
     *
     * You can only change the list completely so far.
     * @param list
     */
    public void setSongList(List<Resource> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Expect non-null, non-empty list.");
        }
        stop();
        this.list = Collections.unmodifiableList(list);
    }

    /**
     * Sets whether after playing the whole play list we should start from the
     * beginning.
     *
     * @param value
     */
    public void setAutoRewind(boolean value) {
        autoRewind = value;
    }

    /**
     *
     * @return
     */
    public boolean isAutoRewind() {
        return autoRewind;
    }

    /**
     * Sets whether after a song we should continue with the next song.
     *
     * @param value
     */
    public void setAutoContinue(boolean value) {
        autoContinue = value;
    }

    /**
     *
     * @return
     */
    public boolean isAutoContinue() {
        return autoContinue;
    }

    /**
     * The song is over.
     *
     * @param event
     */
    @Override
    public void newEvent(String event) {
        if (autoContinue && nextItem < list.size() - 1) {
            nextItem++;
        }
        if (autoContinue && autoRewind && nextItem == list.size() - 1) {
            nextItem = 0;
        }
        if (autoContinue) {
            play();
        }

    }

    /**
     * Plays the next song in the list. If we have stopped or at the beginning,
     * the first song will be played.
     */
    public void play() {
        play(nextItem);
    }

    /**
     * Immediately jumps to another song in the index.
     *
     * @param index
     */
    public void play(int index) {
        if (index < 0 || index >= list.size()) {
            throw new IllegalArgumentException("index out of bounds.");
        }
        AudioFileFormat fmt = null;
        try {
            fmt = AudioSystem.getAudioFileFormat(list.get(index).getInputStream());
        } catch (UnsupportedAudioFileException |IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        AudioInputStream is = SoundSystem.getAudioInputStream(list.get(index));
        if (player.isPlaying()) {
            player.stop();
        }
        // System.out.println("Playing " + list.get(index).getPath());
        if (listener != null) {
            String title = (String) fmt.properties().get("title");
            listener.newEvent(title + " now playing.");
        }
        player.play(is);
    }

    /**
     * Stops playing.
     */
    public void stop() {
        player.stop();
        nextItem = 0;
    }
}
