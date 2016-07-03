package colegiofacil.tipoDato;

import colegiofacil.DAO.ProcedimientoNoTransaccionalDAO;
import colegiofacil.DAO.impl.DAOManager;
import colegiofacil.validacion.impl.ValidadorIntMinMax;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * Clase encargada de implementar y manejar las fechas y horas.
 *
 * @author Iván Torres Curinao
 */
public class FechaHora {

    private static final int ANIO_MINIMO = 1;
    private static final int ANIO_MAXIMO = 2100;
    private static final int DIA_MINIMO = 1;
    private static final int HORA_MINIMA = 0;
    private static final int HORA_MAXIMA = 23;
    private static final int MINUTO_MINIMO = 0;
    private static final int MINUTO_MAXIMO = 59;

    private static final LocalDateTime FECHA_MAXIMA = LocalDateTime.of(
            ANIO_MAXIMO,
            Month.DECEMBER,
            Month.DECEMBER.maxLength(),
            HORA_MAXIMA,
            MINUTO_MAXIMO
    );

    private static final LocalDateTime FECHA_MINIMA = LocalDateTime.of(
            ANIO_MINIMO,
            Month.JANUARY,
            DIA_MINIMO,
            HORA_MINIMA,
            MINUTO_MINIMO
    );

    private static final Logger LOG = Logger.getLogger(Fecha.class);

    private final LocalDateTime fechaHora;

    private int dia;
    private int mes;
    private int año;
    private int hora;
    private int minuto;

    /**
     * Obtiene una instancia de tipo FechaHora a partir de los parametros
     * <P>
     * De esta manera se representa la fecha y hora.
     *
     * @param dia el día es representado, desde el 1 al 31
     * @param mes el mes es representado, desde 1 al 12 (Enero a Diciembre)
     * @param año el año es representado desde una AÑO_MINIMO a AÑO_MAXIMO (1 al
     * 2100)
     * @param hora la hora es representada, desde una HORA_MINIMA a HORA_MAXIMA
     * (0 al 23)
     * @param minuto el minuto es representado, desde un MINUTO_MINIMO a
     * MINUTO_MAXIMO (0 al 59)
     */
    public FechaHora(Integer dia, Integer mes, Integer año, Integer hora, Integer minuto) {
        if (dia == null) {
            throw new NullPointerException("Día nulo.");
        } else if (ValidadorIntMinMax.aplicar(dia, FECHA_MINIMA.getDayOfMonth(), FECHA_MAXIMA.getDayOfMonth()).isError()) {
            throw new IllegalArgumentException("Día no soportado.");
        } else {
            this.dia = dia;
        }
        if (mes == null) {
            throw new NullPointerException("Mes nulo.");
        } else if (ValidadorIntMinMax.aplicar(mes, FECHA_MINIMA.getMonthValue(), FECHA_MAXIMA.getMonthValue()).isError()) {
            throw new IllegalArgumentException("Mes no soportado.");
        } else {
            this.mes = mes;
        }
        if (año == null) {
            throw new NullPointerException("Año nulo.");
        } else if (ValidadorIntMinMax.aplicar(año, FECHA_MINIMA.getYear(), FECHA_MAXIMA.getYear()).isError()) {
            throw new IllegalArgumentException("Año no soportado.");
        } else {
            this.año = año;
        }
        if (hora == null) {
            throw new NullPointerException("Hora nula.");
        } else if (ValidadorIntMinMax.aplicar(hora, FECHA_MINIMA.getHour(), FECHA_MAXIMA.getHour()).isError()) {
            throw new IllegalArgumentException("Hora no soportado.");
        } else {
            this.hora = hora;
        }
        if (minuto == null) {
            throw new NullPointerException("Minuto nulo.");
        } else if (ValidadorIntMinMax.aplicar(minuto, FECHA_MINIMA.getMinute(), FECHA_MAXIMA.getMinute()).isError()) {
            throw new IllegalArgumentException("Minuto no soportado.");
        } else {
            this.minuto = minuto;
        }

        this.fechaHora = LocalDateTime.of(año, mes, dia, hora, minuto);
    }

