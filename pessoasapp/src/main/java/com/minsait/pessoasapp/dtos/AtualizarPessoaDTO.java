package com.minsait.pessoasapp.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record AtualizarPessoaDTO(@NotBlank String nome, String endereco, String cep, String cidade, String uf) {
}
