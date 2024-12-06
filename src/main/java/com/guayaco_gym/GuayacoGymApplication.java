package com.guayaco_gym;

import com.guayaco_gym.modelo.Cliente;
import com.guayaco_gym.servicio.IClienteServicio;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.guayaco_gym.tareasProgramadas.TareasProgramadas;

@SpringBootApplication
@ComponentScan(basePackages = {"com.guayaco_gym", "com.guayaco_gym.tareasProgramadas"})
public class GuayacoGymApplication implements CommandLineRunner {

    @Autowired
    private IClienteServicio clienteServicio;

    @Autowired
    private TareasProgramadas tareasProgramadas;

    private static final Logger logger = LoggerFactory.getLogger(GuayacoGymApplication.class);

    String nl = System.lineSeparator();

    public static void main(String[] args) {
        logger.info("Iniciando la aplicacion");
        //Levantar la fabrica de spring
        SpringApplication.run(GuayacoGymApplication.class, args);

        logger.info("Aplicacion finalizada");

    }

    @Override
    public void run(String... args) throws Exception {
        zonaFitApp();
    }

    private void zonaFitApp() {
        logger.info(nl + "***Aplicacion Zona fit (GYM)****" + nl);
        var salir = false;
        var consola = new Scanner(System.in);

        while (!salir) {
            var opcion = mostrarMenu(consola);
            salir = ejecutarOpciones(consola, opcion);
            logger.info(nl);
        }
    }
    // Método para validar cédula

    private static boolean isCedulaValida(String cedula) {
        return cedula.matches("\\d{10}");
    }

    private int mostrarMenu(Scanner consola) {
        logger.info("""
        \n***Aplicacion Zona fit (GYM)****
        1. Listar Clientes
        2. Buscar cliente
        3. Agregar Cliente
        4. Modificar Cliente
        5. Eliminar Cliente
        6. Agregar dias de membresia
        7. Ver membresias próximas a vencer
        8. Ver dias restantes de clientes                
        9. Salir            
        Elige una opcion:\s""");

        var opcion = Integer.parseInt(consola.nextLine());
        return opcion;
    }

