/*
 * Copyright (C) 1999-2002 Matthias Pfisterer
 *               2013 Trilarion
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sound;

import java.security.AccessControlException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jkeller1
 */
public class TDebug {

    private static final Logger LOG = Logger.getLogger(TDebug.class.getName());

    /**
     *
     */
    public static final boolean SHOW_ACCESS_CONTROL_EXCEPTIONS = false;

    private static final String PROPERTY_PREFIX = "tritonus.";
    private static String indent = "";
    // meta-general
    /**
     *
     */
    public static boolean TraceAllExceptions = getBooleanProperty("TraceAllExceptions");
    /**
     *
     */
    public static boolean TraceAllWarnings = getBooleanProperty("TraceAllWarnings");
    // general
    /**
     *
     */
    public static boolean TraceInit = getBooleanProperty("TraceInit");
    /**
     *
     */
    public static boolean TraceCircularBuffer = getBooleanProperty("TraceCircularBuffer");
    /**
     *
     */
    public static boolean TraceService = getBooleanProperty("TraceService");
    // sampled common implementation
    /**
     *
     */
    public static boolean TraceAudioSystem = getBooleanProperty("TraceAudioSystem");
    /**
     *
     */
    public static boolean TraceAudioConfig = getBooleanProperty("TraceAudioConfig");
    /**
     *
     */
    public static boolean TraceAudioInputStream = getBooleanProperty("TraceAudioInputStream");
    /**
     *
     */
    public static boolean TraceMixerProvider = getBooleanProperty("TraceMixerProvider");
    /**
     *
     */
    public static boolean TraceControl = getBooleanProperty("TraceControl");
    /**
     *
     */
    public static boolean TraceLine = getBooleanProperty("TraceLine");
    /**
     *
     */
    public static boolean TraceDataLine = getBooleanProperty("TraceDataLine");
    /**
     *
     */
    public static boolean TraceMixer = getBooleanProperty("TraceMixer");
    /**
     *
     */
    public static boolean TraceSourceDataLine = getBooleanProperty("TraceSourceDataLine");
    /**
     *
     */
    public static boolean TraceTargetDataLine = getBooleanProperty("TraceTargetDataLine");
    /**
     *
     */
    public static boolean TraceClip = getBooleanProperty("TraceClip");
    /**
     *
     */
    public static boolean TraceAudioFileReader = getBooleanProperty("TraceAudioFileReader");
    /**
     *
     */
    public static boolean TraceAudioFileWriter = getBooleanProperty("TraceAudioFileWriter");
    /**
     *
     */
    public static boolean TraceAudioConverter = getBooleanProperty("TraceAudioConverter");
    /**
     *
     */
    public static boolean TraceAudioOutputStream = getBooleanProperty("TraceAudioOutputStream");
    // sampled specific implementation
    /**
     *
     */
    public static boolean TraceEsdNative = getBooleanProperty("TraceEsdNative");
    /**
     *
     */
    public static boolean TraceEsdStreamNative = getBooleanProperty("TraceEsdStreamNative");
    /**
     *
     */
    public static boolean TraceEsdRecordingStreamNative = getBooleanProperty("TraceEsdRecordingStreamNative");
    /**
     *
     */
    public static boolean TraceAlsaNative = getBooleanProperty("TraceAlsaNative");
    /**
     *
     */
    public static boolean TraceAlsaMixerNative = getBooleanProperty("TraceAlsaMixerNative");
    /**
     *
     */
    public static boolean TraceAlsaPcmNative = getBooleanProperty("TraceAlsaPcmNative");
    /**
     *
     */
    public static boolean TraceMixingAudioInputStream = getBooleanProperty("TraceMixingAudioInputStream");
    /**
     *
     */
    public static boolean TraceOggNative = getBooleanProperty("TraceOggNative");
    /**
     *
     */
    public static boolean TraceVorbisNative = getBooleanProperty("TraceVorbisNative");
    // midi common implementation
    /**
     *
     */
    public static boolean TraceMidiSystem = getBooleanProperty("TraceMidiSystem");
    /**
     *
     */
    public static boolean TraceMidiConfig = getBooleanProperty("TraceMidiConfig");
    /**
     *
     */
    public static boolean TraceMidiDeviceProvider = getBooleanProperty("TraceMidiDeviceProvider");
    /**
     *
     */
    public static boolean TraceSequencer = getBooleanProperty("TraceSequencer");
    /**
     *
     */
    public static boolean TraceSynthesizer = getBooleanProperty("TraceSynthesizer");
    /**
     *
     */
    public static boolean TraceMidiDevice = getBooleanProperty("TraceMidiDevice");
    // midi specific implementation
    /**
     *
     */
    public static boolean TraceAlsaSeq = getBooleanProperty("TraceAlsaSeq");
    /**
     *
     */
    public static boolean TraceAlsaSeqDetails = getBooleanProperty("TraceAlsaSeqDetails");
    /**
     *
     */
    public static boolean TraceAlsaSeqNative = getBooleanProperty("TraceAlsaSeqNative");
    /**
     *
     */
    public static boolean TracePortScan = getBooleanProperty("TracePortScan");
    /**
     *
     */
    public static boolean TraceAlsaMidiIn = getBooleanProperty("TraceAlsaMidiIn");
    /**
     *
     */
    public static boolean TraceAlsaMidiOut = getBooleanProperty("TraceAlsaMidiOut");
    /**
     *
     */
    public static boolean TraceAlsaMidiChannel = getBooleanProperty("TraceAlsaMidiChannel");
    /**
     *
     */
    public static boolean TraceFluidNative = getBooleanProperty("TraceFluidNative");
    // misc
    /**
     *
     */
    public static boolean TraceAlsaCtlNative = getBooleanProperty("TraceAlsaCtlNative");
    /**
     *
     */
    public static boolean TraceCdda = getBooleanProperty("TraceCdda");
    /**
     *
     */
    public static boolean TraceCddaNative = getBooleanProperty("TraceCddaNative");

