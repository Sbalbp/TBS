/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.Serializable;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class TestObject implements Serializable{  
        private int value;  
        private String id;
    
        public  TestObject(int v, String s){  
            this.value=v;  
            this.id=s;  
        }
        
        public int getValue(){
            return value;
        }
        
        public String getId(){
            return id;
        }
    }
