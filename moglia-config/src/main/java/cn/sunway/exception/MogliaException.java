package cn.sunway.exception;

/**
 * Created by SUNWEI on 2018/1/14.
 */
public class MogliaException extends Exception{
    public MogliaException() {
    }

    public MogliaException(String message) {
        super(message);
    }

    public MogliaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MogliaException(Throwable cause) {
        super(cause);
    }
}
