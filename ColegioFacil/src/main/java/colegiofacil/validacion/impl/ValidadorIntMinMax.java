package colegiofacil.validacion.impl;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.Validador;

/**
 *
 * @author Iván Torres Curinao
 */
public class ValidadorIntMinMax implements Validador {

    int min;
    int max;

    private ValidadorIntMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static ResultadoMetodo aplicar(int numero, int min, int max) {
        return new ValidadorIntMinMax(min, max).validar(numero);
    }

    @Override
    public ResultadoMetodo validar(Object object) {
        long numero = (Integer) object;
        if (numero < min) {
            return ResultadoMetodoImpl.setError("Número menor a mínimo permitido");
        } else if (numero > max) {
            return ResultadoMetodoImpl.setError("Número mayor a maximo permitido");
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

}
