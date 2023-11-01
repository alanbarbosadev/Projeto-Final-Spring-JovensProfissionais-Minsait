package com.minsait.pessoasapp.dtos;

import com.minsait.pessoasapp.models.enums.TipoContato;

public record ContatoDTO(Long id, TipoContato tipoContato, String contato) {
}
