package br.ueg.piasi.MatricuLAR.enums;

import java.util.Arrays;

public enum StatusMatricula {
    ATIVO("A","Ativo"),
    INATIVO("I","Inativo");

    private final String id;
    private final String descricao;

    StatusMatricula(final String id, final String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    public static StatusMatricula getById(final String id) {
        return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
    }

}