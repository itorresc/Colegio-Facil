package colegiofacil.DAO.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Iván Torres Curinao
 */
public class FechaHoraDAO extends EclipseLinkDAO {

    private static final Logger LOG = Logger.getLogger(FechaHoraDAO.class);

    public FechaHoraDAO(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Obtener la fecha y hora actual.
     *
     * @return fecha y hora de tipo FechaHora
     */
    @SuppressWarnings("unchecked")
    public LocalDateTime getFechaHoraNow() {
        LOG.debug("Ejecutando consulta para obtener fecha actual desde el servidor.");
        crearQueryNativa("SELECT NOW()");

        return ((Timestamp) getSingleResult()).toLocalDateTime();
    }
}
