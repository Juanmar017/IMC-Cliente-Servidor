# Sistema de Calculo IMC (Cliente - Servidor TCP)

Proyecto desarrollado en Java Swing con comunicacion mediante Sockets TCP para el calculo del Indice de Masa Corporal (IMC).

## Estructura del Proyecto
* ServidorIMC: Gestiona las conexiones entrantes, procesa el peso y la altura recibidos, calcula el IMC y envia la categoria correspondiente.
* ClienteIMC: Interfaz grafica para configurar la direccion IP/Puerto y enviar los datos del usuario al servidor.

## Tecnologias utilizadas
* Lenguaje: Java (JDK 8+)
* GUI: Swing / Form AWT
* Protocolo: Sockets TCP (Socket, ServerSocket)
* IDE Recomendado: NetBeans / VS Code
