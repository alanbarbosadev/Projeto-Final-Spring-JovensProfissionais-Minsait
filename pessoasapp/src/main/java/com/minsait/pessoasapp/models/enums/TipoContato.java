package com.minsait.pessoasapp.models.enums;

public enum TipoContato {
    TELEFONE(0),
    CELULAR(1);

    private int codigo;
    TipoContato(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoContato valorCodigo(int codigo) {
        for(TipoContato tipoContato : TipoContato.values()) {
            if (codigo == tipoContato.getCodigo()) {
                return tipoContato;
            }
        }
        throw new IllegalArgumentException("Por favor, insira um tipo de contato válido! (0 - Telefone, 1 - Celular)");
    }
}
