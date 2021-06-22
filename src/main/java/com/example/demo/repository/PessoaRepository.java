package com.example.demo.repository;

import com.example.demo.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {

    @Query(value = "SELECT p from Pessoa p where" +
            " LOWER(p.email) = LOWER(:login)")
    Optional<Pessoa> findByEmail(@Param("login") String login);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Pessoa p SET p.emailVerificado = 'S' WHERE p.email = :email")
    void ativarPessoa(@Param("email") String email);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Pessoa p SET p.senha = :senha WHERE p.email = :email")
    void mudarSenha(@Param("email")String email, @Param("senha")String senha);

}
