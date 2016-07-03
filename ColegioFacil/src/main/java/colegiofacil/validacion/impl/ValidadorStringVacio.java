package colegiofacil.validacion.impl;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.Validador;

/**
 * Valida que cadena texto no sea nula ni esté vacía.
 *
 * @author itorres
 */
public class ValidadorStringVacio implements Validador {

    private ValidadorStringVacio() {
    }

    public static ResultadoMetodo aplicar(String texto) {
        return new ValidadorStringVacio().validar(texto);
    }

    @Override
    public ResultadoMetodo validar(Object object) {
        String texto = (String) object;
        if (ValidadorNulo.aplicar(texto).isError()) {
            return ValidadorNulo.aplicar(texto);
        } else if (texto.isEmpty()) {
            return ResultadoMetodoImpl.setError("Texto vacío");
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

}
