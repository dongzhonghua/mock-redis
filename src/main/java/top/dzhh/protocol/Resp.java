package top.dzhh.protocol;

/**
 * @author dongzhonghua
 * Created on 2021-11-26
 */
public class Resp<T> {
    protected T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
