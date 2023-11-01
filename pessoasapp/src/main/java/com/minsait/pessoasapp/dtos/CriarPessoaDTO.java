package com.minsait.pessoasapp.dtos;

import java.util.Set;

public record CriarPessoaDTO(String nome, String endereco, String cep, String cidade, String uf, Set<AdicionarContatoDTO> contatos) {
}
