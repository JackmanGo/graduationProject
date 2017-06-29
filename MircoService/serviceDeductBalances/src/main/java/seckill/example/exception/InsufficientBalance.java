package seckill.example.exception;

/**
 * Created by wang on 17-6-21.
 * 余额不足的异常类
 */
public class InsufficientBalance extends RuntimeException {
    public InsufficientBalance(String message) {
        super(message);
    }

    public InsufficientBalance(String message, Throwable cause) {
        super(message, cause);
    }
}
