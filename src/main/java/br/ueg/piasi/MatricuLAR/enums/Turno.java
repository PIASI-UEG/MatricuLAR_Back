package br.ueg.piasi.MatricuLAR.enums;

import java.util.Arrays;

public enum Turno {

   MATUTINO("M", "Matutino"),
   VESPERTINO("V", "Vespertino"),
   INTEGRAL("I", "Integral");

    private final String id;

    private final String descricao;

    Turno(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return descricao;
    }

    public String getDescricao() {
        return id;
    }

    public static Turno getById(final String id) {
        return Arrays.stream(values()).filter
                (value -> value.getId().equals(id)).findFirst().orElse(null);
    }
}
