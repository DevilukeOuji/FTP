import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FTPImplementation extends UnicastRemoteObject implements FTP{

    public BufferedReader buffer = null;
    

    protected FTPImplementation() throws RemoteException {
        super();
    }

    public void Upload(byte[] mydata, String serverpath, int length) throws RemoteException {
			
    	try {
    		File serverpathfile = new File(serverpath);
    		FileOutputStream out=new FileOutputStream(serverpathfile);
    		byte [] data=mydata;
			
    		out.write(data);
			out.flush();
	    	out.close();
	 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
		System.out.println("Upload Concluido");
		
	}

	public byte[] Download(String serverpath) throws RemoteException{
		
		byte[] data;
		File server_file = new File(serverpath);
		if(server_file.exists()){
			data = new byte[(int) server_file.length()];
			FileInputStream input_stream;

			try{

				input_stream = new FileInputStream(server_file);	
				input_stream.read(data, 0, data.length);
				input_stream.close();

			} catch (IOException e) {
				
				e.printStackTrace();
			}

			return data;
		}
		else{
			System.out.println("Arquivo inexistente");
			return null;
		}
	}

	public String ListFiles() throws RemoteException{
		String files_list_str = "";
		String files_path = "./Files/";
		System.out.println(files_path);
		File parent = new File(files_path);
		File[] files_list = parent.listFiles();
		
		for (int i = 0; i < files_list.length; i++) {
			if (files_list [i].isFile()) {
				files_list_str = files_list_str.concat("File " + files_list [i].getName() + "\n");
			} else if (files_list [i].isDirectory()) {
				files_list_str = files_list_str.concat("Directory " + files_list [i].getName() + "\n");
			}
		}

		return files_list_str;
	}

	public boolean Delete(String serverpath) throws RemoteException{
		
		File server_file = new File(serverpath);
		if(server_file.exists()){
			return server_file.delete();
		}
		else{
			System.out.println("Arquivo inexistente");
			return false;
		}
	}
}