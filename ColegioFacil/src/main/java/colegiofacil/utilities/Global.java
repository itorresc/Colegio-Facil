package colegiofacil.utilities;

import javax.persistence.EntityManagerFactory;

/**
 * Clase contenedora de las constantes
 *
 * @author Omar Paché
 */
public class Global {

    // Constante inyectada desde el archivo de propiedades
    public static String ambiente;
    public static EntityManagerFactory emf = null;
}
