package com.minsait.pessoasapp.resources;

import com.minsait.pessoasapp.models.Contato;
import com.minsait.pessoasapp.services.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/contatos")
public class ContatoResource {
    private ContatoService contatoService;

    @Autowired
    public ContatoResource(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @Operation(summary = "Retorna o contato cujo ID é igual ao especificado. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Contato> getContatoById(@PathVariable Long id) {
        var contato = contatoService.getById(id);
        return ResponseEntity.ok().body(contato);
    }

    @Operation(summary = "Atualiza o registro do contato cujo ID é igual ao especificado. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Contato> updateContato(@PathVariable Long id, @RequestBody Contato contato) {
        contato = contatoService.update(id, contato);
        return ResponseEntity.ok().body(contato);
    }

    @Operation(summary = "Deleta o registro do contato cujo ID é igual ao especificado, bem como os contatos associados a ele. Caso não haja nenhum registro com o ID especificado, será lançada uma Excepção do tipo ResourceNotFoundException.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        contatoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
