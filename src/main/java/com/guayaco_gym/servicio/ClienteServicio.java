/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guayaco_gym.servicio;

import excepciones.GuayacoGymException;
import com.guayaco_gym.modelo.Cliente;
import com.guayaco_gym.repositorio.ClienteRepositorio;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

/**
 *
 * @author jair-
 */
@Service
public class ClienteServicio implements IClienteServicio {

    //OJO, contrst
    @Autowired //Inyeccion de dependencia del client repositorio para poder hacer las
    //operaciones con la bd
    private ClienteRepositorio clienteRepositorio;

    // Inyectar el validador
    @Autowired
    private Validator validator; 

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        return clientes;
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Integer idCliente) {
        // Buscamos el cliente por ID, si no existe, devolvemos un Optional vacío
        return clienteRepositorio.findById(idCliente);
    }

    @Override
    public void guardarCliente(@Valid Cliente cliente) {
        // Crear un objeto BindingResult para almacenar los errores de validación
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(cliente, "cliente");

        // Validamos el cliente
        validator.validate(cliente, bindingResult);

        // Validación adicional para la cédula (10 dígitos numéricos)
        if (cliente.getCedula() == null || !cliente.getCedula().matches("\\d{10}")) {
            //La función rejectValue permite agregar errores a un campo específico
            bindingResult.rejectValue("cedula", "error.cedula", "La cédula debe tener exactamente 10 dígitos numéricos.");
        }

        // Si hay errores de validación, mostramos el mensaje
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            System.out.println("Errores de validación: " + errorMessage);
        } else {
            // Asignar la fecha actual si no se ha establecido
            if (cliente.getFechaInicio() == null) {
                cliente.setFechaInicio(LocalDate.now());
            }
            // Guardamos el cliente en la base de datos
            clienteRepositorio.save(cliente);
            System.out.println("Cliente agregado: " + cliente);
        }
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        //Verificacion del cliente antes de eliminarlo:

        //Optional es una clase contenedora que puede contener un valor de tipo T o no
        //la clase Optional<Cliente> es utilizada para manejar el resultado de la búsqueda de un cliente en la base de datos
        /*findById(cliente.getId()): Este método de JpaRepository devuelve un 
        Optional<Cliente>, lo que significa que la consulta puede devolver un cliente 
        o puede no devolver nada. 
        En caso de no encontrar el cliente, el Optional estará vacío. 
         */
        Optional<Cliente> clienteExistente = clienteRepositorio.findById(cliente.getId());

        if (clienteExistente.isPresent()) {
            clienteRepositorio.delete(cliente);
        } else {
            throw new GuayacoGymException("No se puede eliminar: Cliente con ID " + cliente.getId() + " |No existe.");
        }

    }

    @Override
    public void agregarDiasMembresia(int idCliente, int diasAAgregar) {
        Optional<Cliente> optionalCliente = buscarClientePorId(idCliente);

        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();

            // Calcular días restantes
            long diasRestantes = calcularDiasRestantes(cliente);

            // Actualizar la membresía
            cliente.setMembresia((int) diasRestantes + diasAAgregar);

            // Guardar cambios
            clienteRepositorio.save(cliente);

            System.out.println("Se han agregado " + diasAAgregar + " días a la membresía del cliente con ID: " + idCliente);
        } else {
            System.out.println("Cliente con ID " + idCliente + " no encontrado.");
        }
    }

    @Override
    public long calcularDiasRestantes(Cliente cliente) {
        LocalDate fechaFin = cliente.getFechaInicio().plusDays(cliente.getMembresia());
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaFin);
    }

}
