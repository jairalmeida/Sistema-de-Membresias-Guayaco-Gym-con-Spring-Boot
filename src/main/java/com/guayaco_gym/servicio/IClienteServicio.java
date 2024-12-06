/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guayaco_gym.servicio;

import com.guayaco_gym.modelo.Cliente;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jair-
 */
public interface IClienteServicio {
    public List<Cliente> listarClientes();
    
    public Optional<Cliente> buscarClientePorId(Integer idCliente);
    
    public void guardarCliente(Cliente cliente);
    
    public void eliminarCliente(Cliente cliente);
    
    public void agregarDiasMembresia(int idCliente, int diasAAgregar);
             
    public long calcularDiasRestantes(Cliente cliente);
    
    
}
