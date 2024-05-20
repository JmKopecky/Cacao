package dev.prognitio.cacao.log;

public enum LogType {

    DEBUG("DEBUG", "general"),
    WARN("WARNING", "general"),
    ERROR("ERROR", "general"),
    FATAL("FATAL", "general"),
    DEBUG_NET("DEBUG", "networking"),
    WARN_NET("WARNING", "networking"),
    ERROR_NET("ERROR", "networking"),
    FATAL_NET("FATAL", "networking"),
    DEBUG_FORMAT("DEBUG", "format"),
    WARN_FORMAT("WARNING", "format"),
    ERROR_FORMAT("ERROR", "format"),
    FATAL_FORMAT("FATAL", "format");


    private String logTypePriority;
    private String logTag;
    LogType(String logTypePriority, String logTag) {
        this.logTypePriority = logTypePriority;
        this.logTag = logTag;
    }
    public String getLogTag() {
        return logTag;
    }
    public String getLogTypePriority() {return logTypePriority;
    }
}