    private boolean ejecutarOpciones(Scanner consola, int opcion) {
        var salida = false;
        switch (opcion) {
            case 1 -> {
                logger.info(nl + "--- Listado de Clientes --- " + nl);
                List<Cliente> clientes = clienteServicio.listarClientes();
                clientes.forEach(cliente -> logger.info(cliente.toString()));
            }
            case 2 -> {
                logger.info(nl + "--- Buscar cliente por id ---" + nl);
                try {
                    // Solicitamos el ID al usuario
                    var idCliente = Integer.parseInt(consola.nextLine());

                    // Buscamos al cliente
                    //Optional: Evita errores de referencia nula
                    Optional<Cliente> clienteOpt = clienteServicio.buscarClientePorId(idCliente);
                    if (clienteOpt.isPresent()) {
                        logger.info("Se ha encontrado al cliente: " + clienteOpt.get() + nl);
                    } else {
                        logger.info("No se ha encontrado al cliente con ID: " + idCliente + nl);
                    }
                } catch (NumberFormatException e) {
                    // Mensaje en caso de que el usuario ingrese letras u otros caracteres no válidos
                    logger.error("Por favor, ingrese un número válido para el ID." + nl);
                }
            }

            case 3 -> {
                logger.info("---Agregar cliente---" + nl);
                logger.info("Nombre: ");
                var nombre = consola.nextLine();
                logger.info("Apellido: ");
                var apellido = consola.nextLine();

                int membresia = -1;

                while (membresia < 0) {
                    logger.info("Membresia (número entero): ");
                    try {
                        membresia = Integer.parseInt(consola.nextLine());
                        if (membresia <= 0) {
                            throw new NumberFormatException("La membresía debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        logger.error("Error en membresía: " + e.getMessage() + ". Por favor ingrese un valor numérico válido.");
                    }
                }

                logger.info("Cédula (10 dígitos numéricos): ");
                var cedula = consola.nextLine();
                while (!isCedulaValida(cedula)) {
                    logger.error("Error en cédula: La cédula debe tener exactamente 10 dígitos numéricos.");
                    logger.info("Cédula (10 dígitos numéricos): ");
                    cedula = consola.nextLine();
                }

                var cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setMembresia(membresia);
                cliente.setCedula(cedula);
                try {
                    // Intentamos guardar el cliente
                    clienteServicio.guardarCliente(cliente);
                    logger.info("Cliente agregado con éxito: " + cliente);

                } catch (Exception e) {
                    // Si ocurre cualquier excepción, mostramos el error
                    logger.error("Error al agregar cliente: " + e.getMessage());
                }
            }

            case 4 -> {
                logger.info("--- Modificar Cliente ---" + nl);
                try {
                    logger.info("Ingrese el ID: ");
                    var idCliente = Integer.parseInt(consola.nextLine());

                    // Llamamos al método que devuelve un Optional
                    Optional<Cliente> optionalCliente = clienteServicio.buscarClientePorId(idCliente);

                    if (optionalCliente.isPresent()) {
                        Cliente cliente = optionalCliente.get();
                        logger.info("Registro encontrado con ID: " + idCliente);

                        // Pedimos los datos para modificar
                        logger.info("Nombre: ");
                        var nombre = consola.nextLine();
                        logger.info("Apellido: ");
                        var apellido = consola.nextLine();

                        // Intentamos ingresar la membresía con un bucle para reintentar
                        int membresia = 0;
                        boolean membresiaValida = false;
                        while (!membresiaValida) {
                            try {
                                logger.info("Membresía: ");
                                membresia = Integer.parseInt(consola.nextLine());  // Intentamos leer la membresía
                                cliente.setMembresia(membresia);  // Si la membresía es válida, la asignamos
                                membresiaValida = true;  // Marcamos como válida para salir del bucle
                            } catch (NumberFormatException e) {
                                logger.error("Error: La membresía debe ser un número entero válido. Intente nuevamente.");
                            }
                        }

                        // Pedimos la cédula si es necesario
                        logger.info("Cédula (deja en blanco si no deseas modificarla): ");
                        var cedula = consola.nextLine();  // Leemos la cédula

                        // Actualizamos el cliente con los datos nuevos
                        cliente.setNombre(nombre);
                        cliente.setApellido(apellido);

                        // Solo actualizamos la cédula si se ingresó un valor
                        if (!cedula.isEmpty()) {
                            cliente.setCedula(cedula);  // Asignamos la cédula si no está vacía
                        }

                        try {
                            clienteServicio.guardarCliente(cliente);  // Guardamos el cliente
                            logger.info("Cliente modificado con éxito: " + cliente + nl);
                        } catch (Exception e) {
                            logger.error("Error al modificar el cliente: " + e.getMessage());
                            e.printStackTrace(System.out);
                        }
                    } else {
                        logger.info("Cliente con ID " + idCliente + " no encontrado.");
                    }
                } catch (NumberFormatException e) {
                    logger.error("ID inválido. Debe ser un número entero.");
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado: " + e.getMessage());
                    e.printStackTrace(System.out);
                }
            }

            case 5 -> {
                logger.info("--- Eliminar Cliente ---" + nl);
                try {
                    logger.info("ID del cliente: ");
                    var idCliente = Integer.parseInt(consola.nextLine());

                    // Llamamos al método que devuelve un Optional
                    Optional<Cliente> optionalCliente = clienteServicio.buscarClientePorId(idCliente);

                    //verificamos si el cliente existe
                    //verifica si el Optional contiene un valor 
                    if (optionalCliente.isPresent()) {
                        //Recuperamos el valor almacenado dentro del Optional
                        Cliente cliente = optionalCliente.get();
                        /*Lo que estamos haciendo es recuperar el valor contenido dentro del Optional, 
                        que en este caso es un objeto de tipo Cliente. Si el 
                        Optional contiene un cliente, lo obtenemos y lo asignamos a la variable cliente.*/
 /* ^Es recomendable trabajar con metodos mas seguros como
                        ifPresent() o orElse() para Optional. Yo los escogi
                        porque no veo problema para este caso
                         */
                        clienteServicio.eliminarCliente(cliente);
                        logger.info("Cliente eliminado con éxito: " + cliente + nl);
                    } else {
                        logger.info("Cliente con ID " + idCliente + " no encontrado." + nl);
                    }
                } catch (NumberFormatException e) {
                    logger.error("ID inválido. Debe ser un número entero.");
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado: " + e.getMessage());
                    e.printStackTrace(System.out);
                }
            }

            case 6 -> {
                logger.info("---Agregar Días a Membresia ---" + nl);
                logger.info("Ingrese el ID del cliente");
                int id = Integer.parseInt(consola.nextLine());
                logger.info("Ingrese la cantidad de dias a agregar");
                int dias = Integer.parseInt(consola.nextLine());

                try {
                    clienteServicio.agregarDiasMembresia(id, dias);

                } catch (Exception e) {
                    logger.error("Error al agregar dias:" + e.getMessage());
                }
            }

            case 7 -> {
                tareasProgramadas.alertarMembresiasProximasAVencer();
            }

            case 8 -> {
                tareasProgramadas.calcularDiasRestantesParaClientes();
            }

            case 9 -> {
                logger.info("¡Hasta pronto!" + nl + nl);
                salida = true;
            }
            default ->
                logger.info("Opcion no reconocida: " + opcion + nl);
        }
        return salida;
    }
}
