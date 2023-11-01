package com.minsait.pessoasapp.services.interfaces;

import com.minsait.pessoasapp.dtos.*;

import java.util.List;
import java.util.Set;

public interface PessoaServiceInterface {
    List<PessoaDTO> getAll();
    Set<ContatoDTO> getAllContatosPessoa(Long id);
    PessoaDTO getById(Long id);
    PessoaMalaDiretaDTO getByIdMalaDireta(Long id);
    PessoaDTO add(CriarPessoaDTO criarPessoaDTO);
    PessoaDTO addContato(Long id, AdicionarContatoDTO adicionarContatoDTO);
    PessoaDTO update(Long id, AtualizarPessoaDTO atualizarPessoaDTO);
    void delete(Long id);
}
