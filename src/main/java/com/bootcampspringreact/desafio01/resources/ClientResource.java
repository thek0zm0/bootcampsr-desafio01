package com.bootcampspringreact.desafio01.resources;

import com.bootcampspringreact.desafio01.dto.ClientDto;
import com.bootcampspringreact.desafio01.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/clients")
public class ClientResource {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDto>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
        @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction
    )
    {
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Page<ClientDto> list = clientService.findAll(pageRequest);
        return ResponseEntity.ok().body(list);
    }
}
