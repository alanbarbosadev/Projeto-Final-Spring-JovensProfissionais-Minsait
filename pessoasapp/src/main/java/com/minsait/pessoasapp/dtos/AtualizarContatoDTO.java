package com.minsait.pessoasapp.dtos;

import com.minsait.pessoasapp.models.enums.TipoContato;
import jakarta.validation.constraints.NotBlank;

public record AtualizarContatoDTO(@NotBlank TipoContato tipoContato, @NotBlank String contato) {
}
