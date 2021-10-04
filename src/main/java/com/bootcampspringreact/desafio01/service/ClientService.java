package com.bootcampspringreact.desafio01.service;

import com.bootcampspringreact.desafio01.dto.ClientDto;
import com.bootcampspringreact.desafio01.entities.Client;
import com.bootcampspringreact.desafio01.repositories.ClientRepository;
import com.bootcampspringreact.desafio01.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDto> findAll(PageRequest pageRequest) {
        return clientRepository.findAll(pageRequest).map(ClientDto::new);
    }

    public ClientDto findById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client doesnt exists..."));
        return new ClientDto(client);
    }
}
