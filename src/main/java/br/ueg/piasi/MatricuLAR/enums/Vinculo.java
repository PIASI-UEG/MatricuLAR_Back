package br.ueg.piasi.MatricuLAR.enums;

import java.util.Arrays;

public enum Vinculo {

    PAI ("P", "pai"),
    MAE ("M", "mãe"),
    TIO ("T", "tio/tia"),
    VIZINHO ("VZ", "vizinho/vizinha"),
    AVO ("AV", "avô/avó");


    private final String id;
    private final String descricao;

    Vinculo(final String id, final String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Vinculo getById(final String id) {
        return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
    }
}
