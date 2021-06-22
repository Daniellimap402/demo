package com.example.demo.web.rest;

import com.example.demo.service.PessoaService;
import com.example.demo.service.dto.PessoaDTO;
import com.example.demo.service.error.NegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService service;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<List<PessoaDTO>> listar(){
        return ResponseEntity.ok(this.service.listar());
    }

    @PatchMapping("/confirmar/{email}")
    public ResponseEntity<Void> verificarEmail(@PathVariable String email) throws NegocioException {
        service.verificarEmail(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/recuperar/{email}")
    public ResponseEntity<Void> recuperarSenha(@PathVariable String email) throws NegocioException {
        service.recuperarSenha(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@RequestParam(value = "id") Long id, @RequestParam(value = "senha") String senha) throws NegocioException {
        service.alterarSenha(id, senha);
        return ResponseEntity.ok().build();
    }
}