    // make this method configurable to write to file, write to stderr,...
    /**
     *
     * @param strMessage
     */
    public static void out(String strMessage) {
        if (strMessage.length() > 0 && strMessage.charAt(0) == '<') {
            if (indent.length() > 2) {
                indent = indent.substring(2);
            } else {
                indent = "";
            }
        }
        String newMsg;
        if (indent.length() > 0 && strMessage.indexOf('\n') >= 0) {
            newMsg = "";
            StringTokenizer tokenizer = new StringTokenizer(strMessage, "\n");
            while (tokenizer.hasMoreTokens()) {
                newMsg += indent + tokenizer.nextToken() + "\n";
            }
        } else {
            newMsg = indent + strMessage;
        }
        LOG.log(Level.FINE, newMsg);
        if (strMessage.length() > 0 && strMessage.charAt(0) == '>') {
            indent += "  ";
        }
    }

    /**
     *
     * @param throwable
     */
    public static void out(Throwable throwable) {
        LOG.log(Level.FINE, null, throwable);
    }

    /**
     *
     * @param bAssertion
     */
    public static void assertion(boolean bAssertion) {
        if (!bAssertion) {
            throw new AssertException();
        }
    }

    /**
     *
     */
    public static class AssertException
            extends RuntimeException {

        private static final long serialVersionUID = 1;

        /**
         *
         */
        public AssertException() {
        }

        /**
         *
         * @param sMessage
         */
        public AssertException(String sMessage) {
            super(sMessage);
        }
    }

    private static boolean getBooleanProperty(String strName) {
        String strPropertyName = PROPERTY_PREFIX + strName;
        String strValue = "false";
        try {
            strValue = System.getProperty(strPropertyName, "false");
        } catch (AccessControlException e) {
            if (SHOW_ACCESS_CONTROL_EXCEPTIONS) {
                out(e);
            }
        }
        // TDebug.out("property: " + strPropertyName + "=" + strValue);
        boolean bValue = strValue.toLowerCase().equals("true");
        // TDebug.out("bValue: " + bValue);
        return bValue;
    }

    private TDebug() {
    }
}
/**
 * * TDebug.java **
 */
