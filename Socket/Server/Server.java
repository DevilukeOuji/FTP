import java.io.*;
import java.net.*;
import java.util.*;

class Server{
    ServerSocket server = null;
	Socket client = null;
	public static void main(String[] arg)
	{
		Server s = new Server();
		s.connect();
	}
	public void connect(){
		
		try{
            int serverPort =	7896;
			server = new ServerSocket(serverPort);
			while(true)
			{
			  client = server.accept();
			  ClientThread ct = new ClientThread(client);
			  ct.start();
			}
		}
		catch(Exception e)
		{
		}
	}
}

class ClientThread extends Thread{
    public Socket client = null;
	public DataInputStream data_input = null;
	public DataOutputStream data_output = null;
	public FileInputStream file_input = null;
	public FileOutputStream file_output = null;
	public BufferedReader buffer = null;
	public String inputFromUser = "";

	public ClientThread(Socket c)
	{	
        try{
		    client = c;
	        data_input =new DataInputStream(c.getInputStream());
		    data_output = new DataOutputStream(c.getOutputStream());

		}
		catch(Exception e)
		{
			
		}
	}
	public void run()
	{ 
		while(true){
            try{
                String input = data_input.readUTF();
                String file_name = "",file_data ="", folder_name="";
                byte[] data;
				File file;
                if(input.equals("SEND_FILE"))
                {
                    file_name = data_input.readUTF();
                    file_data = data_input.readUTF();
                    file_output = new FileOutputStream("./Files/"+file_name);
                    file_output.write(file_data.getBytes());
                    file_output.close();
                }
                else if(input.equals("RECEIVE_FILE"))
                {
			
                    file_name = "./Files/"+data_input.readUTF();
                    file = new File(file_name);
                    if(file.exists())
			        {
                        file_input = new FileInputStream(file);
                        data = new byte[file_input.available()];
						file_input.read(data);
                        file_input.close();
                        file_data = new String(data);
                        data_output.writeUTF(file_data);
						System.out.println("File Send Successful!");		
                    }
					else
					{
						System.out.println("no");
						data_output.writeUTF("NO_FILE");
					}
                }
				else if(input.equals("LIST_FILES"))
                {
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
					if(parent.exists())
			        {
                        data_output.writeUTF(files_list_str);
						System.out.println("List Sent!");		
                    }
					else
					{
						System.out.println("no");
						data_output.writeUTF("NO_FILE");
					}
				}
                else
                {
                    System.out.println("Erro no servidor");
                }
            }
            catch(Exception e)
            {

            }	
	    }
	}
}