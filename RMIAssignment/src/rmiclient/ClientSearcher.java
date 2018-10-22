/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiclient;

import rmiinterface.RMIInterface;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client Semantic Searcher.
 * Responsible for:
 *  Expanding the query into the modified query set.
 *  Weighting.
 *  Sending the query over to the server.
 * @author jason
 */
public class ClientSearcher {

    String originalQuery;
    ArrayList<String> searhTerm = new ArrayList<String>();
    Socket sock;
    ArrayList<String> searchResults;
    private static RMIInterface look_up;

    /**
     * Constructor.
     * Sets up required objects, maps, and variables.
     * Does not do any of the semantic query modification yet.
     *
     * @param query The original user query
     */
    public ClientSearcher(String query) {
        originalQuery = query.toLowerCase();
        constructQuery();
        searchResults = new ArrayList<>();

    }

    //--------------------QUERY PROCESSING-------------------------

    /**
     * Constructs the query vector to be sent to the server.
     * The entire process builds up the multiple weights.
     * Splits the query into its individual components and weights them.
     * Adds synonyms for each term in the queryWeights map, and weights them.
     * Adds the wikipedia terms for each term in the queryWeights map, and weights them.
     */
    public void constructQuery() {
        // Log time to process the query
        long begin = System.currentTimeMillis();
        splitQuery();
        long end = System.currentTimeMillis();
    }

    /**
     * Splits and weights the original query.
     * Adds terms to the queryWeights, splitting based on config method.
     * Pre: Original query has been set.
     * Post: queryWeights has all required data.
     */
    public void splitQuery() {
        String[] subQueries;
        subQueries = originalQuery.split(" "); //Just split by spaces
        for (String term : subQueries) {
            searhTerm.add(term);
        }


    }


    //---------------------SEARCHING AND RESULTS--------------------

    /**
     * Send Search Query To The Server To Perform Search.
     * Does several things:
     * Consolidates and encrypts the query.
     * Loads in the abstracts to be compared against the query.
     * Ranks the abstracts against the query.
     * Sends the query and abstract choice to the server
     */
    public void search() {

        try {
            look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        String response = null;
        try {
            response = look_up.searchedKeyWord(searhTerm);
            if(response!=null){
                System.out.println("List of files return by server");
                System.out.println(response);
            }
            else{
                System.out.println("no match found out.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }



    }


}