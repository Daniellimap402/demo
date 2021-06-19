package com.example.demo.service.mapper;

import com.example.demo.configuration.security.services.UserDetailsImpl;
import com.example.demo.domain.Pessoa;
import com.example.demo.service.dto.PessoaDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toEntity(PessoaDTO pessoaDTO);

    PessoaDTO toDto(Pessoa pessoa);

    List<PessoaDTO> toDto(List<Pessoa> pessoas);

    @Mapping(source = "nome", target = "username")
    @Mapping(source = "senha", target = "password")
    UserDetailsImpl toUserDetails(Pessoa pessoa);

    @AfterMapping
    default void mapearPermissoesPessoaUserDetails(@MappingTarget UserDetailsImpl userDetails, Pessoa pessoa){
        List<GrantedAuthority> authorities = pessoa.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        userDetails.setAuthorities(authorities);
    }

}
