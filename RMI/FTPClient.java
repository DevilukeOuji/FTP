import java.rmi.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Scanner;

public class FTPClient {

    public static void main( String[] args ) {

		String serverpath;
		String upload = "upload";
		String download = "download";
        String list = "list";

        try {

            FTP ftp = (FTP) Naming.lookup("//127.0.0.1/FTP");

			if(upload.equals(args[0]))
			{

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
            if(download.equals(args[0]))
            {   
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

            if(list.equals(args[0])){
                String files_list = ftp.ListFiles();
                System.out.println(files_list);
            }

        }
        catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("error with connection or command. Check your hostname or command");
		}	
        /*catch ( MalformedURLException murle ) {
            System.out.println( );
            System.out.println( "MalformedURLException" );
            System.out.println( murle );
        }
        catch ( RemoteException re ) {
            System.out.println( );
            System.out.println( "RemoteException" );
            System.out.println( re );
        }
        catch ( NotBoundException nbe ) {
            System.out.println( );
            System.out.println( "NotBoundException" );
            System.out.println( nbe );
        }*/
    }
}
