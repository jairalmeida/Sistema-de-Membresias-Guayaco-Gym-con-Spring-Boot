/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guayaco_gym.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
//Ultima dependencia del pom
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Agrego ciertas validaciones
    //Aseguramos que no sea 0 y que este dentro de un parametro de size
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, max = 25, message = "El nombre debe tener entre 2 y 25 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(min = 2, max = 40, message = "El apellido debe tener entre 2 y 40 caracteres")
    private String apellido;

    @NotNull(message = "La membresia es obligatoria")
    @Positive(message = "La membresia debe ser un número positivo")
    private Integer membresia;

    //Cambio
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Transient //No se guarda en la BD
    private long diasRestantes;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{10}", message = "La cédula debe tener 10 dígitos numéricos.")
    private String cedula;

    @Override
    public String toString() {
        return "ID: " + this.id + "\n"
                + "Nombre: " + this.nombre + "\n"
                + "Apellido: " + this.apellido + "\n"
                + "Membresía: " + this.membresia + "\n"
                + "Fecha de Inicio: " + this.fechaInicio + "\n"
                + "Cédula: " + this.cedula + "\n";
    }

}
