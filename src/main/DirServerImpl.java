package main;
//ISTO � O PRIMEIRO COMENT�RIO COMMITED!
//e este � o segundo...
//quem manda aqui sou eu
//sou o rei
//o z� � gay

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.io.*;

public class DirServerImpl
		extends UnicastRemoteObject
		implements IFileServer
{
	protected DirServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	private String basePathName;
	private File basePath;

	protected DirServerImpl( String pathname) throws RemoteException {
		super();
		this.basePathName = pathname;
		basePath = new File( pathname);
	}

	@Override
	public String[] dir(String path) throws RemoteException, InfoNotFoundException {
		File f = new File( basePath, path);
		if( f.exists())
			return f.list();
		else
			throw new InfoNotFoundException( "Directory not found :" + path);
	}
	
	
	public byte[] getFile(String path, String name) throws IOException   {
		
		//Ter acesso ao ficheiro:
		RandomAccessFile rAf = new RandomAccessFile( basePath + "/" +path + "/" +name, "r");
		byte[] b  = new byte[(int) rAf.length()];
		rAf.readFully(b); //colocar o ficheiro na variavel b
		rAf.close();
		return b;
		
	}
	

	@Override
	public FileInfo getFileInfo(String path, String name) throws RemoteException, InfoNotFoundException {
		File dir = new File( basePath, path);
		if( dir.exists()) {
			File f = new File( dir, name);
			if( f.exists())
				return new FileInfo( path, f.length(), new Date(f.lastModified()), f.isFile());
			else
				throw new InfoNotFoundException( "File not found :" + name);
		} else
			throw new InfoNotFoundException( "Directory not found :" + path);
	}

	public static void main( String args[]) throws Exception {
		try {
			String path = ".";
			if( args.length > 0)
				path = args[0];

			System.getProperties().put( "java.security.policy", "src/policy.all");

			if( System.getSecurityManager() == null) {
				System.setSecurityManager( new RMISecurityManager());
			}

			try { // start rmiregistry
				LocateRegistry.createRegistry( 1099);
			} catch( RemoteException e) { 
				// if not start it
				// do nothing - already started with rmiregistry
			}

			DirServerImpl server = new DirServerImpl( path);
			Naming.rebind( "/myFileServer", server);
			System.out.println( "DirServer bound in registry");
		} catch( Throwable th) {
			th.printStackTrace();
		}
	}


}
