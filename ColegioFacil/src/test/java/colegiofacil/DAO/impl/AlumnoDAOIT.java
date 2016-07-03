package colegiofacil.DAO.impl;

import colegiofacil.DAO.ProcedimientoTransaccionalDAO;
import colegiofacil.model.Alumno;
import colegiofacil.model.excepciones.FechaException;
import colegiofacil.model.excepciones.RutException;
import colegiofacil.tipoDato.Correo;
import colegiofacil.tipoDato.Fecha;
import colegiofacil.tipoDato.RUT;
import colegiofacil.tipoDato.Sexo;
import colegiofacil.tipoDato.Texto;
import colegiofacil.utilities.Global;
import colegiofacil.validacion.ResultadoMetodo;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.eclipse.persistence.jpa.JpaEntityManager;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author itorres
 */
public class AlumnoDAOIT {

    public AlumnoDAOIT() {
    }

    @BeforeClass
    public static void globalSetUp() {
//        Global.dataBase = "colegioFacil";
//        Global.dataBaseUrl = "jdbc:mysql://localhost";
//        Global.dataBasePort = 3306;
        Global.emf = Persistence.createEntityManagerFactory("ColegioFacilPU");
        Connection mysqlCon = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/colegioFacil", "root", "toor");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Before
    public void setUp() {
        EntityManager entityManager = Global.emf.createEntityManager();
        entityManager.unwrap(JpaEntityManager.class).getServerSession().getIdentityMapAccessor().initializeAllIdentityMaps();
    }

    @Test
    public void testGetId() {
        RUT rut = new RUT();
        try {
            rut = new RUT("15222325-0");
        } catch (RutException ex) {
        }
        Texto nombres = new Texto("Ivan Alejandro");
        Texto apellidos = new Texto("Torres Curinao");
        try {
            Fecha fechaNacimiento = new Fecha("15/06/2016");

            Texto telefonoMovil = new Texto("96783324");
            Correo correo = new Correo("itorresc@outlook.com");

            Alumno alumno = new Alumno(rut, nombres, apellidos, fechaNacimiento, telefonoMovil, correo, Sexo.FEMENINO);
            alumno.setId((short) 1);
            ProcedimientoTransaccionalDAO consulta = new DAOManager();

            Object objeto = consulta.transaccion(
                    (DAOManager daoManager) -> {
                        return daoManager.getAlumnoDAO().insertar(alumno);
                    }
            );
            if (objeto instanceof ResultadoMetodo) {
                assertEquals(((ResultadoMetodo) objeto).getCodigo(), 1);
            }
        } catch (FechaException ex) {
        }
    }

}
