package com.indentados.clinicaodonto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indentados.clinicaodonto.DTO.ConsultaDTO;
import com.indentados.clinicaodonto.exception.ResourceNotFoundException;
import com.indentados.clinicaodonto.model.Consulta;
import com.indentados.clinicaodonto.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consulta")
@CrossOrigin( "*" )
public class ConsultaController {

    @Autowired
    ConsultaService service;

    @PostMapping
    public ResponseEntity salvarConsulta(@RequestBody Consulta consulta) {
        return new ResponseEntity(service.salvar(consulta), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity buscarTodasConsultas() {
        List<Consulta> consultaList = service.buscarTodas();

        if(consultaList.isEmpty()){
            return new ResponseEntity("Nenhuma consulta encontrada!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(consultaList, HttpStatus.OK);
    }

    @RequestMapping(value = "/agendaDentista", method = RequestMethod.GET)
    public ResponseEntity buscarTodasConsultasDTO() {
        List<ConsultaDTO> consultaDTOList = service.buscarTodasDTO();

        if(consultaDTOList.isEmpty()){
            return new ResponseEntity("Nenhuma consulta encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(consultaDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/buscaId", method = RequestMethod.GET)
    public ResponseEntity buscarConsultaPorId(@RequestParam("id") Long id) {
        //ObjectMapper mapper = new ObjectMapper();

        Optional<Consulta> consultaOptional = service.buscarPorId(id);
        if(consultaOptional.isEmpty()){
            return new ResponseEntity("Consulta n??o encontrada", HttpStatus.NOT_FOUND);
        }

        Consulta consulta = consultaOptional.get();

        return new ResponseEntity(consulta, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity atualizarConsulta(@RequestBody Consulta consulta) {
        if(consulta.getId() == null)
        {
            return new ResponseEntity("Consulta n??o encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(service.atualizar(consulta), HttpStatus.OK);
    }

    @DeleteMapping
    public void excluirConsulta(@RequestParam("id") Long id) throws ResourceNotFoundException {
        service.excluir(id);
    }

}
