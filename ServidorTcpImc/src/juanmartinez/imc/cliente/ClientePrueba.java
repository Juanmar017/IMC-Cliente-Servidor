/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juanmartinez.imc.cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import juanmartinez.imc.modelo.CalculoImc;

public class ClientePrueba {
  public static void main(String[] args) {
        try {
            // Conectar al servidor en localhost y puerto 9007
            Socket socket = new Socket("127.0.0.1", 9007);

            // Crear objeto CalculoImc con datos de prueba
            CalculoImc calculo = new CalculoImc(70, (float) 1.75); // 70kg, 1.75m

            // Enviar objeto al servidor
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            salida.writeObject(calculo);
            salida.flush();

            // Recibir resultado del servidor
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            CalculoImc.Imc resultado = (CalculoImc.Imc) entrada.readObject();

            // Mostrar resultado en consola
            System.out.println("IMC: " + resultado.resultado + " -> " + resultado.mensaje);

            // Cerrar recursos
            entrada.close();
            salida.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Error en cliente: " + e.getMessage());
        }
    }
}
