package colegiofacil.tipoDato;

import colegiofacil.DAO.ProcedimientoNoTransaccionalDAO;
import colegiofacil.DAO.impl.DAOManager;
import colegiofacil.model.excepciones.FechaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 *
 * @author Iván Torres Curinao
 * @author Omar Paché
 */
public class Fecha {

    private static final Logger LOG = Logger.getLogger(Fecha.class);

    public static final LocalDate FECHA_MAXIMA_SOPORTADA = LocalDate.of(2100, Month.DECEMBER, 31);

    private LocalDate fecha;

    /**
     * Instancia un objeto Fecha a partir de un tipo Date.
     *
     * @param fecha fecha de tipo Date
     * @throws NullPointerException si el parametro fecha es nulo
     */
    public Fecha(Date fecha) {
        if (fecha == null) {
            throw new NullPointerException("Fecha nula");
        } else {
            this.fecha = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault()).toLocalDate();
        }
    }

    /**
     * Instancia un objeto Fecha a partir de un texto.
     *
     * @param fecha fecha como texto en formato "dd-MM-yyyy"
     * @throws NullPointerException si el parametro fecha es nulo
     * @throws FechaException si el formato de la fecha recibida es inválido
     */
    public Fecha(String fecha) throws FechaException {
        if (fecha == null) {
            throw new NullPointerException("Fecha nula");
        } else {
            try {
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                this.fecha = LocalDate.parse(fecha, formateador);
            } catch (Exception e) {
                throw new FechaException("Formato fecha recibida no valido");
            }
        }
    }

    /**
     * Instancia un objeto Fecha a partir de un tipo LocalDateTime.
     *
     * @param fechaHora es una fecha hora de tipo LocalDateTime
     * @throws NullPointerException si el parametro fechaHora es nulo
     */
    public Fecha(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new NullPointerException("Fecha hora nula.");
        } else {
            this.fecha = fechaHora.toLocalDate();
        }
    }

    /**
     * Obtiene una instancia de tipo FechaHora desde el servidor con la fecha y
     * hora actual.
     *
     * @return una Fecha, not null
     */
    public static Fecha ahora() {
        // Danger!! Si existe fecha seteada en el archivo de propiedades se usara esta como la actual
//        if (Global.fechaActual != null) {
//            LOG.debug("Obteniendo fecha actual desde el archivo de propiedades.");
//            return Global.fechaActual;
//        }

        LOG.debug("Obteniendo fecha hora desde el DAO.");

        ProcedimientoNoTransaccionalDAO procedimiento = new DAOManager();
        LocalDateTime fechaHoraAhora = (LocalDateTime) procedimiento.ejecutar((DAOManager daoManager) -> {
            return daoManager.getFechaHoraDAO().getFechaHoraNow();
        });

        return new Fecha(fechaHoraAhora);
    }

    /**
     * Le suma los dias recibidos a la fecha actual.
     *
     * @param dias dias a sumar a la fecha
     */
    public void sumarDias(int dias) {
        fecha = fecha.plusDays(dias);
    }

    /**
     * Calcula la diferencia en dias entre la fecha actual y una fecha recibida.
     *
     * @param otraFecha fecha para restar
     * @return la cantidad de dias que los separan
     */
    public long diasEntre(Fecha otraFecha) {
        return ChronoUnit.DAYS.between(fecha, otraFecha.toLocalDate());
    }

    /**
     * Evalúa si la fecha actual es posterior a la recibida.
     *
     * @param otraFecha la fecha a comparar
     * @return true si es mayor, false de lo contrario
     */
    public boolean esMayorQue(Fecha otraFecha) {
        return fecha.isAfter(otraFecha.toLocalDate());
    }

    /**
     * Evalúa si la fecha actual es igual a la recibida.
     *
     * @param otraFecha la fecha a comparar
     * @return true si es igual, false de lo contrario
     */
    public boolean esIgualQue(Fecha otraFecha) {
        return fecha.isEqual(otraFecha.toLocalDate());
    }

    /**
     * Evalúa si la fecha actual es anterior a la recibida.
     *
     * @param otraFecha la fecha a comparar
     * @return true si es menor, false de lo contrario
     */
    public boolean esMenorQue(Fecha otraFecha) {
        return fecha.isBefore(otraFecha.toLocalDate());
    }

    /**
     * Evalúa si la la fecha es menor a la fecha actual y la fecha fin mayor a
     * la fecha actual.
     *
     * @param fechaFin es la fecha de tope a comparar
     * @return true si la fecha actual esta entre las fechas de inicio y fin,
     * false de lo contrario
     */
    public boolean entreActual(Fecha fechaFin) {
        Fecha fechaActual = Fecha.ahora();
        if (fecha.isEqual(fechaActual.toLocalDate())) {
            return true;
        }
        if (fecha.isBefore(fechaActual.toLocalDate())) {
            if (fechaFin.esMayorQue(fechaActual)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Comprueba que la fecha no supere al máximo soportado 31/12/2100.
     *
     * @return true si el id es valido, false si no lo es
     */
    public boolean validar() {
        return fecha.isBefore(FECHA_MAXIMA_SOPORTADA);
    }

    /**
     * Retorna la representación de la fecha como tipo Date.
     *
     * @return La fecha en tipo Date
     */
    public Date toDate() {
        return Date.from(fecha.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Retorna la representación de la fecha como tipo LocalDate.
     *
     * @return La fecha en tipo Date
     */
    public LocalDate toLocalDate() {
        return fecha;
    }

    @Override
    public String toString() {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaHoraFormateada = fecha.format(formateador);
        return fechaHoraFormateada;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.fecha);
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
        final Fecha other = (Fecha) obj;
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

}
