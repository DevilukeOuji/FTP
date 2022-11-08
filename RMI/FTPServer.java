import java.rmi.Naming;

public class FTPServer{
    public FTPServer(String[] args) {
        try {
            FTP obj = new FTPImplementation();
            Naming.rebind("//" + args[0] +"/FTP", obj);
        }
        catch( Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public static void main( String[] args ) {
        new FTPServer(args);
    }
}
