package colegiofacil.DAO.impl;

import colegiofacil.entities.AlumnoEntity;
import colegiofacil.model.Alumno;
import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.impl.ResultadoMetodoImpl;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Iván Torres Curinao
 */
public class AlumnoDAO extends EclipseLinkDAO {

    public static final Logger LOG = Logger.getLogger(AlumnoDAO.class);

    public AlumnoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Guardar un alumno.
     *
     * @param alumno
     * @return el resultado de la operación.
     */
    public ResultadoMetodo insertar(Alumno alumno) {
        LOG.debug("Guardando el alumno: " + alumno.toString() + ".");

        AlumnoEntity alumnoEntity = getEntity(alumno);
        // Guarda un alumno
        super.insertar(alumnoEntity);
        return ResultadoMetodoImpl.setSinError(1, "Alumno guardado correctamente.");
    }

    /**
     * Actualizar un alumno.
     *
     * @param alumno
     * @return el resultado de la operación.
     */
    public ResultadoMetodo actualizar(Alumno alumno) {
        LOG.debug("Actualizando el alumno: " + alumno.toString() + ".");

        AlumnoEntity alumnoEntity = getEntity(alumno);
        // Actualiza un alumno
        super.actualizar(alumnoEntity);
        return ResultadoMetodoImpl.setSinError(2, "Alumno actualizado correctamente.");
    }

    /**
     * Eliminar un alumno.
     *
     * @param alumno
     * @return el resultado de la operación.
     */
    public ResultadoMetodo eliminar(Alumno alumno) {
        LOG.debug("Eliminando el alumno: " + alumno.toString() + ".");

        AlumnoEntity alumnoEntity = getEntity(alumno);
        alumnoEntity = entityManager.find(alumnoEntity.getClass(), alumnoEntity.getId());
        if (alumnoEntity == null) {
            throw new IllegalArgumentException("AlumnoEntity nulo.");
        } else {
            // Elimina un alumno
            super.eliminar(alumnoEntity);
            return ResultadoMetodoImpl.setSinError();
        }
    }

    private AlumnoEntity getEntity(Alumno alumno) {
        AlumnoEntity alumnoEntity = new AlumnoEntity(alumno.getId(), alumno.getRut().toString(),
                alumno.getNombres().toString(), alumno.getApellidos().toString());

        return alumnoEntity;

    }

}
