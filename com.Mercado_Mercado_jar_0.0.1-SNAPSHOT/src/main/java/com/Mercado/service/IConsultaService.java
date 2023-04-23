
package com.Mercado.service;

import com.Mercado.entity.Consultas;
import java.util.List;


public interface IConsultaService {
     public Consultas save(Consultas consultas);
     public Consultas getConsultaById (Integer id);
     public List<Consultas> findAll();
}
