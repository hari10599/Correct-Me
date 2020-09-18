package Tries;

public class Node {
    private Node[] children;
    private boolean end;
    private int frequency;
    private String word;
    public Node() {
        children = new Node[26];
        for(int i = 0; i < 26; i++)
            children[i] = null;
        frequency = 0;
        end = false;
        word = null;
    }

    public Node getNode(int index) {
        return this.children[index];
    }

    public void setNode(int index) {
        this.children[index] = new Node();
    }

    public void setNode(int index, Node n) {
        this.children[index] = n;
    }

    public boolean getEnd() {
        return this.end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void incrementFrequency() {
        this.frequency++;
    }

    public void setFrequency(int f) {
        this.frequency = f;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
