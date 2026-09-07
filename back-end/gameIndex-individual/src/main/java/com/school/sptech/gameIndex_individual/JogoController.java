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


@CrossOrigin(origins = "http://127.0.0.1:5500")
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
        SELECT j.id, j.nome, j.dataJogou, j.nota, j.favorito,
               p.nomePlat AS plataforma,
               c.nomeCat AS categoria
        FROM jogo j
        JOIN plataforma p ON j.FK_plataforma = p.idPlat
        JOIN categoria c ON j.FK_categoria = c.idCat
        """;
    List<Jogo> jogos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Jogo.class));

    return ResponseEntity.status(200).body(jogos);
    }

    @GetMapping("/plataformas")
    public ResponseEntity<List<Plataforma>> listarPlataformas(){
        String sql = "SELECT * FROM plataforma";

        List<Plataforma> plataformas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Plataforma.class));

        if (plataformas.isEmpty()){
            return  ResponseEntity.status(404).build();
        }
        return  ResponseEntity.status(200).body(plataformas);
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

    private Boolean validacaoNovoRegistro(Jogo jogo){
        if (jogo.getNome() == null || jogo.getNome().trim().isEmpty()) {
            return false;
        }

        if (jogo.getDataJogou() == null || jogo.getDataJogou().isAfter(java.time.LocalDate.now())) {
            return false;
        }

        if (jogo.getNota() == null || jogo.getNota() < 0 || jogo.getNota() > 10) {
            return false;
        }

        if (jogo.getPlataforma() == null || jogo.getCategoria() == null) {
            return false;
        }
        
        Integer verificaPlataforma = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM plataforma WHERE UPPER(nomePlat) = UPPER(?)", Integer.class, jogo.getPlataforma());

        Integer verificaCategoria = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM categoria WHERE LOWER(nomeCat) = LOWER(?)", Integer.class, jogo.getCategoria());

        if (verificaPlataforma == 0 || verificaCategoria == 0) {
            return false;
        }

        return true;
    }

    @PostMapping
    public ResponseEntity<Jogo> criarResgistro(@RequestBody Jogo jogoCriar){
        if (validacaoNovoRegistro(jogoCriar)){
            String sql = "INSERT INTO jogo (nome, dataJogou, nota, FK_plataforma, FK_categoria, favorito) VALUES (?, ?, ?, (SELECT idPlat FROM plataforma WHERE nomePlat = UPPER(?)),(SELECT idCat FROM categoria WHERE LOWER(nomeCat) = LOWER(?)), ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, jogoCriar.getNome());
                ps.setDate(2, java.sql.Date.valueOf(jogoCriar.getDataJogou()));
                ps.setInt(3, jogoCriar.getNota());
                ps.setString(4, jogoCriar.getPlataforma());
                ps.setString(5, jogoCriar.getCategoria());
                ps.setBoolean(6, jogoCriar.getFavorito() != null && jogoCriar.getFavorito());

                return ps;
            }, keyHolder);
            Integer idGerado = keyHolder.getKeyAs(Integer.class);
            jogoCriar.setId(idGerado);
            return ResponseEntity.status(201).body(jogoCriar);
        }

        return ResponseEntity.status(400).build();
    }

}
