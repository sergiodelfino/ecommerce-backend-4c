package br.edu.unifio.ecommerce.recursos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unifio.ecommerce.entidades.Produto;
import br.edu.unifio.ecommerce.repositorios.ProdutoRepositorio;

@RestController 
@RequestMapping ("produtos")
public class ProdutoRecurso {
    @Autowired 
    private ProdutoRepositorio produtoRepositorio;

    @GetMapping 
    public List<Produto> listar () {
        return produtoRepositorio.findAll(Sort.by("nome"));
    }

    @GetMapping ("{id}")
    public Optional<Produto> buscarPorId (@PathVariable Integer id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);

        if (produto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return produto;
    }

    @PostMapping 
    public Produto salvar (@RequestBody Produto produto) {
        return produtoRepositorio.save(produto);
    }

    @DeleteMapping ("{id}")
    public void excluirPorId (@PathVariable Integer id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);

        if (produto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Verificar se o produto esta sendo utilizando em um item do pedido
        // HttpStatus.CONFLICT

        produtoRepositorio.deleteById(id);
    }
}
