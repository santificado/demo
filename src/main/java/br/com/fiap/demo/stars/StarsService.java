package br.com.fiap.demo.stars;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}