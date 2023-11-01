package com.minsait.pessoasapp.dtos;

import java.util.Set;

public record PessoaDTO(Long id, String nome, String endereco, String cep, String cidade, String uf, Set<ContatoDTO> contatos) {
}
