package com.minsait.pessoasapp.resources;

import com.minsait.pessoasapp.dtos.*;
import com.minsait.pessoasapp.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/pessoas")
public class PessoaResource {

    private PessoaService pessoaService;

    @Autowired
    public PessoaResource(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @Operation(summary = "Retorna todas as pessoas cadastradas.")
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> getAll() {
        var pessoas = pessoaService.getAll();
        return ResponseEntity.ok().body(pessoas);
    }

    @Operation(summary = "Retorna a pessoa cujo ID é igual ao especificado. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Long id) {
        var pessoaDTO = pessoaService.getById(id);
        return ResponseEntity.ok().body(pessoaDTO);

    }

    @Operation(summary = "Retorna todos os contatos da pessoa cujo ID é igual ao especificado. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @GetMapping(value = "/{id}/contatos")
    public ResponseEntity<Set<ContatoDTO>> getContatosPessoa(@PathVariable Long id) {
        var contatosDTO = pessoaService.getAllContatosPessoa(id);
        return ResponseEntity.ok().body(contatosDTO);
    }

    @Operation(summary = "Retorno a pessoa em modelo de mala direta (id, nome, endereço específico concatenado) cujo ID é igual ao especificado. Caso as propriedades Cidade ou UF do endereço específico sejam nulas, uma String vazia será retornada no lugar delas. No caso do endereço ou CEP serem nulos, uma mensagem de Endereço Indisponível será retornada.")
    @GetMapping(value = "/maladireta/{id}")
    public ResponseEntity<PessoaMalaDiretaDTO> getPessoaMalaDiretaById(@PathVariable Long id) {
        var pessoaMalaDiretaDTO = pessoaService.getByIdMalaDireta(id);
        return ResponseEntity.ok().body(pessoaMalaDiretaDTO);

    }

    @Operation(summary = "Cria o registro de uma pessoa no banco de dados. Observação: Opções (TELEFONE ou CELULAR)")
    @PostMapping
    public ResponseEntity<PessoaDTO> addPessoa(@RequestBody CriarPessoaDTO criarPessoaDTO) {
        var pessoaDTO = pessoaService.add(criarPessoaDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoaDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(pessoaDTO);
    }

    @Operation(summary = "Adiciona um novo contato a pessoa cujo ID é igual ao especificado. Observação: Opções (TELEFONE ou CELULAR)")
    @PostMapping(value = "/{id}/contatos")
    public ResponseEntity<PessoaDTO> addContatoPessoa(@PathVariable Long id, @RequestBody AdicionarContatoDTO adicionarContatoDTO) {
        var pessoaDTO = pessoaService.addContato(id, adicionarContatoDTO);
        return ResponseEntity.ok().body(pessoaDTO);
    }

    @Operation(summary = "Atualiza o registro da pessoa cujo ID é igual ao especificado. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(@PathVariable Long id, @RequestBody AtualizarPessoaDTO atualizarPessoaDTO) {
        var pessoaAtualizada = pessoaService.update(id, atualizarPessoaDTO);
        return ResponseEntity.ok().body(pessoaAtualizada);
    }

    @Operation(summary = "Deleta o registro da pessoa cujo ID é igual ao especificado, bem como os contatos associados a ele. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
