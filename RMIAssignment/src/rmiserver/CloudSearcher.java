/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Receive search queries, initiate ranking, and send results to client.
 * @author Jason
 */
public class CloudSearcher {

    private ArrayList<String> query;
    private long searchTime;
    private  String searchResultForClient = null;
    public static String indexFileLocation = ".." + File.separator + "index";

    //Name of the index file
    public static String indexFileName = "Index.txt";

    public String searchTermInIndex(ArrayList<String> query){
        for(String searchName: query){
            File file = new File(indexFileLocation + File.separator + indexFileName);

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String st;
                while ((st = br.readLine()) != null) {

                String [] spiltWords =  st.split("\\|.\\|");
                  if(spiltWords[0].equals(searchName)){
                      System.out.println("Match Find on Index File!");
                      System.out.println("Sending following result to the client side:");
                      System.out.println(st);
                      searchResultForClient = st;
                  }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return searchResultForClient;
    }
}



