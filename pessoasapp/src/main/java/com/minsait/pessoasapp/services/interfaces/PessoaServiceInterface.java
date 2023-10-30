package com.minsait.pessoasapp.services.interfaces;

import com.minsait.pessoasapp.dtos.PessoaMalaDiretaDTO;
import com.minsait.pessoasapp.models.Contato;
import com.minsait.pessoasapp.models.Pessoa;
import jakarta.annotation.Nullable;

import java.util.List;

public interface PessoaServiceInterface {
    List<Pessoa> getAll();
    Pessoa getById(Long id);
    PessoaMalaDiretaDTO getByIdMalaDireta(Long id);
    Pessoa add(Pessoa pessoa);
    Pessoa addContato(Long id, Contato contato);
    Pessoa update(Pessoa pessoa);
    void delete(Long id);
}
