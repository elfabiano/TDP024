package tdp024.util.logger;

public interface TodoLogger {
    
    enum TodoLoggerLevel {
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        CRITICAL
    }
    
    public void log(Throwable throwable);
    
    public void log(TodoLoggerLevel todoLoggerLevel, String shortMessage, String longMessage);
    
}
