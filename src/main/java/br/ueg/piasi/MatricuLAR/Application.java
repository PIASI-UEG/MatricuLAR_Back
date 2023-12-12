package br.ueg.piasi.MatricuLAR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {
        "br.ueg.piasi.*",
        "br.ueg.prog.webi.*" //Para funcionamento da Arquitetura
})
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class},
        basePackages = {
                "br.ueg.piasi.*",
                "br.ueg.prog.webi.api.*" //Para funcionamento da Arquitetura
        })

public class Application {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}
