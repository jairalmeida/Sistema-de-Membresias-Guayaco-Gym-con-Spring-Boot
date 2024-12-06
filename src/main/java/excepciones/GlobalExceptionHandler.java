/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author jair-
 */
//Manejo global de excepciones
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Manejo de excepciones de validacion
    @ExceptionHandler(GuayacoGymException.class)
    public ResponseEntity<String> manejarExcepcion(GuayacoGymException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //Manejo de excepciones generales: (por ejemplo, RunTimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * Maneja las excepciones de validación generadas por argumentos de métodos
     * anotados con @Valid.
     *
     * @param ex la excepción MethodArgumentNotValidException generada cuando un
     * objeto no cumple las restricciones de validación.
     * @return ResponseEntity "<String" con el mensaje del error de validación y
     * un código de estado 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarValidacionException(MethodArgumentNotValidException ex) {
        // Extrae el mensaje del primer error de validación encontrado
        String mensaje = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage) // Obtiene el mensaje de cada error
                .findFirst() // Selecciona el primer mensaje
                .orElse("Error de validación"); // Mensaje por defecto en caso de que no haya errores

        // Devuelve una respuesta HTTP con el mensaje y un código de estado 400
        return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
    }

}
