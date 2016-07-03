package colegiofacil.validacion.impl;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.Validador;

/**
 * Valida que número de tipo long esté entre un valor mínimo y un valor máximo.
 *
 * @author Cristián Alarcón de la Maza
 */
public class ValidadorLongMinMax implements Validador {

    long min, max;

    private ValidadorLongMinMax(long min, long max) {
        this.min = min;
        this.max = max;
    }

    public static ResultadoMetodo aplicar(long numero, long min, long max) {
        return new ValidadorLongMinMax(min, max).validar(numero);
    }

    @Override
    public ResultadoMetodo validar(Object object) {
        long numero = (Long) object;
        if (min > numero) {
            return ResultadoMetodoImpl.setError("Número menor a mínimo permitido");
        } else if (max < numero) {
            return ResultadoMetodoImpl.setError("Número mayor a máximo permitido");
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

}
