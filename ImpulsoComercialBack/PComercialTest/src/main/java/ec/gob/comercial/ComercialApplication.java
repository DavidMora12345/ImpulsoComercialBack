package ec.gob.comercial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Clase principal de la aplicación Comercial
 * 
 * Sistema de gestión comercial, catastros y recaudación municipal
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "ec.gob.comercial")
@EntityScan(basePackages = "ec.gob.comercial")
public class ComercialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComercialApplication.class, args);
    }
}
