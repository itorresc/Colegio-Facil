package colegiofacil.tipoDato;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.impl.ResultadoMetodoImpl;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 * Clase encargada de registrar una contraseña para encriptar.
 *
 * @author Iván Torres Curinao
 */
public class Password {

    static final Logger LOG = Logger.getLogger(Password.class);

    private String password;

    //codigos de error para atributos a validar
    public static final int CONTRASENIA = 3;

    //minimos y maximos a validar
    public static final int LARGO_MINIMO = 6;

    /**
     * Corta espacios en los extremos después asigna.
     *
     * @param password la contraseña
     */
    public Password(String password) {
        if (password == null) {
            throw new NullPointerException("password nulo");
        } else {
            this.password = password.trim();
        }
    }

    /**
     * Encriptar una contraseña en formato String que sea mayor a 6 caracteres y
     * menor a 8 en el algoritmo MD5.
     *
     * @param password la contraseña
     * @return el resultado de la operación
     */
    public ResultadoMetodo cifrarPassword(String password) {
        try {
            password = MD5.encriptar(password);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            return ResultadoMetodoImpl.setError(CONTRASENIA, "Error al encriptar MD5");
        }
        return ResultadoMetodoImpl.setSinError(1, password);
    }

    /**
     * Validar que no se encuentre vacío y el largo de contraseña
     *
     * @return error: contraseña vacío o contraseña no mayor a 8 ni menor a 6
     * caracteres, sinError: contraseña valida
     */
    public ResultadoMetodo validar() {
        if (password.isEmpty()) {
            LOG.info("contraseña vacia");
            return ResultadoMetodoImpl.setError(CONTRASENIA, "Debe ingresar una contraseña");
        }
        if (password.length() != LARGO_MINIMO) {
            return ResultadoMetodoImpl.setError(CONTRASENIA, "Debe ingresar una contraseña de 6 caracteres");
        }
        if (esAlfaNumerico(password).isError()) {
            return esAlfaNumerico(password);
        }
        return ResultadoMetodoImpl.setSinError(1, "Contraseña válida");
    }

    /**
     * Validar que la contraseña solo contenga letras y números y que a lo menos
     * tenga un dígito o una letra.
     *
     * @param clave la contraseña
     * @return el resultado de la operación
     */
    public ResultadoMetodo esAlfaNumerico(String clave) {
        int conLetra = 0;
        int conNumero = 0;

        for (int i = 0; i < clave.length(); ++i) {
            char caracter = clave.charAt(i);

            if (Character.isDigit(caracter)) {
                conNumero++;
            }
            if (Character.isLetter(caracter)) {
                conLetra++;
            }
            if (!Character.isLetterOrDigit(caracter)) {
                return ResultadoMetodoImpl.setError(CONTRASENIA, "No se permiten caracteres especiales en la contraseña");
            }
        }
        if (conNumero == 0) {
            return ResultadoMetodoImpl.setError(CONTRASENIA, "La contraseña necesita llevar a lo menos un carácter numérico");
        } else if (conLetra == 0) {
            return ResultadoMetodoImpl.setError(CONTRASENIA, "La contraseña necesita llevar a lo menos una letra");
        }
        return ResultadoMetodoImpl.setSinError();
    }

    public boolean estaVacio() {
        return password.isEmpty();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Password other = (Password) obj;
        return Objects.equals(this.password, other.password);
    }

    @Override
    public String toString() {
        return password;
    }

}
