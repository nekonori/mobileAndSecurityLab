import java.util.Arrays;

public class HillAttack {
    public static void main(String[] args) {
        attackHillCipher("MAA", "CAT");
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

    static void attackHillCipher(String cipherText, String realPlainText) {

        int stringLength = 9;
        char[] charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        int totalCombinations = (int) Math.pow(charset.length, stringLength);

        for (int curComb = 0; curComb < totalCombinations; curComb++) {
            StringBuilder sb = new StringBuilder();
            int index = curComb;

            for (int j = 0; j < stringLength; j++) {
                sb.append(charset[index % charset.length]);
                index /= charset.length;
            }

            System.out.println("Trying key: " + sb.toString());

            int[][] keyMatrix = new int[3][3];
            getKeyMatrix(sb.toString(), keyMatrix);

            int blockSize = 3;
            int no_of_blocks = realPlainText.length() / blockSize;
            if (realPlainText.length() % 3 > 0)
                no_of_blocks++;

            int[][][] messageVector = new int[no_of_blocks][3][1];

            for (int block = 0; block < no_of_blocks; block++) {
                for (int i = 0; i < blockSize; i++) {
                    int curIndex = i + block * blockSize;
                    int c;
                    if (curIndex < realPlainText.length())
                        c = (realPlainText.charAt(curIndex)) % 65;
                    else
                        c = '/';
                    messageVector[block][i][0] = c;
                }
            }

            int[][] cipherMatrix = new int[3][1];

            StringBuilder res = new StringBuilder();

            for (int i = 0; i < no_of_blocks; i++) {
                encrypt(cipherMatrix, keyMatrix, messageVector[i]);
                for (int j = 0; j < 3; j++)
                    res.append((char) (cipherMatrix[j][0] + 65));
            }

            if (res.toString().equals(cipherText)) {
                System.out.println("Found key: " + sb.toString());
                System.out.println("plainText: " + realPlainText);
                System.out.println("cipherText: " + res.toString());
                return;
            }
        }
    }
}
