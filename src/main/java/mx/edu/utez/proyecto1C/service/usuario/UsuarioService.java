package mx.edu.utez.proyecto1C.service.usuario;

import jakarta.transaction.Transactional;
import mx.edu.utez.proyecto1C.controller.usuario.dto.RequestActualizarUsuarioDTO;
import mx.edu.utez.proyecto1C.controller.usuario.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.proyecto1C.exception.BadRequestExeption;
import mx.edu.utez.proyecto1C.model.persona.Persona;
import mx.edu.utez.proyecto1C.model.usuario.Roles;
import mx.edu.utez.proyecto1C.model.usuario.Usuario;
import mx.edu.utez.proyecto1C.repository.usuario.UsuarioRepository;
import mx.edu.utez.proyecto1C.repository.usuario.persona.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestExeption("El usuario con ese id no existe"));
    }



    @Transactional
    public Usuario createUser(RequestCrearUsuarioDTO payload) {

        if (usuarioRepository.findByUsername(payload.getUsername()).isPresent()) {
            throw new BadRequestExeption("Ese usuario ya esta en uso");
        }

        Persona newPersona = new Persona();
        newPersona.setNombres(payload.getNombre());
        newPersona.setPrimerApellido(payload.getPrimerApellido());
        newPersona.setSegundoApellido(payload.getSegundoApellido());
        newPersona.setFechaNacimiento(payload.getFechaNacimento());
        newPersona.setCorreo(payload.getCorreo());
        newPersona.setCurp(payload.getCurp());

        newPersona = personaRepository.save(newPersona);

        Usuario newUser = new Usuario();
        newUser.setUsername(payload.getUsername());
        newUser.setPassword(payload.getPassword());
        newUser.setDescripcion(payload.getDescripcion());
        newUser.setEnable(true);
        newUser.setRol(Roles.valueOf(payload.getRol()));
        newUser.setPersona(newPersona);

        return usuarioRepository.save(newUser);
    }

    @Transactional
    public Usuario toggleUserStatus(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestExeption("El usuario con ese id no existe"));

        usuario.setEnable(!usuario.isEnable());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUser(Long id, RequestActualizarUsuarioDTO payload) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestExeption("El usuario con ese id no existee"));

        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(payload.getUsername());
        if (usuarioExistente.isPresent() && usuarioExistente.get().getId() != id) {
            throw new BadRequestExeption("El username ya esta en uso por otro usuario ");
        }

        usuario.setUsername(payload.getUsername());
        usuario.setDescripcion(payload.getDescripcion());


        return usuarioRepository.save(usuario);
    }





}