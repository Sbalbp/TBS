
package connection;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ClientThread extends Thread{
    private String serverAddress = "localhost";
    
    public enum CurrentState {
        CONNECTING, RECEIVING_GAMES, NO_RESPONSE
    }
    
    private Client client;
    private CurrentState currentState;
    
    public ClientThread(){
        client = new Client();
        currentState = CurrentState.CONNECTING;
    }
    
    public CurrentState getCurrentState(){
        return currentState;
    }
    
    public void setCurrentState(CurrentState newState){
        currentState = newState;
    }
    
    public void setServerAddress(String address){
        serverAddress = address;
    }
    
    public void run(){
        try{
            client.setup(serverAddress,4444);
            client.setOutputStream();
            client.setInputStream();
        }
        catch(Exception e){
            System.out.println("Exception: "+e);
            currentState = CurrentState.NO_RESPONSE;
            return;
        }

        currentState = CurrentState.RECEIVING_GAMES;

        while(currentState == CurrentState.RECEIVING_GAMES){
            try{
                client.receive();
                Thread.sleep(3000);
            }
            catch(Exception e){
                System.out.println("Exception: "+e);
                if(e instanceof javax.net.ssl.SSLException){
                    currentState = CurrentState.NO_RESPONSE;
                    break;
                }
            }
        }
    }
}
