package colegiofacil.DAO.impl;

import colegiofacil.DAO.ProcedimientoNoTransaccionalDAO;
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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.eclipse.persistence.jpa.JpaEntityManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        Global.dataBase = "colegioFacil";
        Global.dataBaseUrl = "jdbc:mysql://localhost";
        Global.dataBasePort = 3306;
        Global.emf = Persistence.createEntityManagerFactory("ColegioFacilPU");
    }

    @Before
    public void setUp() {
        EntityManager entityManager = Global.emf.createEntityManager();
        entityManager.unwrap(JpaEntityManager.class).getServerSession().getIdentityMapAccessor().initializeAllIdentityMaps();
    }

//    @Test
    public void testGetId() {
        RUT rut = new RUT();
        try {
            rut = new RUT("15222325-0");
        } catch (RutException ex) {
        }
        Texto nombres = new Texto("Ivan Alejandro");
        Texto apellidos = new Texto("Torres Curinao");
        try {
            Fecha fechaNacimiento = new Fecha("15-06-2016");

            Texto telefonoMovil = new Texto("96783324");
            Correo correo = new Correo("itorresc@outlook.com");

            Alumno alumno = new Alumno(rut, nombres, apellidos, fechaNacimiento, telefonoMovil, correo, Sexo.FEMENINO);
            alumno.setId((short) 2);
            ProcedimientoTransaccionalDAO procedimiento = new DAOManager();

            Object objeto = procedimiento.transaccion((DAOManager daoManager) -> {
                return daoManager.getAlumnoDAO().insertar(alumno);
            }
            );
            if (objeto instanceof ResultadoMetodo) {
                assertEquals(((ResultadoMetodo) objeto).getCodigo(), 1);
            }
        } catch (FechaException ex) {
        }
    }

    /**
     * Comprobar que la fecha hora obtenida desde el servidor no es nula.
     */
    @Test
    public void test_comprobarFechaDesdeServidor_OK() {
        ProcedimientoNoTransaccionalDAO consulta = new DAOManager();

        List<Alumno> alumnoList = (List<Alumno>) consulta.ejecutar((DAOManager DAOManager) -> {
            return DAOManager.getAlumnoDAO().getAlumno();
        });

        assertNotNull(alumnoList);
    }

}
