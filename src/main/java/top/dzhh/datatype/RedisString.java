package top.dzhh.datatype;

/**
 * @author dongzhonghua
 * Created on 2021-11-28
 */
public class RedisString implements RedisData {
    private volatile long timeout;

    private String value;

    public RedisString(String value) {
        this.value = value;
        this.timeout = -1;
    }

    public RedisString() {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public long timeout() {
        return timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
