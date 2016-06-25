package colegiofacil.utilities;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;

/**
 *
 * @author Omar Paché
 */
public class UnaInstancia extends Thread {

    private static final Logger log = Logger.getLogger(UnaInstancia.class);
    public static final int PORT = 5500;

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    @Override
    public void run() {
        try {
            // Crear el servidor socket en el puerto especificado
            serverSocket = new ServerSocket(PORT, 1);
            while (true) {
                clientSocket = serverSocket.accept();
                clientSocket.close();
            }
        } catch (IOException ioe) {
            log.info("Error al generar instancia: " + ioe);
        }
    }
}
