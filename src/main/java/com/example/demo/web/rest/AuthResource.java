package com.example.demo.web.rest;

import com.example.demo.configuration.security.jwt.JwtUtils;
import com.example.demo.configuration.security.services.UserDetailsImpl;
import com.example.demo.domain.Pessoa;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.dto.ModeloEmailDTO;
import com.example.demo.service.PessoaService;
import com.example.demo.service.dto.PessoaDTO;
import com.example.demo.service.enumerations.PermissaoEnum;
import com.example.demo.service.enumerations.RegistroAtivoEnum;
import com.example.demo.service.error.NegocioException;
import com.example.demo.service.mapper.PessoaMapper;
import com.example.demo.service.util.ConstantesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Component
@RequiredArgsConstructor
@EnableAutoConfiguration
public class AuthResource {
    private final AuthenticationManager authenticationManager;

    private final PessoaService pessoaService;

    private final PessoaRepository userRepository;

    private final PessoaMapper mapper;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final EmailService emailService;

    @PostMapping("/entrar")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws NegocioException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles;

        Pessoa pessoa = userRepository.findById(userDetails.getId()).orElseThrow(() -> new NegocioException(ConstantesUtil.ERROR_TITLE," Usuario não encontrado."));
        roles = (pessoa.getRoles().stream().map(role -> role.name()).collect(Collectors.toList()));

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getNome(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody PessoaDTO pessoaDTO) throws NegocioException {

        pessoaService.validarPessoa(pessoaDTO);
        Pessoa pessoa = mapper.toEntity(pessoaDTO);
        pessoa.setSenha(encoder.encode(pessoaDTO.getSenha()));

        List<PermissaoEnum> roles = new ArrayList<>();

        roles.add(PermissaoEnum.ADMIN);

        pessoa.setRoles(roles);
        pessoa.setEmailVerificado(RegistroAtivoEnum.N);
        userRepository.save(pessoa);
        String CONFIRMAR_EMAIL = ConstantesUtil.ROTA_CONFIRMAR_EMAIL_PESSOA +pessoa.getEmail();
        ModeloEmailDTO modeloEmailDTO = new ModeloEmailDTO("Confirmaçao Email","Abra esse link para confirmar seu email: " + CONFIRMAR_EMAIL,pessoa.getEmail());
        emailService.enviarEmail(modeloEmailDTO);
        return ResponseEntity.ok().build();

    }
}
