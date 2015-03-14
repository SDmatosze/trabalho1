package main;

import java.io.IOException;
import java.rmi.*;
import java.util.*;

public interface IFileServer extends Remote
{
	/**
	 * Lista nome de ficheiros num dado directorio
	 */
	public String[] dir( String path) throws RemoteException, InfoNotFoundException;
	
	/**
	 * Devolve informacao sobre ficheiro.
	 */
	public FileInfo getFileInfo( String path, String name) throws RemoteException, InfoNotFoundException;

	
	/**
	 * @param path
	 * @param name
	 * @return O ficheiro em formato de bytes
	 * @throws IOException
	 */
	public byte[] getFile(String path, String name) throws IOException ;

}
