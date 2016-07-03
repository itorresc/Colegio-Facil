package colegiofacil.validacion.impl;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.Validador;

/**
 *
 * @author Omar Paché
 */
public class ValidadorIntPositivo implements Validador {

    private ValidadorIntPositivo() {
    }

    public static ResultadoMetodo aplicar(int numero) {
        return new ValidadorIntPositivo().validar(numero);
    }

    @Override
    public ResultadoMetodo validar(Object object) {
        Integer numero = (Integer) object;
        if (numero <= 0) {
            return ResultadoMetodoImpl.setError("Número menor a mínimo permitido.");
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

}
