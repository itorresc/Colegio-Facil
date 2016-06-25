/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colegiofacil.DAO.impl;

import colegiofacil.DAO.PersistenceDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.PessimisticLock;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.jpa.JpaEntityManager;

/**
 *
 * @author itorres
 */
public class EclipseLinkDAO implements PersistenceDAO {

    private static final Logger log = Logger.getLogger(EclipseLinkDAO.class);

    protected EntityManager entityManager;
    private Query query;

    public EclipseLinkDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void eliminarCacheCompartida() {
        entityManager.unwrap(JpaEntityManager.class).getServerSession()
                .getIdentityMapAccessor().initializeAllIdentityMaps();
    }

    @Override
    public void insertar(Object object) {
        entityManager.persist(object);
    }

    @Override
    public Object actualizar(Object object) {
        return entityManager.merge(object);
    }

    @Override
    public void eliminar(Object object) {
        entityManager.remove(object);
    }

    /**
     * Crea consulta nativa.
     *
     * @param consulta
     */
    public void crearQueryNativa(String consulta) {
        query = entityManager.createNativeQuery(consulta);
    }

    /**
     * Crea consulta sin resultados máximos, refrescando los valores desde base
     * de datos y permitiendo lectura/escritura
     *
     * @param consulta
     */
    public void crearQueryTipica(String consulta) {
        crearQuery(consulta, 0, true, false);
    }

    /**
     * Crea consulta sin resultados máximos, refrescando los valores desde base
     * de datos y permitiendo solo lectura
     *
     * @param consulta
     */
    public void crearQueryTipicaSoloLectura(String consulta) {
        crearQuery(consulta, 0, true, true);
    }

    /**
     * Crea consulta con registros máximos, refrescando los valores desde base
     * de datos y permitiendo lectura/escritura
     *
     * @param consulta
     * @param registrosMaximos
     */
    public void crearQueryTipicaRegistrosMaximos(String consulta, int registrosMaximos) {
        crearQuery(consulta, registrosMaximos, true, false);
    }

    /**
     *
     * @param consulta
     * @param registrosMaximos
     * @param refrescar
     * @param soloLectura
     */
    public final void crearQuery(String consulta, int registrosMaximos, boolean refrescar, boolean soloLectura) {
        query = entityManager.createQuery(consulta);
        query.setMaxResults(registrosMaximos);
        if (refrescar) {
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
        }
        if (soloLectura) {
            query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        }
    }

    public void setParameter(String nombre, Object valor) {
        query.setParameter(nombre, valor);
    }

    public void setHint(String nombre, Object valor) {
        if (query == null) {
            throw new NullPointerException("Query nula");
        } else {
            query.setHint(nombre, valor);
        }
    }

    public Object getSingleResult() {
        if (query == null) {
            throw new NullPointerException("Query nula");
        } else {
            try {
                Object object = query.getSingleResult();
                log.debug("registro obtenido correctamente...");
                return object;
            } catch (NoResultException noResultException) {
                log.debug("registro no encontrado");
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List getResultList() {
        if (query == null) {
            throw new NullPointerException("Query nula");
        } else {
            List list = query.getResultList();
            log.debug("Registros obtenidos: " + list.size());
            return list;
        }
    }

    public void activarBloqueoPesimista() {
        if (query == null) {
            throw new NullPointerException("Query nula");
        } else {
            query.setHint(QueryHints.PESSIMISTIC_LOCK, PessimisticLock.Lock);
        }
    }

    public void setSoloLectura() {
        if (query == null) {
            throw new NullPointerException("Query nula");
        } else {
            query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        }
    }

    public void refrescar(Object object) {
        entityManager.refresh(object);
    }

    public int ejecutarActualizacion() {
        if (query == null) {
            throw new NullPointerException("Query nula");
        } else {
            int registrosActualizados = query.executeUpdate();
            log.debug("Registros actualizados: " + registrosActualizados);
            return registrosActualizados;
        }
    }
}
