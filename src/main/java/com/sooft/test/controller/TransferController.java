package com.sooft.test.controller;

import com.sooft.test.dto.TransferDTO;
import com.sooft.test.exception.CompanyNotFoundException;
import com.sooft.test.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    TransferService transferService;

    @PostMapping
    public ResponseEntity<?> addTransfer(@RequestBody TransferDTO transfer) {
        try {
            TransferDTO createdTransferDto = transferService.addTransfer(transfer);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdTransferDto.getId())
                    .toUri();

            return ResponseEntity.created(location).body(createdTransferDto);
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
