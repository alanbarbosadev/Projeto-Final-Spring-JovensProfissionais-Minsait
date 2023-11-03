package com.minsait.pessoasapp.dtos;

import com.minsait.pessoasapp.models.enums.TipoContato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarContatoDTO(@NotNull TipoContato tipoContato, @NotBlank String contato) {
}
