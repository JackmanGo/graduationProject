package seckill.example.exception;

/**
 * Created by wang on 17-6-21.
 * 秒杀相关的所有业务异常父类
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
