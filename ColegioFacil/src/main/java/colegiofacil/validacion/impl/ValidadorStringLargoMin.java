package colegiofacil.validacion.impl;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.Validador;

/**
 *
 * @author Iván Torres Curinao
 */
public class ValidadorStringLargoMin implements Validador {

    int largoMin;

    private ValidadorStringLargoMin(int largoMin) {
        this.largoMin = largoMin;
    }

    public static ResultadoMetodo aplicar(String texto, int largoMin) {
        return new ValidadorStringLargoMin(largoMin).validar(texto);
    }

    @Override
    public ResultadoMetodo validar(Object object) {
        String texto = (String) object;
        if (ValidadorStringVacio.aplicar(texto).isError()) {
            return ValidadorStringVacio.aplicar(texto);
        } else if (largoMin <= texto.length()) {
            return ResultadoMetodoImpl.setSinError();
        } else {
            return ResultadoMetodoImpl.setError("Largo de texto inferior al mínimo permitido (" + largoMin + ").");
        }
    }
}
