package com.example.demo.service.mapper;

import com.example.demo.domain.Pessoa;
import com.example.demo.service.dto.PessoaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toEntity(PessoaDTO pessoaDTO);

    PessoaDTO toDto(Pessoa pessoa);
}
