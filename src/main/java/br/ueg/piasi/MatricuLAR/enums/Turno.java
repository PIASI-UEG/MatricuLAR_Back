package br.ueg.piasi.MatricuLAR.enums;

import java.util.Arrays;

public enum Turno {

   MATUTINO("M", "Matutino"),
   VESPERTINO("V", "Vespertino"),
   NOTURNO("N", "Noturno");

    private final String id;

    private final String descricao;

    Turno(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Turno getById(final String id) {
        return Arrays.stream(values()).filter
                (value -> value.getId().equals(id)).findFirst().orElse(null);
    }
}
