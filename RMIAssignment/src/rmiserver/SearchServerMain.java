/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import rmiinterface.RMIInterface;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class SearchServerMain extends UnicastRemoteObject implements RMIInterface{

    private static final long serialVersionUID = 1L;

    protected SearchServerMain() throws RemoteException {
        super();
    }
    
    /**
     * @param args Arguments for what mode the server should launch into
     */
    public static void main(String[] args) {
        //Search indefinitely

        System.out.println("Server Side Running: Waiting for Search Term.....");
        try {
            Naming.rebind("//localhost/MyServer", new SearchServerMain());
            System.err.println("Server ready");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public String searchedKeyWord(ArrayList<String> keyWordList) throws RemoteException {
        CloudSearcher searcher = new CloudSearcher();
        return  searcher.searchTermInIndex(keyWordList);
    }
}
