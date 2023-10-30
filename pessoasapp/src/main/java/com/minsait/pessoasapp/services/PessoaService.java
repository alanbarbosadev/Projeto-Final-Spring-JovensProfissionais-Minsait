package com.minsait.pessoasapp.services;

import com.minsait.pessoasapp.dtos.PessoaMalaDiretaDTO;
import com.minsait.pessoasapp.models.Contato;
import com.minsait.pessoasapp.models.Pessoa;
import com.minsait.pessoasapp.repositories.ContatoRepository;
import com.minsait.pessoasapp.repositories.PessoaRepository;
import com.minsait.pessoasapp.services.exceptions.ResourceNotFoundException;
import com.minsait.pessoasapp.services.interfaces.PessoaServiceInterface;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService implements PessoaServiceInterface {

    private PessoaRepository pessoaRepository;
    private ContatoRepository contatoRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, ContatoRepository contatoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.contatoRepository = contatoRepository;
    }

    @Override
    @Transactional
    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    @Override
    @Transactional
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
        if(pessoa.getContatos() != null) {
            for(Contato contato : pessoa.getContatos()) {
                contato = contatoRepository.save(contato);
            }
        }
        return pessoaRepository.save(pessoa);
    }

    @Override
    @Transactional
    public Pessoa addContato(Long id, Contato contato) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));
        contato = contatoRepository.save(contato);
        pessoa.getContatos().add(contato);
        return pessoa;
    }

    @Override
    @Transactional
    public Pessoa update(Pessoa pessoa) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
