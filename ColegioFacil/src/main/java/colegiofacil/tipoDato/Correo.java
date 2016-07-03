package colegiofacil.tipoDato;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * Clase encargada de manejar un email para validar.
 *
 * @author Omar Paché
 */
public class Correo {

    private static final Logger LOG = Logger.getLogger(Correo.class);

    private final String correo;

    /**
     * Constructor, llama a validar el formato del correo electrónico.
     *
     * @param correo dirección de correo electrónica
     * @throws IllegalArgumentException lanzada si la dirección de correo
     * ingresada no tiene el formato esperado
     */
    public Correo(String correo) throws IllegalArgumentException {
        if (correo == null) {
            throw new NullPointerException("Dirección correo nula");
        }
        if (validar(correo)) {
            this.correo = correo;
        } else {
            throw new IllegalArgumentException("Correo electrónico inválido");
        }
    }

    /**
     * Valida que la dirección de correo electrónico ingresada cumpla con el
     * formato nombre@hosting.dominio
     *
     * @param correo dirección de correo electrónico
     * @return true si el correo cumple formato, false de lo contrario
     */
    private boolean validar(String correo) {
        Pattern pat;
        Matcher mat;
        pat = Pattern.compile("^[\\w\\-\\_]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");
        mat = pat.matcher(correo);
        return mat.find();
    }

    /**
     * Accede al correo como texto
     *
     * @return el correo electrónico
     */
    @Override
    public String toString() {
        return correo;
    }

}
