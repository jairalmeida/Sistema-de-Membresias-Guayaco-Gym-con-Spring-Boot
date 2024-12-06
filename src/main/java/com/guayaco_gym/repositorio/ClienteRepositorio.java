/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guayaco_gym.repositorio;

import com.guayaco_gym.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jair-
 */
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    
}
