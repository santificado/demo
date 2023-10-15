package br.com.fiap.demo.stars;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StarsRepository extends JpaRepository<Stars, Long> {
}