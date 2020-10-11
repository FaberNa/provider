package com.example.demo.provider.controller;

import com.example.demo.provider.contract.Nota;
import com.example.demo.provider.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController("api")
public class BaseProviderController {


    @Autowired
    NoteService noteService;

    @GetMapping("/nota/{id}")
    @Operation(description = "Read notes  byid")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operazione conclusa correttamente", content = @Content(schema = @Schema(implementation = Nota.class))),
            @ApiResponse(responseCode = "502", description = "Errore nel servizio chiamato", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Errore nella gestione della richiesta", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "500", description = "Errore generico", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))})
    public Nota getNota(@PathVariable(name = "id") String id) throws IOException {
        return noteService.getNotaById(id);
    }

    @PostMapping("/nota")
    @Operation(description = "Insert note")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operazione conclusa correttamente", content = @Content(schema = @Schema(implementation = Boolean.class))),})
    public Boolean createNota(@RequestBody @Valid Nota nota) throws IOException {
        return noteService.writeNota(nota);
    }


    @DeleteMapping("/nota")
    @Operation(description = "Cancella le note superiore ad una certa data")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operazione conclusa correttamente", content = @Content(schema = @Schema(implementation = Nota.class))),
            @ApiResponse(responseCode = "502", description = "Errore nel servizio chiamato", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Errore nella gestione della richiesta", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "409", description = "Conflitto", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))})
    public void deleteNoteById(@RequestParam(name = "id") String id) throws IOException {
        noteService.deleteNoteById(id);
    }

    @GetMapping("/noteBefore")
    @Operation(description = "Reads notes before a date")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operazione conclusa correttamente", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "502", description = "Errore nel servizio chiamato", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Errore nella gestione della richiesta", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "500", description = "Errore generico", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))})
    public List<Nota> getNotaByDate(@RequestParam(name = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) throws IOException {
        return noteService.getNoteBeforeByDate(data);
    }

}
