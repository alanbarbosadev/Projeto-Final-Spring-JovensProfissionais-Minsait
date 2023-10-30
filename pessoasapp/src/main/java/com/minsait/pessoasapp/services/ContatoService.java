package com.minsait.pessoasapp.services;

import com.minsait.pessoasapp.models.Contato;
import com.minsait.pessoasapp.repositories.ContatoRepository;
import com.minsait.pessoasapp.services.exceptions.ResourceNotFoundException;
import com.minsait.pessoasapp.services.interfaces.ContatoServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ContatoService implements ContatoServiceInterface {
    private ContatoRepository contatoRepository;

    @Autowired
    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Contato getById(Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);
        Contato contato = contatoOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhum contato com esse ID foi encontrado."));
        return contato;
    }

    @Override
    @Transactional(readOnly = true)
    public Contato update(Long id, Contato contato) {
        try {
            Contato contatoAtualizado = contatoRepository.getReferenceById(id);
            contatoAtualizado.setTipoContato(contato.getTipoContato());
            contatoAtualizado.setContato(contato.getContato());
            return contatoRepository.save(contatoAtualizado);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Nenhum contato com esse ID foi encontrado.");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            contatoRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Nenhum contato com esse ID foi encontrado.");
        }
    }
}
