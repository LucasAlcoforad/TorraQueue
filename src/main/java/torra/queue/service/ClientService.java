package torra.queue.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import torra.queue.controller.dto.ClientDto;
import torra.queue.controller.dto.LoginRequest;
import torra.queue.entity.Client;
import torra.queue.repository.ClientRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository clientRepository;


    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client createClient(ClientDto clientDto){
        if (clientRepository.existsByEmail(clientDto.email())
                || clientRepository.existsByPhone(clientDto.phone())) {
            throw new IllegalArgumentException("Já existe um cliente com este atributo único.");
        }
        List<Client> queue = clientRepository.findAll();
        var entity = new Client(
                null,
                clientDto.name(),
                clientDto.lastName(),
                queue.size()+1,
                clientDto.phone(),
                clientDto.email(),
                null,
                clientDto.isPriority());
        return clientRepository.save(entity);
    }

    public Optional<Client> getClientById(String id){
        return clientRepository.findById(UUID.fromString(id));
    }

    public List<Client> listClient(){
        return clientRepository.findAll(Sort.by(Sort.Direction.ASC, "position"));
    }

    public Boolean updateById(String clientId,
                           ClientDto clientDto){
        if (clientRepository.existsByEmail(clientDto.email())
                || clientRepository.existsByPhone(clientDto.phone())) {
            throw new IllegalArgumentException("Já existe um cliente com este atributo único.");
        }
        var entity = clientRepository.findById(UUID.fromString(clientId));
        if (entity.isEmpty()){
            return false;
        }
        var client = entity.get();
        if (clientDto.email()!=null){
            client.setEmail(clientDto.email());
        }
        if (clientDto.name()!=null){
            client.setName(clientDto.name());
        }
        if (clientDto.lastName()!=null){
            client.setLastName(clientDto.lastName());
        }
        if (clientDto.phone()!=null){
            client.setPhone(clientDto.phone());
        }
        clientRepository.save(client);
        return true;
    }

    public Boolean deleteById(String clientId){
        var entity = clientRepository.findById(UUID.fromString(clientId));
        if (entity.isEmpty()){
            return false;
        }
        var client = entity.get();
        clientRepository.deleteById(client.getId());
        List<Client> queue = clientRepository.findAll();
        for (Client clients : queue) {
            if (clients.getPosition() > client.getPosition()) {
                clients.setPosition(clients.getPosition() - 1);
                clientRepository.save(clients);
            }
        }
        return true;
    }
}
