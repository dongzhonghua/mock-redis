package top.dzhh.datatype;

/**
 * @author dongzhonghua
 * Created on 2021-11-28
 */
public interface RedisData {

    long timeout();

    void setTimeout(long timeout);
}
