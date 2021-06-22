package com.example.demo.service;

import com.example.demo.domain.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.service.dto.ModeloEmailDTO;
import com.example.demo.service.dto.PessoaDTO;
import com.example.demo.service.error.NegocioException;
import com.example.demo.service.mapper.PessoaMapper;
import com.example.demo.service.util.ConstantesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@CrossOrigin({"*"})
public class PessoaService {

    @Autowired
    private  PessoaRepository repository;

    @Autowired
    private  PessoaMapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private final PasswordEncoder encoder;

    public List<PessoaDTO> listar(){
        return this.mapper.toDto(this.repository.findAll());
    }

    public void verificarEmail(String email) throws NegocioException {
        this.obterPorEmail(email);
        this.repository.ativarPessoa(email);
    }

    public void recuperarSenha(String email) throws NegocioException {
        PessoaDTO pessoaDTO = this.obterPorEmail(email);
        String senha = UUID.randomUUID().toString();
        this.repository.mudarSenha(email, encoder.encode(senha));
        enviarEmailRecuperacao(email, pessoaDTO, senha);
    }

    private void enviarEmailRecuperacao(String email, PessoaDTO pessoaDTO, String senha) throws NegocioException {
        String LINK = ConstantesUtil.ROTA_RECUPERAR_PESSOA + pessoaDTO.getId()+"/"+ senha;
        ModeloEmailDTO emailDTO = new ModeloEmailDTO("Recuperação de senha","acesse esse link para recuperar sua senha: "+ LINK, email);
        emailService.enviarEmail(emailDTO);
    }

    public void alterarSenha(Long id, String senha) throws NegocioException {
        PessoaDTO pessoa = this.obterPorId(id);
        this.repository.mudarSenha(pessoa.getEmail(), encoder.encode(senha));
    }

    public PessoaDTO obterPorEmail(String email) throws NegocioException {
        Pessoa pessoa = this.repository.findByEmail(email).orElseThrow(() -> new NegocioException(ConstantesUtil.ERROR_TITLE,ConstantesUtil.PESSOA_NAO_ENCONTRADA));
        return mapper.toDto(pessoa);
    }

    private PessoaDTO obterPorId(Long id) throws NegocioException {
        Pessoa pessoa = this.repository.findById(id).orElseThrow(() -> new NegocioException(ConstantesUtil.ERROR_TITLE,ConstantesUtil.PESSOA_NAO_ENCONTRADA));
        return this.mapper.toDto(pessoa);
    }

}
