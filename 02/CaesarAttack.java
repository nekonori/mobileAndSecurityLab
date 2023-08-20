import java.util.*;

public class CaesarAttack {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the encrypted string: ");
        String word = in.next();
        for (int i = 0; i < 26; i++) {
            String d = decrypt(word, i);
            System.out.println("Possible plain text: " + d);
        }
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
}