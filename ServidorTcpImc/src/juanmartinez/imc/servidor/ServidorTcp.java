/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juanmartinez.imc.servidor;

import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import juanamrtinez.imc.vistas.VentanaPrincipalP;

public class ServidorTcp implements Runnable {
    private boolean estado;
    private int puerto;
    private ServerSocket servicio;
    private Map<String, SubProcesoCliente> listaDeClientes;
    private VentanaPrincipalP ventana;

    public ServidorTcp(int puerto, VentanaPrincipalP ventana) {
        this.puerto = puerto;
        this.ventana = ventana;
        this.listaDeClientes = new HashMap<>();
    }

  @Override
public void run() {
    while (estado) {
        try {
            // Espera conexiones de clientes
            Socket socketCliente = servicio.accept();

            // Crear SubProcesoCliente para atender al cliente
            SubProcesoCliente subProceso = new SubProcesoCliente(socketCliente, ventana);

            // Guardar en la lista de clientes
            listaDeClientes.put(socketCliente.getInetAddress().toString(), subProceso);

            // Iniciar el hilo del cliente
            new Thread(subProceso).start();

            // Log en la ventana
            ventana.agregarLog("Cliente conectado: " + socketCliente.getInetAddress());

        } catch (IOException e) {
            ventana.agregarLog("Error al aceptar cliente: " + e.getMessage());
        }
    }
}

    // Método para iniciar el servicio
    public void iniciarServicio() {
        try {
            servicio = new ServerSocket(puerto);
            estado = true;
            ventana.setEstadoServidor("ONLINE"); // Actualiza la interfaz
            ventana.agregarLog("Servidor iniciado en el puerto " + puerto);
        } catch (IOException e) {
            ventana.agregarLog("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    // Método para detener el servicio
    public void detenerServicio() {
        try {
            estado = false;
            if (servicio != null && !servicio.isClosed()) {
                servicio.close();
            }
            ventana.setEstadoServidor("OFFLINE"); // Actualiza la interfaz
            ventana.agregarLog("Servidor detenido");
        } catch (IOException e) {
            ventana.agregarLog("Error al detener el servidor: " + e.getMessage());
        }
    }

    // Getters y setters
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}