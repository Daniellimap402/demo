package com.example.demo.configuration.security.services;

import com.example.demo.domain.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.service.mapper.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PessoaRepository userRepository;

    private final PessoaMapper mapper;

    @Transactional
    public UserDetails loadUserByUsername(String credencial) throws UsernameNotFoundException {
        Pessoa pessoa = userRepository.findByEmail(credencial)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado com as credenciais: " + credencial));
        return mapper.toUserDetails(pessoa);
    }
}
