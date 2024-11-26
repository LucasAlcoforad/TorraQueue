package torra.queue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import torra.queue.controller.dto.ClientDto;
import torra.queue.controller.dto.LoginRequest;
import torra.queue.controller.dto.LoginResponse;
import torra.queue.entity.Client;
import torra.queue.service.ClientService;
import torra.queue.service.TokenService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    private final TokenService tokenService;


    public ClientController(ClientService clientService, TokenService tokenService) {
        this.clientService = clientService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<String> createClient(@RequestBody ClientDto clientDto){
        var user = clientService.createClient(clientDto);
        return ResponseEntity.ok(user.getId().toString());
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Optional> getClientById(@PathVariable("clientId") String clientId){
        var user = clientService.getClientById(clientId);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @PreAuthorize("authentication.token.claims['isAdmin'] == true")
    public ResponseEntity<List<Client>> listClient(){
        var users = clientService.listClient();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Void> updateClientById(@PathVariable("clientId")String clientId,
                                                 @RequestBody ClientDto clientDto){
        if (clientService.updateById(clientId, clientDto)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClientById(@PathVariable("clientId")String clientId){
        if (clientService.deleteById(clientId)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
