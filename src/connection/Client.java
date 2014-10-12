
package connection;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.ssl.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 * Credit to the JSSE tutorial available at
 * http://www.ibm.com/developerworks/java/tutorials/j-jsse/j-jsse.html
 */
public class Client {
    private String serverAddress;
    private int serverPort;
    
    private OutputStream os;
    private ObjectOutputStream oos;
    private InputStream is;  
    private ObjectInputStream ois;
    
    private SecureRandom secureRandom;
    private KeyStore serverKeyStore;
    private KeyStore clientKeyStore;
    private TrustManagerFactory tmf;
    private KeyManagerFactory kmf;
    private String password = "password";
    private SSLContext sslContext;
    private SSLSocket socket;
    
    public SSLSocket getSocket(){
        return socket;
    }
    
    public void close() throws IOException{
        socket.close();
    }
    
    private void setSecureRandom(){
        secureRandom = new SecureRandom();
        secureRandom.nextInt();
    }
    
    private void setClientKeystore() throws GeneralSecurityException, IOException {
        clientKeyStore = KeyStore.getInstance( "JKS" );
        clientKeyStore.load( new FileInputStream( "keys/client.private" ), password.toCharArray() );
    }
    
    private void setServerKeystore() throws GeneralSecurityException, IOException {
        serverKeyStore = KeyStore.getInstance( "JKS" );
        serverKeyStore.load( new FileInputStream( "keys/server.public" ), "public".toCharArray() );
    }
    
    private void setTrustManagerFactory() throws NoSuchAlgorithmException, KeyStoreException{
        tmf = TrustManagerFactory.getInstance( "SunX509" );
        tmf.init( serverKeyStore );
    }
    
    private void setKeyManagerFactory() throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException{
        kmf = KeyManagerFactory.getInstance( "SunX509" );
        kmf.init( clientKeyStore, password.toCharArray() );
    }
    
    private void setSSLContext() throws NoSuchAlgorithmException, KeyManagementException{
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), secureRandom);
    }
    
    public void setup(String address, int port) throws GeneralSecurityException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException{
        serverAddress = address;
        serverPort = port;

        setSecureRandom();
        setServerKeystore();
        setClientKeystore();
        setTrustManagerFactory();
        setKeyManagerFactory();
        setSSLContext();
        
        SSLSocketFactory sf = sslContext.getSocketFactory();
        socket = (SSLSocket)sf.createSocket(address, port);
    }
    
    public void setOutputStream() throws IOException{
        os = this.getSocket().getOutputStream();  
        oos = new ObjectOutputStream(os);
    }
    
    public void unsetOutputStream() throws IOException{
        oos.close(); 
        os.close();
    }
    
    public void setInputStream() throws IOException{
        is = this.getSocket().getInputStream();
        ois = new ObjectInputStream(is);
    }
    
    public void unsetInputStream() throws IOException{
        ois.close(); 
        is.close();
    }
    
    public void send(Object obj) throws IOException{
        oos.writeObject(obj);
    }
    
    public Object receive() throws IOException, ClassNotFoundException{
        return ois.readObject();
    }
    
    public static void main(String[] args) {
        Client client = new Client();
         
        try{
            client.setup("localhost",4444);
        }catch(Exception e){
            if(e instanceof ConnectException){
                System.out.println("Error on connection: "+e.getMessage());
                return;
            }
            System.out.println("Exception: "+e);
            e.printStackTrace();
        }
        
        try{
            
            client.setOutputStream();
        
            boolean loop = true;
            while(loop){
                TestObject to = new TestObject((int)(100*Math.random()),"object from client");
                System.out.println("Generated = "+to.getValue());

                client.send(to);
                Thread.sleep(3000);
            }
            
            client.unsetOutputStream();
            client.close();
            
        }catch(Exception e){
            System.out.println("Exception: "+e);
            e.printStackTrace();
        }
    }
    
    
}
