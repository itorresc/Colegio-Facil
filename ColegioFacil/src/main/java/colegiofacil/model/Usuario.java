package colegiofacil.model;

import colegiofacil.tipoDato.Correo;
import colegiofacil.tipoDato.Fecha;
import colegiofacil.tipoDato.RUT;
import colegiofacil.tipoDato.Sexo;
import colegiofacil.tipoDato.Texto;
import java.util.Objects;

/**
 *
 * @author Iván Torres Curinao
 */
public abstract class Usuario {

    //codigos de error para atributos a validar
    public static final int RUT = 1;
    public static final int NOMBRE = 2;
    public static final int APELLIDO = 3;
    public static final int FONO = 4;
    public static final int EMAIL = 5;
    //minimos y maximos a validar
    public static final int LARGO_MINIMO = 3;
    public static final int LARGO_MAXIMO = 20;

    protected short id;
    protected RUT rut;
    protected Texto nombres;
    protected Texto apellidos;
    protected Fecha fechaNacimiento;
    protected Texto telefono;
    protected Texto telefonoMovil;
    protected Correo correo;
    protected Sexo sexo;

    //Constantes que definen largo máximo de los campos para la clase
    private final int LARGO_RUT_MINIMO = 11;
    private final int LARGO_RUT_MAXIMO = 13;
    private final int LARGO_NOMBRE_APELLIDO = 45;
    private final int LARGO_FONO = 15;
    private final int LARGO_MAIL = 45;

    public Usuario(RUT rut, Texto nombres, Texto apellidos) {
        this.rut = rut;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Usuario(RUT rut, Texto nombres, Texto apellidos, Fecha fechaNacimiento, Texto telefonoMovil, Correo correo, Sexo sexo) {
        this.rut = rut;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoMovil = telefonoMovil;
        this.correo = correo;
        this.sexo = sexo;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public RUT getRut() {
        return rut;
    }

    public void setRut(RUT rut) {
        this.rut = rut;
    }

    public Texto getNombres() {
        return nombres;
    }

    public void setNombres(Texto nombres) {
        this.nombres = nombres;
    }

    public Texto getApellidos() {
        return apellidos;
    }

    public void setApellidos(Texto apellidos) {
        this.apellidos = apellidos;
    }

    public Fecha getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Fecha fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Texto getTelefono() {
        return telefono;
    }

    public void setTelefono(Texto telefono) {
        this.telefono = telefono;
    }

    public Texto getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(Texto telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Correo getCorreo() {
        return correo;
    }

    public void setCorreo(Correo correo) {
        this.correo = correo;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        hash = 43 * hash + Objects.hashCode(this.rut);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.rut, other.rut);
    }

}
