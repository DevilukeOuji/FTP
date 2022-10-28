import java.rmi.RemoteException;

public interface FTP extends java.rmi.Remote {
    
    public void Upload(byte[] mydata, String serverpath, int length) throws RemoteException;
    public byte[] Download(String serverpath) throws RemoteException;
    public String ListFiles() throws RemoteException;
    public boolean Delete(String serverpath) throws RemoteException;

}
