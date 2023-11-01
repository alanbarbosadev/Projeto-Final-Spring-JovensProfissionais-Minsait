package com.minsait.pessoasapp.dtos;

import com.minsait.pessoasapp.models.enums.TipoContato;

public record AdicionarContatoDTO(TipoContato tipoContato, String contato) {
}
