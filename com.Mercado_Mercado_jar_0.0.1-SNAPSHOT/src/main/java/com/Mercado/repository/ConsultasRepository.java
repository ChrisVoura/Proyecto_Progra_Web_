
package com.Mercado.repository;

import com.Mercado.entity.Consultas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultasRepository extends JpaRepository <Consultas,Integer>{
    
}
