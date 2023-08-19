import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class HillClient {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 4000);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Scanner sc = new Scanner(System.in);
        String ipStr = "", opStr = "";
        while (!ipStr.equals("stop")) {
            System.out.print(">>>");
            opStr = sc.nextLine();
            out.println(HillCipher(opStr, "GYBNQKURP"));
            out.println(opStr.length());
        }
        s.close();
        sc.close();
        in.close();
        out.close();
    }

    static void getKeyMatrix(String key, int keyMatrix[][]) {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                keyMatrix[i][j] = (key.charAt(k)) % 65;
                k++;
            }
        }
    }

    static void encrypt(int cipherMatrix[][],
            int keyMatrix[][],
            int messageVector[][]) {
        int x, i, j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 1; j++) {
                cipherMatrix[i][j] = 0;
                for (x = 0; x < 3; x++) {
                    cipherMatrix[i][j] += keyMatrix[i][x] * messageVector[x][j];
                }
                cipherMatrix[i][j] = cipherMatrix[i][j] % 26;
            }
        }
    }

    // Function to implement Hill Cipher
    static String HillCipher(String message, String key) {
        int[][] keyMatrix = new int[3][3];
        getKeyMatrix(key, keyMatrix);

        int blockSize = 3;
        int no_of_blocks = message.length() / blockSize;
        if(message.length() % 3 > 0) no_of_blocks++;

        int[][][] messageVector = new int[no_of_blocks][3][1];

        for(int block=0;block<no_of_blocks;block++){
            for (int i = 0; i < blockSize; i++) {
                int curIndex = i + block * blockSize;
                int c;
                if(curIndex < message.length())
                    c = (message.charAt(curIndex)) % 65;
                else
                    c = '/';
                messageVector[block][i][0] = c;
            }
        }

        int[][] cipherMatrix = new int[3][1];

        StringBuilder res = new StringBuilder();

        for(int i=0;i<no_of_blocks;i++) {
            encrypt(cipherMatrix, keyMatrix, messageVector[i]);
            for (int j = 0; j < 3; j++)
                res.append((char) (cipherMatrix[j][0] + 65));
        }

        return res.toString();
    }
}
