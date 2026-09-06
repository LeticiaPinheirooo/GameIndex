package com.school.sptech.gameIndex_individual;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/jogos")
public class JogoController {
    private final JdbcTemplate jdbcTemplate;

    public JogoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping()
    public ResponseEntity<List<Jogo>> listarJogos(){
        String sql = """
        SELECT j.id, j.nome, j.dataJogou, j.favorito,
               p.nomePlat AS plataforma,
               c.nomeCat AS categoria
        FROM jogo j
        JOIN plataforma p ON j.FK_plataforma = p.idPlat
        JOIN categoria c ON j.FK_categoria = c.idCat
        """;
    List<Jogo> jogos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Jogo.class));

    return ResponseEntity.status(200).body(jogos);
    }

    @GetMapping("/procurar")
    public ResponseEntity<List<Jogo>> buscarPorNome(@RequestParam String nome){
        String sql = """
        SELECT j.nome, j.dataJogou, j.favorito,
                p.nomePlat AS plataforma,
                c.nomeCat AS categoria
        FROM jogo j 
        JOIN plataforma p ON j.FK_plataforma = p.idPlat
        JOIN categoria c ON j.FK_categoria = c.idCat
        WHERE LOWER(j.nome) LIKE LOWER(?)                
        """;

        List<Jogo>  jogos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Jogo.class), "%" + nome + "%");
        return  ResponseEntity.status(200).body(jogos);
    }

    @PostMapping
    public ResponseEntity<Jogo> criarResgistro(@RequestBody Jogo jogoCriar){
            boolean valido = jogoCriar.getNome() != null &&
                jogoCriar.getDataJogou() != null &&
                jogoCriar.getCategoria() != null &&
                jogoCriar.getPlataforma() != null;

        if (valido){
            String sql = "INSERT INTO jogo (nome, dataJogou, FK_plataforma, FK_categoria, favorito) VALUES (?, ?, (SELECT idPlat FROM plataforma WHERE nomePlat = ?),(SELECT idCat FROM categoria WHERE nomeCat = ?), ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, jogoCriar.getNome());
                ps.setDate(2, java.sql.Date.valueOf(jogoCriar.getDataJogou()));
                ps.setString(3, jogoCriar.getPlataforma());
                ps.setString(4, jogoCriar.getCategoria());
                ps.setBoolean(5, jogoCriar.getFavorito());

                return ps;
            }, keyHolder);
            Integer idGerado = keyHolder.getKeyAs(Integer.class);
            jogoCriar.setId(idGerado);
            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(400).build();
    }

}
