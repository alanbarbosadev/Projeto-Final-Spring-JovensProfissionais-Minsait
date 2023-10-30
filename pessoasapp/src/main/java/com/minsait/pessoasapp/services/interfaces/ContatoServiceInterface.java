package com.minsait.pessoasapp.services.interfaces;

import com.minsait.pessoasapp.models.Contato;

public interface ContatoServiceInterface {
    Contato getById(Long id);
    Contato update(Long id, Contato contato);
    void delete(Long id);
}
