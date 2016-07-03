package colegiofacil.validacion.impl;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.Validador;

/**
 * Valida que un texto tenga un largo superior a un determinado minimo e
 * inferior a un determinado máximo.
 *
 * @author Cristián Alarcón de la Maza
 */
public class ValidadorStringLargoMinMax implements Validador {

    private final int largoMin;
    private final int largoMax;

    private ValidadorStringLargoMinMax(int largoMin, int largoMax) {
        this.largoMin = largoMin;
        this.largoMax = largoMax;
    }

    public static ResultadoMetodo aplicar(String texto, int largoMin, int largoMax) {
        return new ValidadorStringLargoMinMax(largoMin, largoMax).validar(texto);
    }

    @Override
    public ResultadoMetodo validar(Object object) {
        String texto = (String) object;
        if (ValidadorStringLargoMax.aplicar(texto, largoMax).isError()) {
            return ValidadorStringLargoMax.aplicar(texto, largoMax);
        } else if (largoMin > texto.length()) {
            return ResultadoMetodoImpl.setError("Largo de texto inferior al mínimo permitido (" + largoMin + ").");
        } else {
            return ResultadoMetodoImpl.setSinError();
        }
    }

}
