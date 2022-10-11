import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

    public Socket client = null;
	public DataInputStream data_input = null;
	public DataOutputStream data_output = null;
	public FileInputStream file_input = null;
	public FileOutputStream file_output = null;
	public BufferedReader buffer = null;
	public String inputFromUser = "";

    public static void main(String[] args)
	{
		Client client = new Client();
		client.connect(args[0]);	
	}

    public void connect(String host) 
	{
		try{
		  InputStreamReader input_stream = new InputStreamReader(System.in);
		  buffer = new BufferedReader(input_stream);
          int serverPort =	7896;
		  client = new Socket(host, serverPort);
          System.out.println("Conectado");
		  data_input = new DataInputStream(client.getInputStream());
		  data_output = new DataOutputStream(client.getOutputStream());
		  
		}
		catch(Exception e)
		{
		  	System.out.println("Não foi possível se conectar ao servidor");
		}
		
		while(true)
		{
            try{
                System.out.println("Menu : \n1 Upload \n2 Download \n3 Listar arquivos\nEscolha: ");
                inputFromUser = buffer.readLine();
                int i = Integer.parseInt(inputFromUser);
                switch(i)
                {
                    case 1: sendFile(); break;
                    case 2: receiveFile(); break;
                    case 3: listFiles(); break;
                    default: System.out.println("Õpção inválida !");
                } 
            }catch(Exception e)
            {
                System.out.println("Ocorreu um erro!");
            }
		}
	}

    public void sendFile(){
        try{
            String file_name="",file_data="";		
            File file;
            byte[] data;
            System.out.println("Insira o nome do arquivo: ");
            file_name = buffer.readLine();
            file = new File(file_name);
            if(file.isFile())
			{	
				file_input = new FileInputStream(file);
				data = new byte[file_input.available()];
				file_input.read(data);
				file_input.close();
				file_data = new String(data);
				data_output.writeUTF("SEND_FILE");
				data_output.writeUTF(file_name);		
				data_output.writeUTF(file_data);
				System.out.println("Arquivo enviado");

			}
			else
			{
				System.out.println("Arquivo nao encontrado!");
			}
        }catch(Exception e)
		{
			System.out.println("Ocorreu um erro!");
		}
    }

    public void receiveFile(){
        try
        {
            String file_name="",file_data="";	
            System.out.println("Insira o nome do arquivo: ");
            file_name = buffer.readLine();
            data_output.writeUTF("RECEIVE_FILE");
			data_output.writeUTF(file_name);
            file_data = data_input.readUTF();
            if(file_data.equals("NO_FILE"))
            {
                System.out.println("Arquivo nao encontrado!");
            }
            else
            {
                file_output = new FileOutputStream(file_name);
                file_output.write(file_data.getBytes());
                file_output.close();
            }
            
        }catch(Exception e)
		{
			System.out.println("Ocorreu um erro!");
		}
    }   

    public void listFiles(){
        try
        {
            String file_data="";
            data_output.writeUTF("LIST_FILES");
            file_data = data_input.readUTF();
            System.out.println(file_data);

        }catch(Exception e)
		{
			System.out.println("Ocorreu um erro!");
		}
        
    }
}