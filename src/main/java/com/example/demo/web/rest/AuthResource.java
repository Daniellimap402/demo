package com.example.demo.web.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.demo.configuration.security.jwt.JwtUtils;
import com.example.demo.configuration.security.services.UserDetailsImpl;
import com.example.demo.domain.Permissao;
import com.example.demo.domain.Pessoa;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.PermissaoRepository;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.service.enumerations.PermissoesEnum;
import com.example.demo.service.enumerations.RegistroAtivoEnum;
import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Component
@EnableAutoConfiguration
public class AuthResource {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PessoaRepository userRepository;

    @Autowired
    PermissaoRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles;

        Pessoa pessoa = userRepository.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        roles = (pessoa.getRoles().stream().map(role -> role.getNome().name()).collect(Collectors.toList()));

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getDocumento(),
                userDetails.getNumTelefone(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        Pessoa user = new Pessoa(signUpRequest.getNome(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getSenha()),
                signUpRequest.getDocumento(), signUpRequest.getNumTelefone());

        Set<Permissao> roles = new HashSet<>();

        Permissao userRole = roleRepository.findByNome(PermissoesEnum.USUARIO)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        user.setRegistroAtivo(RegistroAtivoEnum.S);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
