package utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {
        public static String actualDate() {
            // Obtener la hora actual
            LocalTime horaActual = LocalTime.now();

            // Definir el formato deseado
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");

            // Formatear y mostrar la hora actual
            return horaActual.format(formatoHora);
        }
}
