package br.com.fiap.demo.stars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StarsService {

    @Autowired
    private StarsRepository starsRepository;

    public List<Stars> findAllStars() {
        return starsRepository.findAll();
    }


    public boolean deleteStars(Long id) {
        Optional<Stars> stars = starsRepository.findById(id);
        if (stars.isPresent()) {
            starsRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public void save(Stars stars) {
        starsRepository.save(stars);
    }
}