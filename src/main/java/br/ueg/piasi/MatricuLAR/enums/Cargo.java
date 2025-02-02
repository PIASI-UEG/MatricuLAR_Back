package br.ueg.piasi.MatricuLAR.enums;

import java.util.Arrays;

public enum Cargo {

    ADMIN("ROLE_A","Administrador"),
    SECRETARIA("ROLE_S","Secretaria"),
    DIRETORA("ROLE_D", "Diretora"),
    COORDENADORA("ROLE_C", "Coordenadora");

    private final String id;
    private final String descricao;

    Cargo(final String id, final String descricao){
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

    public static Cargo getById(final String id) {
        return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
    }
}
