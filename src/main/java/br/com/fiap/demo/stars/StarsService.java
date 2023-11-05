package br.com.fiap.demo.stars;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.demo.user.User;
import br.com.fiap.demo.user.UserService;

@Service
public class StarsService {

    @Autowired
    StarsRepository repository;

    @Autowired
    UserService userService;

    public List<Stars> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var task = repository.findById(id);
        if(task.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Stars stars) {
        repository.save(stars);
    }

    public boolean increment(Long id) {
        var optionalStar = repository.findById(id);

        if (optionalStar.isEmpty()) return false;

        var stars = optionalStar.get();

        if (stars.getStatus() == null) stars.setStatus(0);
        if (stars.getStatus() == 100) return false;

        stars.setStatus(stars.getStatus() + 10);

        if (stars.getStatus() == 100){
            var user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.addScore(stars.getScore(), Long.valueOf(user.getName()));
        }

        repository.save(stars);
        return true;

    }

    public boolean decrement(Long id) {
        var optionalStar = repository.findById(id);

        if (optionalStar.isEmpty()) return false;

        var star = optionalStar.get();

        if (star.getStatus() == null || star.getStatus() == 0) return false;

        star.setStatus(star.getStatus() - 10);
        repository.save(star);
        return true;
    }

    public boolean catchStar(Long id, User user) {
        var optionalStars = repository.findById(id);

        if (optionalStars.isEmpty()) return false;

        var stars = optionalStars.get();

        if (stars.getUser() != null) return false;

        stars.setUser(user);

        repository.save(stars);
        return true;

    }

}