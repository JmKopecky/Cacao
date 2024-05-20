package dev.prognitio.cacao.log;

import android.util.Log;

import androidx.annotation.Nullable;

public class Logger {

    public static void log(String toLog, LogType type, @Nullable String tag) {
        String logTag;
        if (tag == null) {
            logTag = type.getLogTag();
        } else {
            logTag = tag;
        }
        switch (type.getLogTypePriority()) {
            case ("DEBUG"): {
                Log.d(logTag, toLog);
                break;
            }
            case ("WARNING"): {
                Log.w(logTag, toLog);
                break;
            }
            case ("ERROR"): {
                Log.e(logTag, toLog);
                break;
            }
            case ("FATAL"): {
                Log.wtf(logTag, toLog);
                break;
            }
            default: {
                Log.w(logTag, "Could not find log type for log message: " + toLog);
            }
        }
    }
}
