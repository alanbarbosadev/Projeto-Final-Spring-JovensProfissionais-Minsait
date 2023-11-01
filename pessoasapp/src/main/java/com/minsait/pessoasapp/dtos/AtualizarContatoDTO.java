package com.minsait.pessoasapp.dtos;

import com.minsait.pessoasapp.models.enums.TipoContato;

public record AtualizarContatoDTO(TipoContato tipoContato, String contato) {
}
