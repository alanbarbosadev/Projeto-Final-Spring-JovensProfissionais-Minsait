package com.minsait.pessoasapp.services;

import com.minsait.pessoasapp.dtos.*;
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
import java.util.stream.Collectors;

@Service
public class PessoaService implements PessoaServiceInterface {

    private PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PessoaDTO> getAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        return pessoas
                .stream()
                .map(x -> mapPessoaParaPessoaDTO(x))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ContatoDTO> getAllContatosPessoa(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));

        return pessoa
                .getContatos()
                .stream()
                .map(x -> mapContatoParaContatoDTO(x)).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public PessoaDTO getById(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));

        return mapPessoaParaPessoaDTO(pessoa);
    }

    @Override
    @Transactional(readOnly = true)
    public PessoaMalaDiretaDTO getByIdMalaDireta(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));

        return new PessoaMalaDiretaDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.enderecoMalaDireta());
    }

    @Override
    @Transactional
    public PessoaDTO add(CriarPessoaDTO criarPessoaDTO) {
        var pessoa = new Pessoa();

        pessoa.setNome(criarPessoaDTO.nome());
        pessoa.setEndereco(criarPessoaDTO.endereco());
        pessoa.setCep(criarPessoaDTO.cep());
        pessoa.setCidade(criarPessoaDTO.cidade());
        pessoa.setUf(criarPessoaDTO.uf());

        pessoa = pessoaRepository.save(pessoa);

        pessoa.getContatos().clear();
        for(AdicionarContatoDTO adicionarContatoDTO : criarPessoaDTO.contatos()) {
            pessoa.getContatos().add(new Contato(adicionarContatoDTO.tipoContato(), adicionarContatoDTO.contato()));
            pessoa = pessoaRepository.save(pessoa);
        }

        return mapPessoaParaPessoaDTO(pessoa);
    }

    @Override
    @Transactional
    public PessoaDTO addContato(Long id, AdicionarContatoDTO adicionarContatoDTO) {

        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa com esse ID foi encontrada."));

        var contato = new Contato(adicionarContatoDTO.tipoContato(), adicionarContatoDTO.contato());

        pessoa.getContatos().add(contato);
        pessoa = pessoaRepository.save(pessoa);

        return mapPessoaParaPessoaDTO(pessoa);
    }

    @Override
    @Transactional
    public PessoaDTO update(Long id, AtualizarPessoaDTO atualizarPessoaDTO) {
        try {
            Pessoa pessoaAtualizada = pessoaRepository.getReferenceById(id);

            pessoaAtualizada.setNome(atualizarPessoaDTO.nome());
            pessoaAtualizada.setEndereco(atualizarPessoaDTO.endereco());
            pessoaAtualizada.setCep(atualizarPessoaDTO.cep());
            pessoaAtualizada.setCidade(atualizarPessoaDTO.cidade());
            pessoaAtualizada.setUf(atualizarPessoaDTO.uf());
            pessoaAtualizada =  pessoaRepository.save(pessoaAtualizada);

            return mapPessoaParaPessoaDTO(pessoaAtualizada);
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

    private PessoaDTO mapPessoaParaPessoaDTO(Pessoa pessoa) {
        return new PessoaDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getEndereco(),
                pessoa.getCep(),
                pessoa.getCidade(),
                pessoa.getUf(),
                pessoa.getContatos()
                        .stream()
                        .map(x -> new ContatoDTO(
                                x.getId(),
                                x.getTipoContato(),
                                x.getContato()))
                        .collect(Collectors.toSet()));
    }

    private ContatoDTO mapContatoParaContatoDTO(Contato contato) {
        return new ContatoDTO(
                contato.getId(),
                contato.getTipoContato(),
                contato.getContato());
    }
}
