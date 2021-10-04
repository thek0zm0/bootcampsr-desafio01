package com.bootcampspringreact.desafio01.service;

import com.bootcampspringreact.desafio01.dto.ClientDto;
import com.bootcampspringreact.desafio01.entities.Client;
import com.bootcampspringreact.desafio01.repositories.ClientRepository;
import com.bootcampspringreact.desafio01.service.exceptions.DatabaseException;
import com.bootcampspringreact.desafio01.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDto> findAll(PageRequest pageRequest) {
        return clientRepository.findAll(pageRequest).map(ClientDto::new);
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client doesnt exists..."));
        return new ClientDto(client);
    }

    @Transactional
    public ClientDto insert(ClientDto clientDto) {
        Client entity = new Client();
        copyDtoToEntity(clientDto,entity);
        entity = clientRepository.save(entity);
        return new ClientDto(entity);
    }

    @Transactional
    public ClientDto update(Long id, ClientDto clientDto) {
        try {
            Client client = clientRepository.getOne(id);
            copyDtoToEntity(clientDto, client);
            client = clientRepository.save(client);

            return new ClientDto(client);
        }
        catch (EntityNotFoundException e)
        {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try
        {
            clientRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ClientDto clientDto, Client entity)
    {
        entity.setName(clientDto.getName());
        entity.setCpf(clientDto.getCpf());
        entity.setIncome(clientDto.getIncome());
        entity.setBirthDate(clientDto.getBirthDate());
        entity.setChildren(clientDto.getChildren());
    }
}
