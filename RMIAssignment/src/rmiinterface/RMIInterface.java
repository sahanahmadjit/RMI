package rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIInterface extends Remote {
	public String searchedKeyWord(ArrayList<String> keyWordsList) throws RemoteException;
}