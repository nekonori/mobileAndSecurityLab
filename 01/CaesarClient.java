import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CaesarClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 4000);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Scanner sc = new Scanner(System.in);
        String ipStr = "", opStr = "";
        while (!opStr.equals("stop")) {
            System.out.print(">>>");
            opStr = sc.nextLine();
            out.println(encryptMessage(opStr, 6));
        }
        s.close();
        sc.close();
        in.close();
        out.close();
    }

    private static String encryptMessage(String word, int key) {
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
