package com.minsait.pessoasapp.services.interfaces;

import com.minsait.pessoasapp.dtos.PessoaMalaDiretaDTO;
import com.minsait.pessoasapp.models.Contato;
import com.minsait.pessoasapp.models.Pessoa;

import java.util.List;
import java.util.Set;

public interface PessoaServiceInterface {
    List<Pessoa> getAll();
    Set<Contato> getAllContatosPessoa(Long id);
    Pessoa getById(Long id);
    PessoaMalaDiretaDTO getByIdMalaDireta(Long id);
    Pessoa add(Pessoa pessoa);
    Pessoa addContato(Long id, Contato contato);
    Pessoa update(Long id, Pessoa pessoa);
    void delete(Long id);
}
