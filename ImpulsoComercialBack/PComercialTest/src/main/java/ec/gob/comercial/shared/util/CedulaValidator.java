package ec.gob.comercial.shared.util;

/**
 * Validador de cédula ecuatoriana
 * 
 * Algoritmo módulo 10
 */
public class CedulaValidator {
    
    /**
     * Valida una cédula ecuatoriana
     * 
     * @param cedula Cédula a validar (10 dígitos)
     * @return true si es válida, false si no
     */
    public static boolean validar(String cedula) {
        if (cedula == null || cedula.length() != 10) {
            return false;
        }
        
        try {
            // Validar que sean solo números
            Long.parseLong(cedula);
            
            // Validar provincia (primeros 2 dígitos)
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            if (provincia < 1 || provincia > 24) {
                return false;
            }
            
            // Validar tercer dígito (debe ser < 6)
            int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
            if (tercerDigito >= 6) {
                return false;
            }
            
            // Algoritmo módulo 10
            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int suma = 0;
            
            for (int i = 0; i < 9; i++) {
                int digito = Integer.parseInt(cedula.substring(i, i + 1));
                int producto = digito * coeficientes[i];
                
                if (producto >= 10) {
                    producto -= 9;
                }
                
                suma += producto;
            }
            
            int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));
            int resultado = suma % 10;
            int valorEsperado = resultado == 0 ? 0 : 10 - resultado;
            
            return digitoVerificador == valorEsperado;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Valida un RUC ecuatoriano
     * 
     * @param ruc RUC a validar (13 dígitos)
     * @return true si es válido, false si no
     */
    public static boolean validarRuc(String ruc) {
        if (ruc == null || ruc.length() != 13) {
            return false;
        }
        
        // Los primeros 10 dígitos deben ser una cédula válida
        String cedula = ruc.substring(0, 10);
        if (!validar(cedula)) {
            return false;
        }
        
        // Los últimos 3 dígitos deben ser "001"
        String establecimiento = ruc.substring(10, 13);
        return establecimiento.equals("001");
    }
}
