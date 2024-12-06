/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author jair-
 */
public class GuayacoGymException extends RuntimeException{
    
    
    //El constructor que recibe el mensaje de error
    public GuayacoGymException(String mensaje){
        super(mensaje);
    }
    
    //El constructor que recibe el mensaje de error y la causa
    public GuayacoGymException(String mensaje, Throwable cause){
        super(mensaje, cause);
    }
}
