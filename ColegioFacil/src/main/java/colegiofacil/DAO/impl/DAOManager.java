/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colegiofacil.DAO.impl;

import colegiofacil.DAO.ProcedimientoDAO;
import colegiofacil.DAO.ProcedimientoNoTransaccionalDAO;
import colegiofacil.DAO.ProcedimientoTransaccionalDAO;
import colegiofacil.utilities.Global;
import colegiofacil.utilities.Utils;
import colegiofacil.validacion.impl.ResultadoMetodoImpl;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.RollbackException;
import org.apache.log4j.Logger;

/**
 *
 * @author itorres
 */
public class DAOManager implements ProcedimientoNoTransaccionalDAO, ProcedimientoTransaccionalDAO {

    public static final Logger log = Logger.getLogger(DAOManager.class);

    protected final EntityManager entityManager;

    public DAOManager() {
        entityManager = Global.emf.createEntityManager();
        entityManager.setFlushMode(FlushModeType.COMMIT);
    }

    @Override
    public Object ejecutar(ProcedimientoDAO procedimientoDAO) {
        log.debug("ejecutando procedimiento no transaccional...");
        try {
            Object ret = procedimientoDAO.ejecutar(this);
            log.debug("procedimiento ejecutado correctamente...");
            return ret;
        } catch (Exception exception) {
//            Funciones.crearLogExcepcion(exception);
            return ResultadoMetodoImpl.setError("Error al ejecutar procedimiento en base de datos");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Object transaccion(ProcedimientoDAO procedimientoDAO) {
        log.debug("iniciando transacción...");
        entityManager.getTransaction().begin();
        try {
            Object retorno = procedimientoDAO.ejecutar(this);
            entityManager.getTransaction().commit();
            log.debug("transacción ejecutada correctamente...");
            return retorno;
        } catch (RollbackException rollbackException) {
            log.debug(rollbackException.getMessage());
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return ResultadoMetodoImpl.setError(rollbackException.getMessage());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Utils.crearLogExcepcion(e);
            return ResultadoMetodoImpl.setError("Error al ejecutar transacción en base de datos");
        } finally {
            entityManager.close();
        }
    }
}
