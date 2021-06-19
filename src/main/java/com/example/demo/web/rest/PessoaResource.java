package com.example.demo.web.rest;

import com.example.demo.service.PessoaService;
import com.example.demo.service.dto.PessoaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pessoas")
@CrossOrigin({"*"})
public class PessoaResource {

    @Autowired
    private PessoaService service;

    @GetMapping("")
    public String login(){
        return "teste autenticacao";
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<PessoaDTO>> listar(){
        return ResponseEntity.ok(this.service.listar());
    }
}
