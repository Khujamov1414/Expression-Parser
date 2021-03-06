package expression.parser;

public class Pair<T, T1> {
    private T key;
    private T1 value;

    public Pair(T key, T1 value) {
        this.key = key;
        this.value = value;
    }

    public T1 getValue() {
        return value;
    }

    public T getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }
}
