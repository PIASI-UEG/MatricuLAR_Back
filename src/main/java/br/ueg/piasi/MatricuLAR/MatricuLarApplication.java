package br.ueg.piasi.MatricuLAR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = {
		"br.ueg.piasi.MatricuLAR.*",
		"br.ueg.prog.webi.*" //Para funcionamento da Arquitetura
})
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class},
		basePackages = {
				"br.ueg.piasi.MatricuLAR.*",
				"br.ueg.prog.webi.api.*" //Para funcionamento da Arquitetura
		})
@EnableWebSecurity
public class MatricuLarApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatricuLarApplication.class, args);
	}

}
