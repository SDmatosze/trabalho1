package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.rmi.* ;

//argumentos : localhost /src /policy.all

public class GetFileData {
    
    public static void main(String[] args) {
        if( args.length != 3) {
        	System.out.println( "Use: java GetFileInfo server_host path name");
        	System.exit(0);
        }
        String serverHost = args[0];
        String path = args[1];
        String name = args[2];
    	
		try {
			IFileServer server = (IFileServer) Naming.lookup("//" + serverHost + "/myFileServer");
			
			byte[] buff = new byte[(int)server.getFile(path, name).length];
			buff = server.getFile(path, name);
			
			//FileInfo info = server.getFileInfo(path, name);
			File f = new File("teste1");
			f.setWritable(true)	;
			FileOutputStream fis = new FileOutputStream(f);
			fis.write(buff);
			fis.close();
			
			
			
	        System.out.println("acabei");
		} catch( Exception e) {
			System.err.println( "Erro: " + e.getMessage());
		}
    }
}
