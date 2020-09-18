import Tries.*;
import java.util.*;
import java.io.*;
public class CorrectMe {
    private int maxLevDistance;
    private int capacity;
    private List<List<Integer>> distanceMatrix;
    private Trie trie;
    private String path;

    CorrectMe(int maxLevDistance) {
        this.maxLevDistance = maxLevDistance;
        this.trie = new Trie();
        this.capacity = 5;
        this.distanceMatrix = new ArrayList<>();
    }

    public void populate(String path) {
        this.path = path;
        this.trie.populate(path);
    }

    public void setMaxLevDistance(int d) {
        this.maxLevDistance = d;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean searchTrie(String s) {
        return this.trie.search(s);
    }

    public List<String> findPrefix(String s) {
        return this.trie.findPrefix(s);
    }

    public void removeWord(String s) {
        this.trie.remove(s);
    }


    private void addRow(char c, String target) {
        List<Integer> temp = new ArrayList<>(target.length()+1);
        int size = distanceMatrix.size();
        temp.add(size);
        for(int i = 1; i <= target.length(); i++) {
            if(c == target.charAt(i - 1)) {
                temp.add(distanceMatrix.get(size-1).get(i-1));
            }
            else {
                int insertion = temp.get(i-1);
                int deletion = distanceMatrix.get(size-1).get(i);
                int replace = distanceMatrix.get(size-1).get(i-1);
                temp.add(min(insertion, deletion, replace)+1);
            }
        }
        distanceMatrix.add(temp);
    }

    private void removeRow() {
        if(this.distanceMatrix.size() == 1)
            return;
        this.distanceMatrix.remove(this.distanceMatrix.size()-1);
    }

    private int min(int i, int j, int k) {
        return Math.min(i, Math.min(j, k));
    }

    private int getLevDistance(String target) {
        return this.distanceMatrix.get(distanceMatrix.size()-1).get(target.length());
    }

    public void search(String target) {
        target = target.toLowerCase();
        List<Integer> temp = new ArrayList<>(target.length()+1);
        for(int i = 0; i <= target.length(); i++) {
            temp.add(i);
        }
        distanceMatrix.add(temp);
        Queue<WordFrequency> result = new PriorityQueue<>((a, b) -> a.getFrequency() - b.getFrequency());
        Node curr = this.trie.getRoot();
        searchSimilar(curr, target, result);
        System.out.println("SIMILAR WORDS"+" , "+"FREQUENCY");
        Stack<String> stack = new Stack<>();
        while(!result.isEmpty()) {
            WordFrequency i = result.remove();
            stack.push(i.getWord()+" , "+i.getFrequency());
        }
        while(!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

        List<String> writeToFile = this.trie.findPrefix("");
        try {
            FileWriter writer = new FileWriter(this.path);
            for(String str: writeToFile) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }



    private void searchSimilar(Node curr, String target, Queue<WordFrequency> result) {
        if(curr == null)
            return;
        if(curr.getEnd()) {
            if(getLevDistance(target) <= this.maxLevDistance) {
                curr.incrementFrequency();
                result.add(new WordFrequency(curr.getWord(), curr.getFrequency()));
                if(result.size() > this.capacity)
                    result.remove();
            }
            else if((curr.getWord().length()+this.maxLevDistance) > target.length())
                return;
        }
        for(int i = 0; i < 26; i++) {
            Node node = curr.getNode(i);
            if(node != null) {
                char c = (char)(97 + i);
                addRow(c, target);
                searchSimilar(node, target, result);
                removeRow();

            }
        }
    }

}
