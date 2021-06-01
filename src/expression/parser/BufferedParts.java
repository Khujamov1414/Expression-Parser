package expression.parser;

import java.util.List;

public class BufferedParts {
    private List<Pair<Parts, String>> list;
    private int pos = 0;

    public BufferedParts(List<Pair<Parts, String> > list) {
        this.list = list;
    }

    public Pair<Parts, String> getNext() {
        return list.get(pos++);
    }

    public void goBack() {
        pos--;
    }

    public Pair<Parts, String> getPrevious() {
        return list.get(pos - 2);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
