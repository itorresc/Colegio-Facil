package colegiofacil.tipoDato;

import colegiofacil.validacion.ResultadoMetodo;
import colegiofacil.validacion.impl.ResultadoMetodoImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.log4j.Logger;

/**
 *
 * @author Cristián Alarcón de la Maza
 * @author Iván Torres Curinao
 * @author Omar Paché
 */
public final class GranDecimal extends BigDecimal {

    // Digitos GranDecimalImpl
    public final static int DIGITOS_MAX_CORTO = 12;
    public final static int DIGITOS_MAX_LARGO = 18;
    public final static int DECIMALES_ESTANDAR = 4;
    public final static int DECIMALES_CORTOS = 2;
    public final static GranDecimal UNO = new GranDecimal(ONE);
    public final static GranDecimal DIEZ = new GranDecimal(convertir(10));
    public final static GranDecimal CIEN = new GranDecimal(convertir(100));
    public final static GranDecimal CERO = new GranDecimal(ZERO);
    public final static GranDecimal IVA = new GranDecimal("0.19");

    private static final Logger LOG = Logger.getLogger(GranDecimal.class);

    public GranDecimal(String val) {
        super(val);
    }

    public GranDecimal(BigDecimal bd) {
        this(bd.toString());
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(toString());
    }

    public double toDouble() {
        return doubleValue();
    }

    public int toInt() {
        return intValue();
    }

    /**
     * Convierte un número entero en un gran decimal.
     *
     * @param numero
     * @return
     */
    public static GranDecimal convertir(int numero) {
        return new GranDecimal(Integer.toString(numero));
    }

    /**
     * Multiplica dos factores de tipo GranDecimal.
     *
     * @param factor es un factor de tipo GranDecimal
     * @return un producto de la operación
     */
    public GranDecimal multiplicar(GranDecimal factor) {
        return new GranDecimal(multiply(factor.toBigDecimal()));
    }

    /**
     * Divide un dividendo con un divisor de tipo GranDecimal redondeando el
     * cociente sin decimales.
     *
     * @param divisor es el divisor de tipo GranDecimal
     * @return un cociente de la operación sin decimales
     */
    public GranDecimal dividirSinDecimales(GranDecimal divisor) {
        return new GranDecimal(divide(divisor.toBigDecimal(), RoundingMode.UP));
    }

    /**
     * Divide un dividendo con un divisor de tipo GranDecimal redondeando el
     * cociente con los decimales estándar.
     *
     * @param divisor es el divisor de tipo GranDecimal
     * @return un cociente de la operación sin decimales
     */
    public GranDecimal dividir(GranDecimal divisor) {
        return new GranDecimal(divide(divisor.toBigDecimal(), DECIMALES_ESTANDAR, RoundingMode.HALF_UP));
    }

    /**
     * Suma dos sumando de tipo GranDecimal.
     *
     * @param sumando es un sumando de tipo GranDecimal
     * @return el total de la operación
     */
    public GranDecimal sumar(GranDecimal sumando) {
        return new GranDecimal(add(sumando.toBigDecimal()));
    }

    /**
     * Resta un minuendo y sustraendo de tipo GranDecimal.
     *
     * @param sustraendo es el sustraendo de tipo GranDecimal
     * @return la diferencia entre el minuendo y el sustraendo
     */
    public GranDecimal restar(GranDecimal sustraendo) {
        return new GranDecimal(subtract(sustraendo.toBigDecimal()));
    }

    /**
     * Redondea un decimal hacia arriba, a la cantidad de decimales máximos
     * utilizados en el sistema (4).
     *
     * @return
     */
    public GranDecimal redondearAcuatroDigitos() {
        return new GranDecimal(setScale(DECIMALES_ESTANDAR, RoundingMode.HALF_UP));
    }

    public static String formatearDigitosMoneda(GranDecimal value) {
        Locale locCL = new Locale("es", "CO");    // Colombia
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(locCL);
        moneyFormat.setMaximumFractionDigits(0);
        moneyFormat.setRoundingMode(RoundingMode.HALF_UP);
        Number num = (Number) value;
        String text = moneyFormat.format(num);
        return text;
    }

