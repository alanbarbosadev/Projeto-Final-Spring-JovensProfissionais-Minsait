package com.minsait.pessoasapp.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CriarPessoaDTO(@NotBlank String nome, String endereco, String cep, String cidade, String uf, Set<AdicionarContatoDTO> contatos) {
}
