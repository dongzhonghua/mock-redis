package top.dzhh.base;

/**
 * @author dongzhonghua
 * Created on 2021-12-31
 */
public class DzhSkipList<T> {
    public DzhSkipList() {

    }

    public boolean search(int target) {
        return false;
    }

    public void add(int num) {

    }

    public boolean erase(int num) {
        return false;
    }
}

class SkipNode<E> {
    int key;
    E value;
    SkipNode<E> right,down;


    public SkipNode(int key, E value) {
        this.key = key;
        this.value = value;
    }
}