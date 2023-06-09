package com.produtos.controller;

import com.produtos.model.DTO.ProdutoModelDTO;
import com.produtos.model.ProdutoModel;
import com.produtos.service.ProdutoService;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
@AllArgsConstructor
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    /**
     * Obtém um produto pelo seu ID.
     *
     * @param id ID do produto a ser obtido.
     * @return ResponseEntity contendo o produto encontrado.
     *         - Retorna status 200 OK se o produto for encontrado.
     *         - Retorna status 404 Not Found se o produto não for encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> getProduto(@PathVariable("id") UUID id) {
        ProdutoModel produto = produtoService.getProduto(id);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(produto);
    }

    /**
     * Obtém todos os produtos.
     *
     * @return ResponseEntity contendo a lista de produtos.
     *         - Retorna status 200 OK se existirem produtos cadastrados.
     *         - Retorna status 404 Not Found se não existirem produtos cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<ProdutoModel>> getProdutos() {
        List<ProdutoModel> produtos = produtoService.getProdutos();
        if (produtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtos);
    }

    /**
     * Cria um novo produto.
     *
     * @param produtoDTO Objeto contendo os dados do produto a ser criado.
     * @return ResponseEntity contendo o produto criado.
     *         - Retorna status 201 Created se o produto for criado com sucesso.
     */
    @PostMapping
    public ResponseEntity<ProdutoModel> postProduto(@RequestBody @Valid ProdutoModelDTO produtoDTO) {
        ProdutoModel produto = produtoService.createProduto(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    /**
     * Processa o upload de um arquivo CSV contendo dados de produtos.
     *
     * @param file Arquivo CSV a ser processado.
     * @return ResponseEntity indicando o sucesso do processamento.
     *         - Retorna status 200 OK se o arquivo for processado com sucesso.
     * @throws CsvValidationException      Exceção lançada caso ocorra um erro de validação do CSV.
     * @throws IOException                 Exceção lançada caso ocorra um erro de leitura do arquivo.
     * @throws InvocationTargetException  Exceção lançada caso ocorra um erro na invocação do método.
     * @throws IllegalAccessException     Exceção lançada caso ocorra um erro de acesso ilegal.
     * @throws NoSuchMethodException        Exceção lançada caso ocorra um erro de método não encontrado.
     */
    @PostMapping("/upload")
    public ResponseEntity<Void> postProdutosUpload(@RequestParam("file") MultipartFile file) throws CsvValidationException, IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        produtoService.processarArquivoCSV(file);
        return ResponseEntity.ok().build();
    }

    /**
     * Exclui um produto pelo seu ID.
     *
     * @param id ID do produto a ser excluído.
     * @return ResponseEntity indicando o sucesso da exclusão.
     *         - Retorna status 204 No Content se o produto for excluído com sucesso.
     *         - Retorna status 404 Not Found se o produto não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable("id") UUID id) {
        boolean deleted = produtoService.deleteProduto(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Atualiza um produto pelo seu ID.
     *
     * @param id          ID do produto a ser atualizado.
     * @param produtoDTO  Objeto contendo os dados atualizados do produto.
     * @return ResponseEntity contendo o produto atualizado.
     *         - Retorna status 200 OK se o produto for atualizado com sucesso.
     *         - Retorna status 404 Not Found se o produto não for encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> putProduto(@PathVariable("id") UUID id,
                                                   @RequestBody @Valid ProdutoModelDTO produtoDTO) {
        ProdutoModel produto = produtoService.updateProduto(id, produtoDTO);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produto);
    }
}
