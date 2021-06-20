package com.example.demo.service;

import com.example.demo.repository.PessoaRepository;
import com.example.demo.service.dto.PessoaDTO;
import com.example.demo.service.mapper.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@CrossOrigin({"*"})
public class PessoaService {

    @Autowired
    private  PessoaRepository repository;

    @Autowired
    private  PessoaMapper mapper;

    public List<PessoaDTO> listar(){
        return this.mapper.toDto(this.repository.findAll());
    }

}
