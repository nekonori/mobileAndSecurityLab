import java.util.*;
import java.io.*;
import java.net.*;

public class HillServer {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ServerSocket ss = new ServerSocket(4000);
        Socket s = ss.accept();
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String ipStr = "";
        while (!ipStr.equals("stop")) {
            ipStr = in.readLine();
            int stringLength = Integer.parseInt(in.readLine());
            System.out.println("Message from client: " + ipStr);
            System.out.println("Decrypted message: \t" + HillCipher(ipStr, "GYBNQKURP").substring(0, stringLength));
        }
        in.close();
        out.close();
        s.close();
        ss.close();
        sc.close();
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

    static void decrypt(int ptMatrix[][],
            int inverseKeyMatrix[][],
            int messageVector[][]) {
        int x, i, j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 1; j++) {
                ptMatrix[i][j] = 0;
                for (x = 0; x < 3; x++) {
                    ptMatrix[i][j] += inverseKeyMatrix[i][x] * messageVector[x][j];
                }

                ptMatrix[i][j] = ptMatrix[i][j] % 26;
            }
        }
    }

    static void getCofactor(int A[][], int temp[][], int p, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = A[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    static int determinant(int A[][], int n) {
        int D = 0;

        if (n == 1)
            return A[0][0];

        int[][] temp = new int[3][3];

        int sign = 1;

        for (int f = 0; f < n; f++) {
            getCofactor(A, temp, 0, f, n);
            D += sign * A[0][f] * determinant(temp, n - 1);
            sign = -sign;
        }

        return D;
    }

    static void adjoint(int A[][], int[][] adj) {
        int sign = 1;
        int[][] temp = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getCofactor(A, temp, i, j, 3);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign) * (determinant(temp, 3 - 1));
            }
        }
    }

    static void getInverseMatrix(int[][] keyMatrix, int[][] inverseMatrix) {
        adjoint(keyMatrix, inverseMatrix);
    }

    static String HillCipher(String message, String key) {
        int[][] keyMatrix = new int[3][3];
        getKeyMatrix(key, keyMatrix);

        int[][] inverseMatrix = new int[3][3];
        getInverseMatrix(keyMatrix, inverseMatrix);

        int det = determinant(keyMatrix, 3) % 26;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverseMatrix[i][j] = (inverseMatrix[i][j] * det) % 26;
                if (inverseMatrix[i][j] < 0)
                    inverseMatrix[i][j] += 26;
            }
        }

        int blockSize = 3;
        int no_of_blocks = message.length() / blockSize;
        if(message.length() % 3 > 0) no_of_blocks++;

        int[][][] cipherMatrix = new int[no_of_blocks][3][1];

        StringBuilder plainText = new StringBuilder();

        for(int block=0;block<no_of_blocks;block++) {
            for (int i = 0; i < blockSize; i++) {
                int curIndex = i + block * blockSize;
                int c;
                if(curIndex < message.length())
                    c = (message.charAt(curIndex)) % 65;
                else
                    c = ' ';
                cipherMatrix[block][i][0] = c;
            }

            int[][] ptMatrix = new int[3][1];

            decrypt(ptMatrix, inverseMatrix, cipherMatrix[block]);

            for (int i = 0; i < ptMatrix.length; i++)
                ptMatrix[i][0] %= 26;

            for (int i = 0; i < 3; i++)
                plainText.append((char) (ptMatrix[i][0] + 65));
        }
        return plainText.toString();
    }
}
