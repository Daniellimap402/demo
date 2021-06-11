package com.example.demo.web.rest;

import com.example.demo.domain.Pessoa;
import com.example.demo.service.PessoaService;
import com.example.demo.service.dto.PessoaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pessoas")
@CrossOrigin({"*"})
public class PessoaResource {

    @Autowired
    private PessoaService service;

    @PostMapping
    public ResponseEntity<Pessoa> salvar(@RequestBody @Valid PessoaDTO pessoaDto) {
        service.salvar(pessoaDto);
        return ResponseEntity.ok().build();
    }
}
