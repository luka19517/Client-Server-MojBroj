package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MojBrojServer {

    public static int PORT = 1235;
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.err.println("Listening for client");

            Socket client = server.accept();

            System.err.println("Client accepted");
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            String userLine = in.readLine();
            String[] tokens = userLine.split(" ");
            int niz[]=new int[6];
            int goal = Integer.parseInt(tokens[0].trim());
            for (int i = 0;i<6;i++){
                niz[i] = Integer.parseInt(tokens[i+1].trim());
            }

            String result = solveAndPrint(niz,goal);
            out.write(result);
            out.newLine();
            out.flush();

            System.err.println("client served");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String solveAndPrint(int[] niz, int goal) {
        Set<Integer> solvedKeys = new HashSet<Integer>();
        HashMap<Integer,Integer> toLeftParent= new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> toRightParent= new HashMap<Integer,Integer>();
        HashMap<Integer,Character> toOperator = new HashMap<Integer, Character>();
        Queue<Integer> red = new ArrayDeque<Integer>();
        Set<Integer> newKeys = new HashSet<Integer>();
        int n = niz.length;

        for(int i = 0 ; i<n;i++){
            int key = (niz[i]<<n)+(1<<i);
            solvedKeys.add(key);
            red.add(key);
        }

        while(!red.isEmpty()){
            int currentKey = red.poll();
            int curValue = currentKey>>n;
            int curMask = currentKey & ((1<<n)-1);

            Iterator<Integer> iterator = newKeys.iterator();
            while(iterator.hasNext()){
                solvedKeys.add(iterator.next());
            }
            newKeys.clear();

            Iterator<Integer> it1 = solvedKeys.iterator();
            while(it1.hasNext()){
                int key = it1.next();
                int mask = key & ((1<<n)-1);
                int value = key>>n;

                if ( (curMask & mask) ==0){
                    int newValue = 0;
                    int newMask = 0;
                    int newKey = 0;
                    char opSign;

                    opSign = '+';
                    newValue = curValue + value;
                    newMask = mask | curMask;
                    newKey = (newValue<<n) + newMask;
                    if (!newKeys.contains(newKey)){
                        newKeys.add(newKey);
                        toLeftParent.put(newKey,currentKey);
                        toRightParent.put(newKey,key);
                        toOperator.put(newKey,opSign);
                        red.add(newKey);
                    }
                    if (newValue == goal){
                        StringBuilder sb = new StringBuilder();
                        sb.append(printExpresion(toLeftParent,toRightParent,toOperator,newKey,n));
                        sb.append("= "+goal);
                        return sb.toString();
                    }

                    opSign = '-';
                    newValue = curValue - value;
                    newMask = mask | curMask;
                    if(newValue>0) {
                        newKey = (newValue << n) + newMask;
                        if (!newKeys.contains(newKey)) {
                            newKeys.add(newKey);
                            toLeftParent.put(newKey, currentKey);
                            toRightParent.put(newKey, key);
                            toOperator.put(newKey, opSign);
                            red.add(newKey);
                        }
                        if (newValue == goal) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(printExpresion(toLeftParent, toRightParent, toOperator, newKey, n));
                            sb.append("= " + goal);
                            return sb.toString();
                        }
                    }

                    opSign = '*';
                    newValue = curValue * value;
                    newMask = mask | curMask;
                    newKey = (newValue<<n) + newMask;
                    if (!newKeys.contains(newKey)){
                        newKeys.add(newKey);
                        toLeftParent.put(newKey,currentKey);
                        toRightParent.put(newKey,key);
                        toOperator.put(newKey,opSign);
                        red.add(newKey);
                    }
                    if (newValue == goal){
                        System.out.println("ima");
                        StringBuilder sb = new StringBuilder();
                        sb.append(printExpresion(toLeftParent,toRightParent,toOperator,newKey,n));
                        sb.append("= "+goal);
                        return sb.toString();
                    }

                    opSign = '/';
                    if(value!= 0 && curValue % value == 0) {
                        newValue = curValue / value;
                        newMask = mask | curMask;
                        newKey = (newValue << n) + newMask;
                        if (!newKeys.contains(newKey)) {
                            newKeys.add(newKey);
                            toLeftParent.put(newKey, currentKey);
                            toRightParent.put(newKey, key);
                            toOperator.put(newKey, opSign);
                            red.add(newKey);
                        }
                        if (newValue == goal) {
                            System.out.println("ima");
                            StringBuilder sb = new StringBuilder();
                            sb.append(printExpresion(toLeftParent, toRightParent, toOperator, newKey, n));
                            sb.append("= " + goal);
                            return sb.toString();
                        }
                    }
                }

            }

        }
        int closest = findClosest(solvedKeys, goal, n);
        StringBuilder sb = new StringBuilder();
        sb.append(printExpresion(toLeftParent, toRightParent, toOperator, closest, n));
        return sb.toString() + "=" + (closest >> n);


    }

    private static int findClosest(Set<Integer> solvedKeys, int goal, int n) {
        Iterator<Integer> iterator = solvedKeys.iterator();
        int closestElement = 0;
        int closestKey = 0;
        while(iterator.hasNext()){
            int key = iterator.next();
            int value = key>>n;
            if(Math.abs(goal-value) < Math.abs(closestElement-goal)) {
                closestElement = value;
                closestKey = key;
            }
        }
        return closestKey;
    }

    private static String printExpresion(HashMap<Integer, Integer> toLeftParent, HashMap<Integer, Integer> toRightParent, HashMap<Integer, Character> toOperator, int newKey, int n) {
        if(!toOperator.containsKey(newKey)){
            return ""+(newKey>>n);
        }
        else{
            return "("+printExpresion(toLeftParent, toRightParent, toOperator, toLeftParent.get(newKey), n) + toOperator.get(newKey)+printExpresion(toLeftParent,toRightParent,toOperator, toRightParent.get(newKey), n)+")";
        }
    }
}
