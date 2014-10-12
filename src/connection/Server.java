
package connection;

import java.security.*;
import java.net.*;  
import java.io.*;
import javax.net.ssl.*;
import java.security.KeyStore;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 * Credit to the JSSE tutorial available at
 * http://www.ibm.com/developerworks/java/tutorials/j-jsse/j-jsse.html
 */
public class Server {
    
    private SecureRandom secureRandom;
    private KeyStore serverKeyStore;
    private KeyStore clientKeyStore;
    private TrustManagerFactory tmf;
    private KeyManagerFactory kmf;
    private String passphrase = "password";
    private SSLContext sslContext;
    private SSLServerSocket ss;
    
    public SSLServerSocket getServerSocket(){
        return ss;
    }
    
    private void setSecureRandom(){
        secureRandom = new SecureRandom();
        secureRandom.nextInt();
    }
    
    private void setupServerKeystore() throws GeneralSecurityException, IOException {
        serverKeyStore = KeyStore.getInstance( "JKS" );
        serverKeyStore.load( new FileInputStream( "keys/server.private" ), passphrase.toCharArray() );
    }
    
    private void setupClientKeystore() throws GeneralSecurityException, IOException {
        clientKeyStore = KeyStore.getInstance( "JKS" );
        clientKeyStore.load( new FileInputStream( "keys/client.public" ), "public".toCharArray() );
    }
    
    private void setTrustManagerFactory() throws NoSuchAlgorithmException, KeyStoreException{
        tmf = TrustManagerFactory.getInstance( "SunX509" );
        tmf.init( clientKeyStore );
    }
    
    private void setKeyManagerFactory() throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException{
        kmf = KeyManagerFactory.getInstance( "SunX509" );
        kmf.init( serverKeyStore, passphrase.toCharArray() );
    }
    
    private void setSSLContext() throws NoSuchAlgorithmException, KeyManagementException{
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), secureRandom);
    }
    
    private void setup() throws GeneralSecurityException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException{
        setSecureRandom();
        setupServerKeystore();
        setupClientKeystore();
        setTrustManagerFactory();
        setKeyManagerFactory();
        setSSLContext();
        
        SSLServerSocketFactory sf = sslContext.getServerSocketFactory();
        ss = (SSLServerSocket)sf.createServerSocket( 4444 );
        ss.setNeedClientAuth( true );
    }
    
    public static void main(String[] args) {
        Server server = new Server();
        Socket s1;
        
        try{
            server.setup();
        }catch(Exception e){
            System.out.println("Exception: "+e);
            e.printStackTrace();
        }
        
        try{
            while(true){
                s1 = server.getServerSocket().accept();
                new Server.SocketThread(s1).start();
            }
        }catch(Exception e){
            System.out.println("Exception: "+e);
            e.printStackTrace();
        }
    }
    
    private static class SocketThread extends Thread{
        private Socket s = null;
    
        public SocketThread(Socket socket) {
            super("SocketThread");
            this.s= socket;
            System.out.println("connection from "+s.getInetAddress().toString());
        }
        
        public void run(){
            try{
                
                OutputStream os1 = s.getOutputStream();  
                ObjectOutputStream oos1 = new ObjectOutputStream(os1);
                InputStream is1 = s.getInputStream();  
                ObjectInputStream ois1 = new ObjectInputStream(is1);
                
            
                boolean loop = true;
                while(loop){
                    TestObject to1 = (TestObject)ois1.readObject();
                    if (to1!=null){
                        System.out.println(to1.getValue());
                    }
                }
                
                ois1.close();  
                is1.close(); 
                oos1.close();  
                os1.close(); 
                s.close();
            }catch(Exception e){
                System.out.println("Exception: "+e);
                e.printStackTrace();
            }
        }
    }
    
}
