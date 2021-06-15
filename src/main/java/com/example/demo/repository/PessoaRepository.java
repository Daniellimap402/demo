package com.example.demo.repository;

import com.example.demo.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {

    @Query(value = "SELECT p from Pessoa p where" +
            " LOWER(p.email) = LOWER(:login) OR p.documento = :login")
    Optional<Pessoa> findByEmailOrDocumento(@Param("login") String login);
}
