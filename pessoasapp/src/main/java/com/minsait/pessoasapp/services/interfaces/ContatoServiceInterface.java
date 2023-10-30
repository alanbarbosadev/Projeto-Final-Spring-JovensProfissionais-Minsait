package com.minsait.pessoasapp.services.interfaces;

import com.minsait.pessoasapp.dtos.PessoaMalaDiretaDTO;
import com.minsait.pessoasapp.models.Pessoa;

import java.util.List;
import java.util.Optional;

public interface ContatoServiceInterface {
    List<Pessoa> getAll();
    Optional<Pessoa> getById(Long id);
    Optional<PessoaMalaDiretaDTO> getByIdMalaDireta(Long id);
    Pessoa update(Pessoa pessoa);
    void delete(Long id);
    Pessoa save(Pessoa pessoa);
}
