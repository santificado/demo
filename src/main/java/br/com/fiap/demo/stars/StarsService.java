package br.com.fiap.demo.stars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StarsService {

    @Autowired
    StarsRepository repository;

    public List<Stars> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var stars = repository.findById(id);
        if(stars.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Stars stars) {
        repository.save(stars);
    }

}