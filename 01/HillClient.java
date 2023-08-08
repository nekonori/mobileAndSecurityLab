package javaapplication11;

import java.io.*;
import java.net.*;
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
            ipStr = in.readLine();
            System.out.println("Message from server-->" + ipStr);
        }
        s.close();
        sc.close();
        in.close();
        out.close();
    }
    
    static void getKeyMatrix(String key, int keyMatrix[][])
{
	int k = 0;
	for (int i = 0; i < 3; i++)
	{
            for (int j = 0; j < 3; j++)
            {
                    keyMatrix[i][j] = (key.charAt(k)) % 65;
                    k++;
            }
	}
}

static void encrypt(int cipherMatrix[][],
			int keyMatrix[][],
			int messageVector[][])
{
	int x, i, j;
	for (i = 0; i < 3; i++)
	{
            for (j = 0; j < 1; j++)
            {
                cipherMatrix[i][j] = 0;
                for (x = 0; x < 3; x++)
                {
                    cipherMatrix[i][j] +=
                            keyMatrix[i][x] * messageVector[x][j];
                }

                cipherMatrix[i][j] = cipherMatrix[i][j] % 26;
            }
	}
}

// Function to implement Hill Cipher
static String HillCipher(String message, String key)
{
	// Get key matrix from the key string
	int [][]keyMatrix = new int[3][3];
	getKeyMatrix(key, keyMatrix);

	int [][]messageVector = new int[3][1];

	// Generate vector for the message
	for (int i = 0; i < 3; i++)
		messageVector[i][0] = (message.charAt(i)) % 65;

	int [][]cipherMatrix = new int[3][1];

	// Following function generates
	// the encrypted vector
	encrypt(cipherMatrix, keyMatrix, messageVector);
	
	int[][] inverseMatrix = new int[3][3];
	
	String CipherText="";

	// Generate the encrypted text from
	// the encrypted vector
	for (int i = 0; i < 3; i++)
		CipherText += (char)(cipherMatrix[i][0] + 65);

	return CipherText;
}
    
}
