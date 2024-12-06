package com.guayaco_gym.tareasProgramadas;

import com.guayaco_gym.modelo.Cliente;
import com.guayaco_gym.servicio.IClienteServicio;
import java.util.List;
import static org.hibernate.internal.CoreLogging.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author jair-
 */
@Component
public class TareasProgramadas {

    private static final Logger logger = LoggerFactory.getLogger(TareasProgramadas.class);
    private final IClienteServicio clienteServicio;

    //Iny de dependencia
    @Autowired
    public TareasProgramadas(IClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    //Para el caso de esta bd con pocos registros no hay problema
    //En caso de que sea algo masivo, no seria conveniente estar mostrando todos
    //los dias a todos los clientes. 
    @Scheduled(cron = "0 0 0 * * *")//Tarea prog para la media noche todos los dias
    public void calcularDiasRestantesParaClientes() {
        logger.info("Iniciando tarea programada: cálculo de días restantes...");

        clienteServicio.listarClientes().forEach(cliente -> {
            long diasRestantes = clienteServicio.calcularDiasRestantes(cliente);
           logger.info("Cliente: {} {} - Días restantes: {}\n----------", 
                    cliente.getNombre(), cliente.getApellido(), diasRestantes);
        });

        logger.info("Tarea programada completada.");
    }

    @Scheduled(cron = "0 0 8 * * *") // A las 08:00 am todos los días
    public void alertarMembresiasProximasAVencer() {
        logger.info("Iniciando tarea programada: alerta de membresías próximas a vencer...");

        clienteServicio.listarClientes().forEach(cliente -> {
            long diasRestantes = clienteServicio.calcularDiasRestantes(cliente);
            if (diasRestantes <= 5) { // Si quedan 5 días o menos
               logger.warn("Alerta: La membresía de {} {} está próxima a vencer en {} días.\n----------",
                        cliente.getNombre(), cliente.getApellido(), diasRestantes);
            }
        });

        logger.info("Tarea de alerta completada.");
    }

//    @Scheduled(cron = "0 0 1 * * ?") // A la 1:00 AM cada día
//    public void eliminarClientesInactivos() {
//        List<Cliente> clientes = clienteRepositorio.findAll();
//
//        clientes.stream()
//                .filter(cliente -> calcularDiasRestantes(cliente) < -30) // Más de 30 días de vencimiento
//                .forEach(cliente -> {
//                    clienteRepositorio.delete(cliente);
//                    System.out.println("Cliente eliminado: " + cliente.getNombre() + " " + cliente.getApellido());
//                });
//    }

}
