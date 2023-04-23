
package com.Mercado.service;


import com.Mercado.entity.Consultas;
import com.Mercado.repository.ConsultasRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService implements IConsultaService{
  @Autowired
     private ConsultasRepository consultasrepository;
    @Override
    public Consultas save(Consultas consultas) {
        return consultasrepository.save(consultas);
    }

    @Override
    public Consultas getConsultaById(Integer id) {
      return consultasrepository.findById(id).get();
    }

    @Override
    public List<Consultas> findAll() {
       return consultasrepository.findAll();
    }
  
}
