package com.example.demo.provider.service;

import com.example.demo.provider.contract.Nota;
import com.example.demo.provider.contract.Notes;
import com.example.demo.provider.exception.NotaConflictDeleteException;
import com.example.demo.provider.exception.NotaNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class NoteService {


    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ObjectMapper objectMapper;


    @Value("classpath:json/note.json")
    private Resource resourceFile;


    public Nota getNotaById(String id) throws IOException {

        Notes note = objectMapper.readValue(resourceFile.getFile(), Notes.class);
        Optional<Nota> nota = note.getNote().stream().filter(n -> n.getId().equalsIgnoreCase(id)).findFirst();
        return nota.orElseThrow(() -> new NotaNotFoundException("Nota not found"));

    }

    public Boolean writeNota(Nota nota) throws IOException {
        Boolean result = true;
        Notes note = objectMapper.readValue(resourceFile.getFile(), Notes.class);
        //check if present
        note.getNote().stream().filter(n -> n.getId().equalsIgnoreCase(nota.getId())).findFirst().ifPresent(c -> note.getNote().remove(c));
        note.getNote().add(nota);
        try {
            objectMapper.writeValue(resourceFile.getFile(), note);
        } catch (IOException ex) {
            log.error("Something wen wrong ");
            result = false;
        }
        return result;
    }


    public List<Nota> getNoteBeforeByDate(LocalDate data) throws IOException {
        Notes note = objectMapper.readValue(resourceFile.getFile(), Notes.class);
        List<Nota> response = note.getNote().stream().filter(n -> n.getCreateAt().isBefore(data)).collect(Collectors.toList());
        if (response.isEmpty()) {
            throw new NotaNotFoundException("Nota not found");
        }
        return response;
    }

    public void deleteNoteById(String id) throws IOException {
        Notes note = objectMapper.readValue(resourceFile.getFile(), Notes.class);
        Optional<Nota> toDelete = note.getNote().stream().filter(c -> c.getId().equalsIgnoreCase(id)).findFirst();
        if (toDelete.isPresent()) {
            note.getNote().remove(toDelete.get());
        } else {
            throw new NotaNotFoundException("Nota not found");
        }
    }
}
