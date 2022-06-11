package top.dzhh.protocol;

/**
 * @author dongzhonghua
 * Created on 2021-11-26
 */
public class RespData<T> {
    protected T value;

    public T getValue() {
        return value;
    }

    public RespData<T> setValue(T value) {
        this.value = value;
        return this;
    }
}
