package com.minsait.pessoasapp.services.interfaces;

import com.minsait.pessoasapp.dtos.AtualizarContatoDTO;
import com.minsait.pessoasapp.dtos.ContatoDTO;

public interface ContatoServiceInterface {
    ContatoDTO getById(Long id);
    ContatoDTO update(Long id, AtualizarContatoDTO atualizarContatoDTO);
    void delete(Long id);
}
