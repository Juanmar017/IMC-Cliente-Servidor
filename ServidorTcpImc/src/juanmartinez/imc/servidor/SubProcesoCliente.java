package juanmartinez.imc.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import juanamrtinez.imc.vistas.VentanaPrincipalP;

public class SubProcesoCliente implements Runnable {

    // Atributos principales
    private Socket socketCliente;
    private VentanaPrincipalP ventana;

    // Constructor
    public SubProcesoCliente(Socket socketCliente, VentanaPrincipalP ventana) {
        this.socketCliente = socketCliente;
        this.ventana = ventana;
    }

    @Override
    public void run() {
        try {
            // Flujos de entrada/salida
            DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());

            // Recibir datos del cliente
            float peso = entrada.readFloat();
            float altura = entrada.readFloat();

            // Calcular IMC
            float imc = peso / (altura * altura);
            String mensaje;
            if (imc < 18.5) {
                mensaje = "Bajo peso";
            } else if (imc < 25) {
                mensaje = "Peso normal";
            } else if (imc < 30) {
                mensaje = "Sobrepeso";
            } else {
                mensaje = "Obesidad";
            }

            // Enviar resultado al cliente
            salida.writeFloat(imc);
            salida.writeUTF(mensaje);
            salida.flush();

            // Log en la ventana del servidor
            ventana.agregarLog("Cliente " + socketCliente.getInetAddress() +
                    " -> Peso: " + peso +
                    ", Altura: " + altura +
                    ", IMC: " + imc +
                    " (" + mensaje + ")");

            // Cerrar flujos y socket
            entrada.close();
            salida.close();
            socketCliente.close();

        } catch (IOException e) {
            ventana.agregarLog("Error en SubProcesoCliente: " + e.getMessage());
        }
    }
}
