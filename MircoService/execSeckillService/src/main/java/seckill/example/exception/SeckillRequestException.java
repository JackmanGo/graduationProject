package seckill.example.exception;

/**
 * Created by wang on 17-6-23.
 */
public class SeckillRequestException extends SeckillException {
    public SeckillRequestException(String message) {
        super(message);
    }

    public SeckillRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
