package colegiofacil.validacion.impl;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.Validador;

/**
 *
 * @author Iván Torres Curinao
 */
public class ValidadorIntMin implements Validador {

    int min;

    private ValidadorIntMin(int min) {
        this.min = min;
    }

    public static ResultadoMetodo aplicar(int numero, int min) {
        return new ValidadorIntMin(min).validar(numero);
    }

    @Override
    public ResultadoMetodo validar(Object object) {
        long numero = (Integer) object;
        if (numero < min) {
            return ResultadoMetodoImpl.setError("Número menor a mínimo permitido");
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

}