    /**
     * Instancia un objeto FechaHora a partir de un tipo LocalDateTime.
     *
     * @param fechaHora es una fecha hora de tipo LocalDateTime
     */
    public FechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new NullPointerException("Fecha hora nula.");
        } else {
            this.fechaHora = fechaHora;
            this.dia = fechaHora.getDayOfMonth();
            this.mes = fechaHora.getMonthValue();
            this.año = fechaHora.getYear();
            this.hora = fechaHora.getHour();
            this.minuto = fechaHora.getMinute();
        }
    }

    /**
     * Instancia un objeto FechaHora a partir de un tipo Date.
     *
     * @param fecha es una fecha de tipo Date
     */
    public FechaHora(Date fecha) {
        if (fecha == null) {
            throw new NullPointerException("Fecha nula.");
        } else {
            this.fechaHora = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
            this.dia = fechaHora.getDayOfMonth();
            this.mes = fechaHora.getMonthValue();
            this.año = fechaHora.getYear();
            this.hora = fechaHora.getHour();
            this.minuto = fechaHora.getMinute();
        }
    }

    /**
     * Obtiene una instancia de tipo int con la representación del día.
     * <p>
     * El día es representado con los valores:
     * <p>
     * Del 1 al 31 (Enero - Marzo - Mayo - Julio - Agosto - Octubre -
     * Diciembre).
     * <p>
     * Del 1 al 30 (Abril - Junio - Septiembre - Noviembre).
     * <p>
     * Del 1 al 28 (Febrero), año bisiesto del 1 al 29.
     *
     * @return un int, representando el día, not null
     */
    public int getDia() {
        return dia;
    }

    /**
     * Obtiene una instancia de tipo int con la representación del mes.
     * <p>
     * El mes es representado con los valores del 1 al 12 (Enero a Diciembre).
     *
     * @return un int, representando el mes, not null
     */
    public int getMes() {
        return mes;
    }

    /**
     * Obtiene una instancia de tipo int con la representación del año.
     * <p>
     * El año es representado con un valor maximo 2100.
     *
     * @return un int, representado el año, not null
     */
    public int getAño() {
        return año;
    }

    /**
     * Obtiene una instancia de tipo int con la representación de la hora.
     * <p>
     * La hora es representado con los valores del 0 al 23.
     *
     * @return un int, representando la hora, not null
     */
    public int getHora() {
        return hora;
    }

    /**
     * Obtiene una instancia de tipo int con la representación del minuto.
     * <p>
     * El minuto es representado con los valores del 0 al 59.
     *
     * @return un int, representando el minuto, not null
     */
    public int getMinuto() {
        return minuto;
    }

    /**
     * Obtiene una instancia de tipo FechaHora desde el servidor con la fecha y
     * hora actual.
     *
     * @return una FechaHora, not null
     */
    public static FechaHora ahora() {

        LOG.debug("Obteniendo fecha hora desde el DAO.");

        ProcedimientoNoTransaccionalDAO procedimiento = new DAOManager();

        LocalDateTime fechaHoraAhora = (LocalDateTime) procedimiento.ejecutar((DAOManager daoManager) -> {
            return daoManager.getFechaHoraDAO().getFechaHoraNow();
        });

        return new FechaHora(
                fechaHoraAhora.getDayOfMonth(),
                fechaHoraAhora.getMonthValue(),
                fechaHoraAhora.getYear(),
                fechaHoraAhora.getHour(),
                fechaHoraAhora.getMinute()
        );
    }

    /**
     * Comprueba que la FechaHora no supere al máximo soportado 31/12/2100.
     *
     * @return true si el id es valido, false si no lo es
     */
    public boolean validar() {
        return fechaHora.isBefore(FECHA_MAXIMA);
    }

    /**
     * Obtiene una instancia de tipo Date a partir de una FechaHora dia/mes/año
     * hora:minuto.
     *
     * @return un Date de la fechaHora
     */
    public Date toDate() {
        ZonedDateTime zonaFechaHora = fechaHora.atZone(ZoneId.systemDefault());
        return Date.from(zonaFechaHora.toInstant());
    }

    /**
     * Obtiene una instancia de tipo LocalDateTime a partir de una FechaHora
     * dia/mes/año hora:minuto.
     *
     * @return un LocalDateTime de la fechaHora
     */
    public LocalDateTime toLocalDateTime() {
        ZonedDateTime zonaFechaHora = fechaHora.atZone(ZoneId.systemDefault());
        return LocalDateTime.from(zonaFechaHora);
    }

    /**
     * Obtiene una instancia fecha hora (dia/mes/año hora:minuto) de tipo String
     * a partir de una FechaHora.
     * <p>
     * dia (d) representado con los valores del 1 al 31.
     * <p>
     * mes (m) representado con los valores del 1 al 12 (Enero - Diciembre).
     * <p>
     * año (a) representado por un valor maximo de 2100.
     * <p>
     * hora (h) representado con los valores del 0 al 23.
     * <p>
     * minuto (m) representado con los valores del 0 al 59.
     *
     * @return un String con la fecha formateada, dd/mm/aaaa hh:mm
     * @see toStringFecha(), toStringHora()
     */
    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaHoraFormateada = fechaHora.format(formatter);
        return fechaHoraFormateada;
    }

    /**
     * Obtiene una instancia fecha (dia/mes/año) de tipo String a partir de una
     * FechaHora.
     * <p>
     * dia (d) representado con los valores del 1 al 31.
     * <p>
     * mes (m) representado con los valores del 1 al 12 (Enero - Diciembre).
     * <p>
     * año (a) representado por un valor maximo de 2100.
     * <p>
     *
     * @return un String con la fecha formateada, dd/mm/aaaa
     * @see toString(), toStringHora()
     */
    public String toStringFecha() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaHoraFormateada = fechaHora.format(formatter);
        return fechaHoraFormateada;
    }

    /**
     * Obtiene una instancia hora (hora:minuto) de tipo String a partir de una
     * FechaHora.
     * <p>
     * hora (h) representado con los valores del 0 al 23.
     * <p>
     * minuto (m) representado con los valores del 0 al 59.
     * <p>
     *
     * @return un String con la hora formateada, hh:mm
     * @see toString(), toStringFecha()
     */
    public String toStringHora() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String fechaHoraFormateada = fechaHora.format(formatter);
        return fechaHoraFormateada;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.año;
        hash = 43 * hash + this.mes;
        hash = 43 * hash + this.dia;
        hash = 43 * hash + this.hora;
        hash = 43 * hash + this.minuto;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FechaHora other = (FechaHora) obj;
        if (this.año != other.año) {
            return false;
        }
        if (this.mes != other.mes) {
            return false;
        }
        if (this.dia != other.dia) {
            return false;
        }
        if (this.hora != other.hora) {
            return false;
        }
        return this.minuto == other.minuto;
    }

}
