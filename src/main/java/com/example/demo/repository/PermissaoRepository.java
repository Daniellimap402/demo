package com.example.demo.repository;

import com.example.demo.domain.Permissao;
import com.example.demo.service.enumerations.PermissoesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao,Long> {

    Optional<Permissao> findByNome(PermissoesEnum name);
}
