package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MojBrojClient {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", MojBrojServer.PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            Scanner sc = new Scanner(System.in);

            int niz[]=new int[6];
            System.out.println("Unesite ciljni broj: ");
            int goal = sc.nextInt();

            System.out.println("Unesite prvi broj: ");
            niz[0]=sc.nextInt();
            System.out.println("Unesite drugi broj: ");
            niz[1]=sc.nextInt();
            System.out.println("Unesite treci broj: ");
            niz[2]=sc.nextInt();
            System.out.println("Unesite cetvrti broj: ");
            niz[3]=sc.nextInt();
            System.out.println("Unesite peti broj: ");
            niz[4]=sc.nextInt();
            System.out.println("Unesite sesti broj: ");
            niz[5]=sc.nextInt();

            StringBuilder sb = new StringBuilder();
            sb.append(""+goal);
            for (int i =0;i<6;i++){
                sb.append(" "+niz[i]);
            }
            String message = sb.toString();
            out.write(message);
            out.newLine();
            out.flush();

            String result = in.readLine();
            System.out.println(result);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
