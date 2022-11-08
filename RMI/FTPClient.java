import java.rmi.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Scanner;

public class FTPClient {

    String serverpath;

    public static void main(String[] args)
	{
		FTPClient client = new FTPClient();
		client.Connect(args);	
	}

    public void Connect(String[] args){

        try {

            FTP ftp = (FTP) Naming.lookup("//" + args[0] +"/FTP");
            while(true)
		{
            try{
                System.out.println("Menu : \n1 Upload \n2 Download \n3 Listar arquivos\n4 Excluir Arquivos\nEscolha: ");
                Scanner myObj = new Scanner(System.in);
                String inputFromUser = myObj.nextLine(); 
                int i = Integer.parseInt(inputFromUser);
                switch(i)
                {
                    case 1: Send(ftp); break;
                    case 2: Receive(ftp); break;
                    case 3: List(ftp); break;
                    case 4: Del(ftp); break;
                    default: System.out.println("Õpção inválida !");
                } 
            }catch(Exception e)
            {
                System.out.println("Ocorreu um erro!");
            }
		}
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("error with connection or command. Check your hostname or command");
        }
    
    }

    public void Send(FTP ftp)
    {

        try{
            Scanner myObj = new Scanner(System.in);
            System.out.println("Informe o arquivo:");
            String filepath = myObj.nextLine(); 
            serverpath = "./Files/";
            
            File input_file = new File(filepath);
            if(input_file.exists()){
                byte [] mydata=new byte[(int) input_file.length()];
                FileInputStream input_stream =new FileInputStream(input_file);	
                input_stream.read(mydata, 0, mydata.length);	
                ftp.Upload(mydata, serverpath + filepath, (int) input_file.length());
                input_stream.close();
            }
            else
            {
                System.out.println("Arquivo inexistente");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Receive(FTP ftp)
    {   
        try{
            Scanner myObj = new Scanner(System.in);
            System.out.println("Informe o arquivo:");
            String filepath = myObj.nextLine(); 
            serverpath = "./Files/"  + filepath;
            byte [] data;
            try{
                data = ftp.Download(serverpath);
                if(data == null){
                    System.out.println("Arquivo inexistente.");
                    return;
                }
                File clientfile = new File("./" + filepath);
                FileOutputStream output_stream = new FileOutputStream(clientfile);

                System.out.println("Download Concluido");
                
                output_stream.write(data);
                output_stream.flush();
                output_stream.close();
                }catch(NullPointerException e){
                System.out.println("Arquivo inexistente.");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void List(FTP ftp){
        try{

            String files_list = ftp.ListFiles();
            System.out.println(files_list);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void Del(FTP ftp){
        try{ 
            Scanner myObj = new Scanner(System.in);
            System.out.println("Informe o arquivo para deletar:");
            String filepath = myObj.nextLine(); 
            serverpath = "./Files/"  + filepath;
            try{
                ftp.Delete(serverpath);

                System.out.println("Arquivo deletado.");
                }catch(NullPointerException e){
                System.out.println("Arquivo inexistente.");
            } 
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
