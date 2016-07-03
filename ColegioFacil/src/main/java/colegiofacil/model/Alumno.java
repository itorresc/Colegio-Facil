package colegiofacil.model;

import colegiofacil.tipoDato.Correo;
import colegiofacil.tipoDato.Fecha;
import colegiofacil.tipoDato.RUT;
import colegiofacil.tipoDato.Sexo;
import colegiofacil.tipoDato.Texto;

/**
 *
 * @author Iván Torres Curinao
 */
public class Alumno extends Usuario {

    public Alumno(RUT rut, Texto nombres, Texto apellidos, Fecha fechaNacimiento,
            Texto telefonoMovil, Correo correo, Sexo sexo) {
        super(
                rut,
                nombres,
                apellidos,
                fechaNacimiento,
                telefonoMovil,
                correo,
                sexo
        );
    }

}
