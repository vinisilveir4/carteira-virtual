package com.example.carteiraVirtual.repositories;

import com.example.carteiraVirtual.models.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long>, JpaSpecificationExecutor<Carteira> {

    @Query("select c from Carteira c join c.usuario u where u.id = :idUsuario")
    Optional<Carteira> findCarteiraBy(@Param("idUsuario") Long id);
}
