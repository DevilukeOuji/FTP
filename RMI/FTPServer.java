import java.rmi.Naming;

public class FTPServer{
    public FTPServer() {
        try {
            FTP obj = new FTPImplementation();
            Naming.rebind("//127.0.0.1/FTP", obj);
        }
        catch( Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public static void main( String[] args ) {
        new FTPServer( );
    }
}
