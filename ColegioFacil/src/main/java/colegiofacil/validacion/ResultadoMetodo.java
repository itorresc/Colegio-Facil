/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colegiofacil.validacion;

/**
 *
 * @author itorres
 */
public interface ResultadoMetodo {

    public boolean isError();

    public int getCodigo();

    public String getMensaje();
}
