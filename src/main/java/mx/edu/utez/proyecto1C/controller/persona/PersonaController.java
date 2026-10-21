package mx.edu.utez.proyecto1C.controller.persona;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1C.controller.persona.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1C.model.persona.Persona;
import mx.edu.utez.proyecto1C.service.persona.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persona")
@CrossOrigin({"*"})
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(this.personaService.getPersonaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(
            @PathVariable Long id,
            @RequestBody @Valid RequestActualizarPersonaDTO payload) {
        return ResponseEntity
                .status(200)
                .body(this.personaService.updatePersona(id, payload));
    }
}