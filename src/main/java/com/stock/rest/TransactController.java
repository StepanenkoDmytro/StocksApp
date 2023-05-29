package com.stock.rest;

import com.stock.dto.trasact.TransactDto;
import com.stock.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transacts")
public class TransactController {
    private final TransactService transactService;
    @Autowired
    public TransactController(TransactService transactService) {
        this.transactService = transactService;
    }

    @GetMapping("")
    public ResponseEntity getListTransacts(@RequestParam("userId") Long userID) {
        List<TransactDto> transacts = transactService.getTransactsByUserID(userID)
                .stream()
                .map(TransactDto::mapTransactToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(transacts);
    }
}
