package com.minsait.pessoasapp.resources;

import com.minsait.pessoasapp.dtos.PessoaMalaDiretaDTO;
import com.minsait.pessoasapp.models.Contato;
import com.minsait.pessoasapp.models.Pessoa;
import com.minsait.pessoasapp.services.PessoaService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pessoas")
public class PessoaResource {

    private PessoaService pessoaService;

    @Autowired
    public PessoaResource(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> getAll() {
        var pessoas = pessoaService.getAll();
        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable Long id) {
        var pessoa = pessoaService.getById(id);
        return ResponseEntity.ok().body(pessoa);

    }

    @GetMapping(value = "/maladireta/{id}")
    public ResponseEntity<PessoaMalaDiretaDTO> getPessoaMalaDiretaById(@PathVariable Long id) {
        var pessoaMalaDiretaDTO = pessoaService.getByIdMalaDireta(id);
        return ResponseEntity.ok().body(pessoaMalaDiretaDTO);

    }

    @PostMapping
    public ResponseEntity<Pessoa> addPessoa(@RequestBody Pessoa pessoa) {
        var pessoaInserida = pessoaService.add(pessoa);
        return ResponseEntity.ok().body(pessoaInserida);
    }

    @PostMapping(value = "/{id}/contatos")
    public ResponseEntity<Pessoa> addPessoa(@PathVariable Long id, @RequestBody Contato contato) {
        var pessoa = pessoaService.addContato(id, contato);
        return ResponseEntity.ok().body(pessoa);
    }
}
