package colegiofacil.tipoDato;

import colegiofacil.model.excepciones.RutException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * Clase encargada de implementar un rut para validar y formatear.
 *
 * @author Cristián Alarcón de la Maza
 * @author Omar Paché
 */
public final class RUT {

    public static final int LARGO_MAXIMO_RUT_FORMATEADO = 12;
    private static final Logger LOG = Logger.getLogger(RUT.class);
    private final long RUT_MINIMO = 100000;
    private final long RUT_MAXIMO = 999999999;
    private String stringFormateado;
    private int entero;
    private String verificador;

    /**
     * Constructor estándar.
     *
     * @param texto rut como texto
     * @throws RutException si el rut tiene un formato inválido
     * @throws NullPointerException si el parámetro texto es nulo
     */
    public RUT(String texto) throws RutException {
        if (texto == null) {
            throw new NullPointerException("Texto nulo.");
        } else {
            texto = formatear(texto);
            if (validar(texto)) {
                stringFormateado = texto;
            } else {
                throw new RutException("RUT inválido.");
            }
        }
    }

    /**
     * Constructor generado para testing.
     */
    public RUT() {
    }

    /**
     * Quitar espacios en blanco de los extremos.
     *
     * @param texto con o sin espacios en los extremos
     * @return texto sin espacios en los extremos
     */
    private String quitarEspacioExtremos(String texto) {
        return texto.trim();
    }

    /**
     * Reemplaza k minúscula por K mayúscula.
     *
     * @param texto con o sin k minúscula
     * @return texto con K, si tenía k o K.
     */
    public String reemplazarK(String texto) {
        return texto.replace('k', 'K');
    }

    /**
     * Estrae lo dígitos y la K si se encuentra al final.
     *
     * @param texto el rut como texto
     * @return el rut sin digitos y sin k
     */
    public String extraerDigitos(String texto) {
        String stringDigitos = "";
        for (int i = 0; i < texto.length(); i++) {
            if (Character.isDigit(texto.charAt(i)) || (i == texto.length() - 1 && texto.charAt(i) == 'K')) {
                stringDigitos += texto.charAt(i);
            }
        }
        return stringDigitos;
    }

    /**
     * Formatea los dígitos de un texto como un RUT, insertando puntos y dígito
     * verificador.
     *
     * @param texto rut sin formato
     * @return rut formateado. Si no puede formatear, retorna texto original.
     */
    public String formatear(String texto) {

        LOG.debug("formateando texto: " + texto);

        //TODO (alguien) soportar rut menores a 1.000.000
        String textoDigitos = extraerDigitos(reemplazarK(quitarEspacioExtremos(texto)));
        String textoFormateado;

        try {
            textoFormateado = textoDigitos.substring(textoDigitos.length() - 1, textoDigitos.length());
            textoFormateado = "-" + textoFormateado;
            textoFormateado = textoDigitos.substring(textoDigitos.length() - 4, textoDigitos.length() - 1) + textoFormateado;
            textoFormateado = "." + textoFormateado;
            textoFormateado = textoDigitos.substring(textoDigitos.length() - 7, textoDigitos.length() - 4) + textoFormateado;
            textoFormateado = "." + textoFormateado;
            textoFormateado = textoDigitos.substring(0, textoDigitos.length() - 7) + textoFormateado;
            LOG.debug("Texto formateado correctamente: " + textoFormateado);
            return textoFormateado;
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            LOG.debug("No se pudo formatear texto");
            return texto;
        }
    }

    /**
     * Comprueba que un texto este formateado como RUT.
     *
     * @param texto rut a verificar
     * @return true si esta formateado, false de lo contrario
     */
    public boolean estaFormateado(String texto) {
        Matcher mat = Pattern.compile("^(\\d{1,3}\\.)?\\d{3}\\.\\d{3}-[\\dK]$").matcher(texto);
        return mat.find();
    }

    /**
     * Extrae entero en base a dígitos a la izquierda del guión, en un texto
     * formateado como RUT.
     *
     * @param texto extrae los numeros a la izquierda del guion de un rut
     * formateado
     * @return el rut representado como numero entero
     * @throws RutException si el rut no cumple formato
     */
    public int extraerEntero(String texto) throws RutException {
        if (estaFormateado(texto)) {
            texto = texto.split("-")[0].replace(".", "");
            return Integer.parseInt(texto);
        } else {
            throw new RutException("Texto no formateado como RUT (Ej.:22.222.222-2)");
        }
    }

    /**
     * Comprueba extrayendo verificador de un rut formateado correctamente.
     *
     * @param texto
     * @return
     * @throws RutException si el rut no cumple formato
     */
    public String extraerVerificador(String texto) throws RutException {
        if (estaFormateado(texto)) {
            return texto.split("-")[1];
        } else {
            throw new RutException("Texto no formateado como RUT (Ej.:22.222.222-2)");
        }
    }

    /**
     * Calcula digito verificador de RUT en base a un entero.
     *
     * @param entero
     * @return
     */
    public String calcularVerificador(int entero) {
        if (entero < RUT_MINIMO || entero > RUT_MAXIMO) {
            throw new IllegalArgumentException("Entero no esta entre los valores aceptados para RUT");
        }
        int M = 0, S = 1;
        for (; entero != 0; entero /= 10) {
            S = (S + entero % 10 * (9 - M++ % 6)) % 11;
        }
        return ((char) (S != 0 ? S + 47 : 75)) + "";
    }

    /**
     * Valida texto formateado como RUT comparando dígito verificador calculado
     * vs ingresado.
     *
     * @param texto texto en formato rut
     * @return true si el digito verificador es correcto, false de lo contrario
     * @throws RutException si el rut no cumple con formato
     */
    public boolean validar(String texto) throws RutException {
        entero = extraerEntero(texto);
        verificador = extraerVerificador(texto);
        String verificadorCalculado = calcularVerificador(entero);
        return verificador.equals(verificadorCalculado);
    }

    /**
     * Le da formato a un texto, agregando puntos y guión para que parezca RUT
     * (no valida verificador ni mínimos).
     *
     * @param texto Texto a formatear
     * @return Un nuevo String, con el rut formateado
     */
    public static String formatearRut(String texto) {

        LOG.debug("transformando:" + texto + " en formato rut");

        if (texto == null) {
            return "";
        }

        int cont = 0;
        String formateado;

        if (texto.length() > LARGO_MAXIMO_RUT_FORMATEADO) {
            return texto.substring(0, LARGO_MAXIMO_RUT_FORMATEADO);
        }

        switch (texto.length()) {
            case 0:
                return "";
            case 1:
                return texto;
            default:
                texto = texto.replace(".", "");
                texto = texto.replace("-", "");

                if (texto.isEmpty()) {
                    return texto;
                }

                formateado = "-" + texto.substring(texto.length() - 1);
                for (int i = texto.length() - 2; i >= 0; i--) {
                    formateado = texto.substring(i, i + 1) + formateado;
                    cont++;
                    if (cont == 3 && i != 0) {
                        formateado = "." + formateado;
                        cont = 0;
                    }
                }
                return formateado.toUpperCase();
        }
    }

    /**
     * Accede al rut como texto.
     *
     * @return el rut formateado
     */
    @Override
    public String toString() {
        return stringFormateado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.stringFormateado);
        hash = 61 * hash + Objects.hashCode(this.verificador);
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
        final RUT other = (RUT) obj;
        if (!Objects.equals(this.stringFormateado, other.stringFormateado)) {
            return false;
        }
        return Objects.equals(this.verificador, other.verificador);
    }

}
