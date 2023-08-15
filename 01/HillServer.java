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
        String ipStr = "", opStr = "";
        while (!ipStr.equals("stop")) {
            ipStr = in.readLine();
            System.out.println("Message from client\t-->" + ipStr);
            System.out.println("Decrypted message: " + HillCipher(ipStr, "GYBNQKURP"));
            System.out.print(">>>");
            opStr = sc.nextLine();
            out.println(opStr);
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

        // Looping for each element of the matrix
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // Copying into temporary matrix only those element
                // which are not in given row and column
                if (row != p && col != q) {
                    temp[i][j++] = A[row][col];

                    // Row is filled, so increase row index and
                    // reset col index
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    static int determinant(int A[][], int n) {
        int D = 0; // Initialize result

        // Base case : if matrix contains single element
        if (n == 1)
            return A[0][0];

        int[][] temp = new int[3][3]; // To store cofactors

        int sign = 1; // To store sign multiplier

        // Iterate for each element of first row
        for (int f = 0; f < n; f++) {
            // Getting Cofactor of A[0][f]
            getCofactor(A, temp, 0, f, n);
            D += sign * A[0][f] * determinant(temp, n - 1);

            // terms are to be added with alternate sign
            sign = -sign;
        }

        return D;
    }

    static void adjoint(int A[][], int[][] adj) {
        // temp is used to store cofactors of A[][]
        int sign = 1;
        int[][] temp = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Get cofactor of A[i][j]
                getCofactor(A, temp, i, j, 3);

                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = ((i + j) % 2 == 0) ? 1 : -1;

                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
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

        int[][] cipherMatrix = new int[3][1];

        // Generate vector for the message
        for (int i = 0; i < 3; i++)
            cipherMatrix[i][0] = (message.charAt(i)) % 65;

        int[][] ptMatrix = new int[3][1];

        decrypt(ptMatrix, inverseMatrix, cipherMatrix);

        for(int i=0;i<ptMatrix.length;i++)
            ptMatrix[i][0] %= 26;

        String plainText = "";

        // Generate the encrypted text from
        // the encrypted vector
        for (int i = 0; i < 3; i++)
            plainText += (char) (ptMatrix[i][0] + 65);
        
        return plainText;
    }

}
