import java.util.Scanner;

public class Caesar {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the word: ");
        String word = in.next();
        System.out.print("Enter key: ");
        int key = in.nextInt();
        String e = encrypt(word, key);
        String d = decrypt(e, key);
        System.out.println("Original string: " + word);
        System.out.println("Encrypted string: " + e);
        System.out.println("Decrypted string: " + d);
    }

    private static String decrypt(String word, int key) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int j = c - 'a';
            if (j - key < 0) {
                c = (char) (((j - key) + 26) % 26 + 97);
            } else {
                c = (char) ((j - key) % 26 + 97);
            }
            s.append(c);
        }
        return s.toString();
    }

    private static String encrypt(String word, int key) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int j = c - 'a';
            c = (char) ((j + key) % 26 + 97);
            s.append(c);
        }
        return s.toString();
    }
}
