package com.minsait.pessoasapp.services;

import com.minsait.pessoasapp.dtos.AtualizarContatoDTO;
import com.minsait.pessoasapp.dtos.ContatoDTO;
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
    public ContatoDTO getById(Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);
        Contato contato = contatoOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhum contato com esse ID foi encontrado."));
        return mapContatoParaContatoDTO(contato);
    }

    @Override
    @Transactional
    public ContatoDTO update(Long id, AtualizarContatoDTO atualizarContatoDTO) {
        try {
            Contato contatoAtualizado = contatoRepository.getReferenceById(id);
            contatoAtualizado.setTipoContato(atualizarContatoDTO.tipoContato());
            contatoAtualizado.setContato(atualizarContatoDTO.contato());
            contatoAtualizado = contatoRepository.save(contatoAtualizado);

            return mapContatoParaContatoDTO(contatoAtualizado);
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

    private ContatoDTO mapContatoParaContatoDTO(Contato contato) {
        return new ContatoDTO(
                contato.getId(),
                contato.getTipoContato(),
                contato.getContato());
    }
}
