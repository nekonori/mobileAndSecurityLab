import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AffineClient {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 4000);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Scanner sc = new Scanner(System.in);
        String ipStr = "", opStr = "";
        while (!opStr.equals("stop")) {
            System.out.print(">>>");
            opStr = sc.nextLine();
            out.println(encryptMessage(opStr.toCharArray()));
        }
        s.close();
        sc.close();
        in.close();
        out.close();
    }

    static int a = 17;
    static int b = 20;

    static String encryptMessage(char[] msg) {
        StringBuilder cipher = new StringBuilder();
        for (char c : msg) {
            if (c == ' '){
                cipher.append(c);
                continue;
            }
            cipher.append((char) ((((a * (c - 'A')) + b) % 26) + 'A'));
        }
        return cipher.toString();
    }
}
