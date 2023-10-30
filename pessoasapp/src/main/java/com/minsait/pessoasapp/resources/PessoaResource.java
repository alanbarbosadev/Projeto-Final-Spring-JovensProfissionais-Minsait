package com.minsait.pessoasapp.resources;

import com.minsait.pessoasapp.dtos.PessoaMalaDiretaDTO;
import com.minsait.pessoasapp.models.Contato;
import com.minsait.pessoasapp.models.Pessoa;
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
    public ResponseEntity<List<Pessoa>> getAll() {
        var pessoas = pessoaService.getAll();
        return ResponseEntity.ok().body(pessoas);
    }

    @Operation(summary = "Retorna a pessoa cujo ID é igual ao especificado. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable Long id) {
        var pessoa = pessoaService.getById(id);
        return ResponseEntity.ok().body(pessoa);

    }

    @Operation(summary = "Retorna todos os contatos da pessoa cujo ID é igual ao especificado. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @GetMapping(value = "/{id}/contatos")
    public ResponseEntity<Set<Contato>> getContatosPessoa(@PathVariable Long id) {
        var contatos = pessoaService.getAllContatosPessoa(id);
        return ResponseEntity.ok().body(contatos);
    }

    @Operation(summary = "Retorno a pessoa em modelo de mala direta (id, nome, endereço específico concatenado) cujo ID é igual ao especificado. Caso alguma propriedade do endereço específico seja nula, uma String vazia será retornada em seu lugar.")
    @GetMapping(value = "/maladireta/{id}")
    public ResponseEntity<PessoaMalaDiretaDTO> getPessoaMalaDiretaById(@PathVariable Long id) {
        var pessoaMalaDiretaDTO = pessoaService.getByIdMalaDireta(id);
        return ResponseEntity.ok().body(pessoaMalaDiretaDTO);

    }

    @Operation(summary = "Cria o registro de uma pessoa no banco de dados. Observação: Não incluir o ID da pessoa e do contato na requisição, pois essas propriedades serão gerenciadas pelo banco de dados automaticamente.")
    @PostMapping
    public ResponseEntity<Pessoa> addPessoa(@RequestBody Pessoa pessoa) {
        pessoa = pessoaService.add(pessoa);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoa.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pessoa);
    }

    @Operation(summary = "Adiciona um novo contato a pessoa cujo ID é igual ao especificado. Observação: Não incluir o ID do contato na requisição, pois essa propriedade será gerenciada automaticamente pelo banco de dados.")
    @PostMapping(value = "/{id}/contatos")
    public ResponseEntity<Pessoa> addPessoa(@PathVariable Long id, @RequestBody Contato contato) {
        var pessoa = pessoaService.addContato(id, contato);
        return ResponseEntity.ok().body(pessoa);
    }

    @Operation(summary = "Atualiza o registro da pessoa cujo ID é igual ao especificado. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        pessoa = pessoaService.update(id, pessoa);
        return ResponseEntity.ok().body(pessoa);
    }

    @Operation(summary = "Deleta o registro da pessoa cujo ID é igual ao especificado, bem como os contatos associados a ele. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
