package colegiofacil.tipoDato;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.impl.ResultadoMetodoImpl;
import colegiofacil.validacion.impl.ValidadorStringLargoMax;
import colegiofacil.validacion.impl.ValidadorStringLargoMin;
import colegiofacil.validacion.impl.ValidadorStringLargoMinMax;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Omar Paché
 * @author itorres
 */
public class Texto {

    private String texto;

    /**
     * Corta espacios en los extremos y convierte todos las letras a mayúscula,
     * luego asigna.
     *
     * @param texto contenido del texto
     */
    public Texto(String texto) {
        if (texto == null) {
            throw new NullPointerException("Texto nulo.");
        } else {
            this.texto = texto.trim().toUpperCase();
        }
    }

    /**
     * Comprueba si texto no supera largo recibido por parámetro.
     *
     * @param maximo cantidad máxima de caracteres que puede tener el texto
     * @return error si el texto supera el largo permitido, sin error de lo
     * contrario
     */
    public ResultadoMetodo validarLargo(int maximo) {
        if (ValidadorStringLargoMax.aplicar(texto, maximo).isError()) {
            return ValidadorStringLargoMax.aplicar(texto, maximo);
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

    /**
     * Comprueba si texto es mayor e igual al minimo recibido por parámetro.
     *
     * @param minimo es el minimo de caracteres que debe tener una cadena de
     * String
     * @return Error si el String en menor al minimo y Sin Error si el String es
     * mayor
     */
    public ResultadoMetodo validarLargoMinimo(int minimo) {
        if (ValidadorStringLargoMin.aplicar(texto, minimo).isError()) {
            return ValidadorStringLargoMin.aplicar(texto, minimo);
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

    /**
     * Comprueba si largo de texto esta entre mínimo y máximo recibidos como
     * parámetro.
     *
     * @param minimo cantidad mínima de caracteres que debe contener el texto
     * @param maximo cantidad máxima de caracteres que debe contener el texto
     * @return error si el texto no cumple con alguna restricción de largo, sin
     * error de lo contrario
     */
    public ResultadoMetodo validarLargo(int minimo, int maximo) {
        if (ValidadorStringLargoMinMax.aplicar(texto, minimo, maximo).isError()) {
            return ValidadorStringLargoMinMax.aplicar(texto, minimo, maximo);
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

    /**
     * Formatea la cadena de texto como título. La letra inicial de cada palara
     * con Mayúscula soportando puntos y comas Ej: "Hola Mundo, Java.".
     *
     * @return un string formateado como titulo
     */
    public String aTitulo() {
        char[] caracteres = texto.toLowerCase().toCharArray();
        if (caracteres.length == 0) {
            return "";
        }

        caracteres[0] = Character.toUpperCase(caracteres[0]);
        for (int i = 0; i < texto.length() - 2; i++) {
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
            }
        }
        return new String(caracteres);
    }

    /**
     * Comprueba si un texto es de tipo numerico.
     *
     * @return true si el texto es numerico de lo contrario false
     */
    public boolean esNumero() {
        return (this.toString().matches("[+-]?\\d*(\\.\\d+)?") && this.toString().equals("") == false);
    }

    /**
     * Une dos texto en uno solo.
     *
     * @param textoConcatenar texto a unir
     * @return el texto unido
     */
    public Texto concatenar(Texto textoConcatenar) {
        return new Texto(texto.concat(" " + textoConcatenar.toString()));
    }

    /**
     * Comprueba si el texto esta vacío.
     *
     * @return true si el texto esta vacío de lo contrario false
     */
    public boolean estaVacio() {
        return texto.isEmpty();
    }

    /**
     * Formatea la cadena de texto como título. La letra inicial de cada palara
     * con Mayúscula Ej: "Este Es Mi Titulo".
     *
     * @return texto formateado
     */
    @SuppressWarnings("unchecked")
    public String toTitle() {
        texto = texto.toLowerCase();
        List listaDeLetras = new ArrayList<>();

        for (int i = 0; i < texto.length(); i++) {
            listaDeLetras.add(texto.substring(i, (i + 1)));
        }

        String titulo = "";
        String letra;
        String letraPrevia = " ";
        Iterator iter = listaDeLetras.iterator();

        while (iter.hasNext()) {
            letra = (String) iter.next();
            if (letraPrevia.equals(" ") == true) {
                titulo += letra.toUpperCase();
            } else {
                titulo += letra;
            }
            letraPrevia = letra;
        }
        return titulo;
    }

    @Override
    public String toString() {
        return texto;
    }
}
