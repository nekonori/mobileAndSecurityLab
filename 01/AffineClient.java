/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author student
 */
public class AffineClient {
   
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 4000);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Scanner sc = new Scanner(System.in);
        String ipStr = "", opStr = "";
        while (!ipStr.equals("stop")) {
            System.out.print(">>>");
            opStr = sc.nextLine();
            out.println(encryptMessage(opStr.toCharArray()));
            ipStr = in.readLine();
            System.out.println("Message from server-->" + ipStr);
        }
        s.close();
        sc.close();
        in.close();
        out.close();
    }
    
    static int a = 17;
    static int b = 20;

    static String encryptMessage(char[] msg)
    {
        String cipher = "";
        for (int i = 0; i < msg.length; i++)
        {
            if (msg[i] != ' ')
            {
                cipher = cipher
                                    + (char) ((((a * (msg[i] - 'A')) + b) % 26) + 'A');
            } else 
            {
                cipher += msg[i];
            }
        }
        return cipher;
    }

    static String decryptCipher(String cipher)
    {
        String msg = "";
        int a_inv = 0;
        int flag = 0;

        for (int i = 0; i < 26; i++)
        {
            flag = (a * i) % 26;
            if (flag == 1)
            {
                    a_inv = i;
            }
        }
        for (int i = 0; i < cipher.length(); i++)
        {
            if (cipher.charAt(i) != ' ')
            {
                msg = msg + (char) (((a_inv *
                                ((cipher.charAt(i) + 'A' - b)) % 26)) + 'A');
            }
            else
            {
                msg += cipher.charAt(i);
            }
        }

        return msg;
    }
}
