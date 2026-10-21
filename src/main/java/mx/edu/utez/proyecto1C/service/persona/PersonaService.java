package mx.edu.utez.proyecto1C.service.persona;

import jakarta.transaction.Transactional;
import mx.edu.utez.proyecto1C.controller.persona.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1C.exception.BadRequestExeption;
import mx.edu.utez.proyecto1C.model.persona.Persona;
import mx.edu.utez.proyecto1C.repository.usuario.persona.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona getPersonaById(Long id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new BadRequestExeption("La persona con ese id no existe"));
    }

    @Transactional
    public Persona updatePersona(Long id, RequestActualizarPersonaDTO payload) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new BadRequestExeption("La persona con ese id no existe"));

        persona.setNombres(payload.getNombre());
        persona.setPrimerApellido(payload.getPrimerApellido());
        persona.setSegundoApellido(payload.getSegundoApellido());
        persona.setTelefono(payload.getTelefono());

        return personaRepository.save(persona);
    }
}