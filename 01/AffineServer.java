/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author student
 */
public class AffineServer {
    static int a = 17;
    static int b = 20;
         public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ServerSocket ss = new ServerSocket(4000);
        Socket s = ss.accept();
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String ipStr = "", opStr = "";
        while (!ipStr.equals("stop")) {
            ipStr = in.readLine();
            System.out.println("Message from client\t-->" + decryptCipher(ipStr));
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
