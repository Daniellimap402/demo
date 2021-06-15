package com.example.demo.configuration;


import com.example.demo.domain.Permissao;
import com.example.demo.repository.PermissaoRepository;
import com.example.demo.service.enumerations.PermissoesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PermissoesConfiguration {

    private final PermissaoRepository repository;
    private static final Long NENHUM_CADASTRO = 0L;

    @Bean
    void cadastrarPermissoes(){
        if(NENHUM_CADASTRO.equals(repository.count())) {
            Permissao permissao = new Permissao();
            permissao.setNome(PermissoesEnum.USUARIO);
            repository.save(permissao);
        }
    }

}
