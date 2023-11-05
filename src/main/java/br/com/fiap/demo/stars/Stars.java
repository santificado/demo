package br.com.fiap.demo.stars;

import br.com.fiap.demo.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Stars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank()
    private String title;

    @Size(min = 10, message = "{stars.description.size.error}")
    private String description;

    @Min(1) @Max(5)  // Assuming scores range from 1 to 5
    private Integer score;

    @Min(0) @Max(100)
    private Integer status;

    @ManyToOne
    private User user;
}
