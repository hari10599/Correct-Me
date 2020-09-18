package Tries;
import java.util.*;
import java.io.*;

public class Trie {
    private Node root;
    private int wordCount;
    private int nodeCount;
    public Trie() {
        this.root = new Node();
        this.wordCount = 0;
        this.nodeCount = 1;
    }

    public Node getRoot() {
        return this.root;
    }


    public void populate(String filePath) {
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String word = reader.readLine();
            while(word != null) {
                String s[] = word.split(" ");
                this.add(s[0], Integer.valueOf(s[1]));
                word = reader.readLine();
            }
            reader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void add(String word, int frequency) {
        Node curr = root;
        for(char c: word.toCharArray()) {
            int index = c - 'a';
            if(curr.getNode(index) == null) {
                curr.setNode(index);
                this.nodeCount++;
            }
            curr = curr.getNode(index);
        }
        curr.setEnd(true);
        this.wordCount++;
        curr.setFrequency(frequency);
        curr.setWord(word);
    }

    public boolean search(String word) {
        Node curr = root;
        for(char c: word.toCharArray()) {
            int index = c - 'a';
            if(curr.getNode(index) == null)
                return false;
            curr = curr.getNode(index);
        }
        return curr.getEnd();
    }


    public List<String> findPrefix(String word) {
        Node curr = root;
        List<String> result = new ArrayList<>();
        for(char c: word.toCharArray()) {
            int index = c - 'a';
            if(curr.getNode(index) == null)
                return result;
            curr = curr.getNode(index);
        }
        findPrefixRecurse(curr, result);
        return result;
    }

    private void findPrefixRecurse(Node curr, List<String> result) {
        if(curr.getEnd()) {
            result.add(curr.getWord() +" "+curr.getFrequency());
        }
        for(int i = 0; i < 26; i++) {
            if(curr.getNode(i) != null)
                findPrefixRecurse(curr.getNode(i), result);
        }
    }

    private boolean isEmpty(Node curr) {
        for(int i = 0; i < 26; i++) {
            if(curr.getNode(i) != null)
                return false;
        }
        return true;
    }

    public void remove(String s) {
        remove(s, root, 0);
    }

    private Node remove(String s, Node curr, int i) {
        if(curr == null || s.length() == 0) {
            return null;
        }
        if(i == s.length()) {
            if(curr.getEnd())
                curr.setEnd(false);
            return isEmpty(curr)? null : curr;
        }
        int index = s.charAt(i) - 'a';
        curr.setNode(index, remove(s, curr.getNode(index), i + 1));
        if(isEmpty(curr) && !curr.getEnd()) {
            return null;
        }
        return curr;
    }

}
