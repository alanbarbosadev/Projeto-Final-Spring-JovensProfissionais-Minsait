package com.minsait.pessoasapp.services;

import com.minsait.pessoasapp.dtos.PessoaMalaDiretaDTO;
import com.minsait.pessoasapp.models.Contato;
import com.minsait.pessoasapp.models.Pessoa;
import com.minsait.pessoasapp.repositories.PessoaRepository;
import com.minsait.pessoasapp.services.exceptions.ResourceNotFoundException;
import com.minsait.pessoasapp.services.interfaces.PessoaServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PessoaService implements PessoaServiceInterface {

    private PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Contato> getAllContatosPessoa(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));
        return pessoa.getContatos();
    }

    @Override
    @Transactional(readOnly = true)
    public Pessoa getById(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));
        return pessoa;
    }

    @Override
    @Transactional
    public PessoaMalaDiretaDTO getByIdMalaDireta(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));
        return new PessoaMalaDiretaDTO(pessoa.getId(), pessoa.getNome(), pessoa.enderecoMalaDireta());
    }

    @Override
    @Transactional
    public Pessoa add(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    @Transactional
    public Pessoa addContato(Long id, Contato contato) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));
        pessoa.getContatos().add(contato);
        return pessoa;
    }

    @Override
    @Transactional
    public Pessoa update(Long id, Pessoa pessoa) {
        try {
            Pessoa pessoaAtualizada = pessoaRepository.getReferenceById(id);
            pessoaAtualizada.setNome(pessoa.getNome());
            pessoaAtualizada.setEndereco(pessoa.getEndereco());
            pessoaAtualizada.setCep(pessoa.getCep());
            pessoaAtualizada.setCidade(pessoa.getCidade());
            pessoaAtualizada.setUf(pessoa.getUf());
            return pessoaRepository.save(pessoaAtualizada);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada.");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            pessoaRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada.");
        }
    }
}
