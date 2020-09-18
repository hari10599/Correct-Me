public class Main {
    public static void main(String[] args) {
        int levDistance = 1;
        int capacity = 5;
        String filePath = "/Users/harikrishna/Documents/Projects/Correct Me/Files/dictionaryWithFrequency.txt";
        CorrectMe correctMe = new CorrectMe(levDistance);
        correctMe.setCapacity(capacity);
        correctMe.populate(filePath);
        correctMe.search("Find");

    }
}