    /**
     * Redondea un decimal hacia arriba, a la cantidad de decimales máximos
     * utilizados en el sistema (4).
     *
     * @return
     */
    public GranDecimal redondearAdosDigitos() {
        return new GranDecimal(setScale(DECIMALES_CORTOS, RoundingMode.HALF_UP));
    }

    /**
     * Redondea un valor según el redondeo recibido. EJ: Si está configurado en
     * 10 el redondeo, el monto $14536.49 se transformaría en $14540
     *
     * @param valorRedondeo redondeo a usar
     * @return el GranDecimal redondeado
     */
    public GranDecimal redondearA(int valorRedondeo) {
        BigDecimal redondeo = BigDecimal.valueOf(valorRedondeo);

        GranDecimal valor = redondearSinDecimales();
        GranDecimal resto = new GranDecimal(valor.remainder(redondeo));

        if (resto.esPositivo()) {
            valor = valor.sumar(new GranDecimal(redondeo.subtract(resto)));
        }

        return valor;
    }

    /**
     * Redondea un decimal hacia arriba quitándole todos sus decimales.
     *
     * @return el valor del del decimal sin decimales
     */
    public GranDecimal redondearSinDecimales() {
        return new GranDecimal(setScale(0, RoundingMode.HALF_UP));
    }

    /**
     * Retorna la cantidad total de digitos del número, incluyendo sus decimales
     * representativos
     *
     * @return
     */
    public int contarDigitos() {
        int largo = stripTrailingZeros().toPlainString().replace(".", "").length();
        return largo == 0 ? 1 : largo;
    }

    /**
     * Retorna cantidad digitos decimales representativos
     *
     * @return
     */
    public int contarDecimales() {
        String string = stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

    /**
     * Valida un numero en base a 2 tipos de clasificacion, largo o corto.
     *
     * @param corto
     * @return
     */
    public ResultadoMetodo validarDigitos(boolean corto) {
        LOG.info("Validando dígitos permitidos para grandecimal " + (corto ? "corto" : "largo"));
        int digitosPermitidos = corto ? DIGITOS_MAX_CORTO : DIGITOS_MAX_LARGO;
        if (contarDigitos() > digitosPermitidos) {
            String mensaje = "Cantidad de digitos supera el máximo establecido";
            LOG.info(mensaje);
            return ResultadoMetodoImpl.setError(mensaje);
        } else if (contarDecimales() > DECIMALES_ESTANDAR) {
            String mensaje = "Cantidad de decimales supera el máximo establecido";
            LOG.info(mensaje);
            return ResultadoMetodoImpl.setError(mensaje);
        } else {
            LOG.info("dígitos validados positivamente");
            return ResultadoMetodoImpl.setSinError();
        }
    }

    /**
     * Comprueba si un GranDecimal es positivo.
     *
     * @return true si es positivo de lo contrario false
     */
    public boolean esPositivo() {
        return signum() == 1;
    }

    /**
     * Comprueba si un GranDecimal es negativo.
     *
     * @return true si es positivo de lo contrario false
     */
    public boolean esNegativo() {
        return signum() == -1;
    }

    /**
     * Comprueba si un GranDecimal es cero.
     *
     * @return true si es positivo de lo contrario false
     */
    public boolean esCero() {
        return signum() == 0;
    }

    /**
     * Trasnforma un monto ingresado como texto a un GranDecimal
     *
     * @param monto monto como texto, el separador decimal puede ser la coma o
     * el punto
     * @return
     */
    public static GranDecimal crearDesdeTexto(String monto) {
        if (monto.isEmpty()) {
            monto = "0";
        }
        String montoDecimal = monto.replace(",", ".");
        return new GranDecimal(montoDecimal);
    }

    /**
     * Retorna el valor decimal representado como un porcentaje.
     *
     * @return el porcentaje. Ej si el valor decimal 0,06 devuelve 6.00%.
     */
    public String toPorcentajeString() {
        return multiplicar(CIEN).toString().concat("%");
    }
}
