import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CaesarServer {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        ServerSocket ss = new ServerSocket(4000);
        Socket s = ss.accept();
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String ipStr = "", opStr = "";
        while (!ipStr.equals("stop")) {
            ipStr = in.readLine();
            System.out.println("Message from client: " + ipStr);
            System.out.println("Decrypted msg: \t" + decryptCipher(ipStr, 6));
        }
        in.close();
        out.close();
        s.close();
        ss.close();
        sc.close();
    }
    private static String decryptCipher(String word, int key) {
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
