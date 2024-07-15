package com.forohub.ForoHub.modelo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.forohub.ForoHub.dto.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 150 )
    private String titulo;

    @Column(unique = true, length = 1500)
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Boolean activo;

    @OneToMany(mappedBy = "temaId", fetch = FetchType.LAZY)
    private List<Request> respuestas;
}
