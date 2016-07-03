package colegiofacil.DAO.impl;

import colegiofacil.DAO.ProcedimientoNoTransaccionalDAO;
import colegiofacil.utilities.Global;
import java.time.LocalDateTime;
import javax.persistence.Persistence;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Iván Torres Curinao
 */
public class FechaHoraDAOIT {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    public FechaHoraDAOIT() {
    }

    @Before
    public void setUp() {
        Global.dataBase = "colegioFacil";
        Global.dataBaseUrl = "jdbc:mysql://localhost";
        Global.dataBasePort = 3306;
        Global.emf = Persistence.createEntityManagerFactory("ColegioFacilPU");
    }

    /**
     * Comprobar que la fecha hora obtenida desde el servidor no es nula.
     */
    @Test
    public void test_comprobarFechaDesdeServidor_OK() {
        System.out.print("test_comprobarFechaDesdeServidor_OK");
        ProcedimientoNoTransaccionalDAO consulta = new DAOManager();

        LocalDateTime fechaHoraResultado = (LocalDateTime) consulta.ejecutar((DAOManager DAOManager) -> {
            return DAOManager.getFechaHoraDAO().getFechaHoraNow();
        });

        assertNotNull(fechaHoraResultado);
    }
}
