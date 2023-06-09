package com.produtos.controller;

import com.produtos.model.DTO.ProdutoModelDTO;
import com.produtos.model.ProdutoModel;
import com.produtos.service.ProdutoService;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produtoController = new ProdutoController(produtoService);
    }

    @Test
    void getProduto_ValidId_ReturnsProdutoModel() {
        UUID id = UUID.randomUUID();
        ProdutoModel produto = new ProdutoModel(id,
                "Produto 1", "10.0", "Descricao produto 1", "15", "7");
        when(produtoService.getProduto(id)).thenReturn(produto);

        ResponseEntity<ProdutoModel> response = produtoController.getProduto(id);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(produto, response.getBody());
    }

    @Test
    void getProduto_InvalidId_ReturnsNotFound() {
        UUID id = UUID.randomUUID();
        when(produtoService.getProduto(id)).thenReturn(null);

        ResponseEntity<ProdutoModel> response = produtoController.getProduto(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getProdutos_ReturnsListOfProdutoModel() {
        List<ProdutoModel> produtos = new ArrayList<>();
        produtos.add(new ProdutoModel(UUID.randomUUID(),
                "Produto 1", "10.0", "Descricao produto 1", "10", "2"));
        produtos.add(new ProdutoModel(UUID.randomUUID(),
                "Produto 2", "50.0", "Descricao produto 2", "63", "5"));
        when(produtoService.getProdutos()).thenReturn(produtos);

        ResponseEntity<List<ProdutoModel>> response = produtoController.getProdutos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtos, response.getBody());
    }

    @Test
    void getProdutos_ReturnsNotFound() {
        when(produtoService.getProdutos()).thenReturn(new ArrayList<>());

        ResponseEntity<List<ProdutoModel>> response = produtoController.getProdutos();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void postProduto_ValidProdutoModelDTO_ReturnsCreated() {
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO("Produto 1",
                "10.0", "Descricao produto 1", "10", "2");
        ProdutoModel produto = new ProdutoModel(UUID.randomUUID(),
                "Produto 1", "10.0", "Descricao produto 1", "15", "7");
        when(produtoService.createProduto(produtoDTO)).thenReturn(produto);

        ResponseEntity<ProdutoModel> response = produtoController.postProduto(produtoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produto, response.getBody());
    }

    @Test
    void postProdutosUpload_ValidMultipartFile_ReturnsOk() throws IOException, CsvValidationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        MultipartFile file = new MockMultipartFile("test.csv", "content".getBytes());

        ResponseEntity<Void> response = produtoController.postProdutosUpload(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(produtoService, times(1)).processarArquivoCSV(file);
    }

    @Test
    void deleteProduto_ValidId_ReturnsNoContent() {
        UUID id = UUID.randomUUID();
        when(produtoService.deleteProduto(id)).thenReturn(true);

        ResponseEntity<Void> response = produtoController.deleteProduto(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteProduto_InvalidId_ReturnsNotFound() {
        UUID id = UUID.randomUUID();
        when(produtoService.deleteProduto(id)).thenReturn(false);

        ResponseEntity<Void> response = produtoController.deleteProduto(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void putProduto_ValidIdAndProdutoModelDTO_ReturnsProdutoModel() {
        UUID id = UUID.randomUUID();
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO("Produto 1",
                "10.0", "Descricao produto 1", "10", "2");
        ProdutoModel produto = new ProdutoModel(id,
                "Produto 1", "10.0", "Descricao produto 1", "15", "7");;
        when(produtoService.updateProduto(id, produtoDTO)).thenReturn(produto);

        ResponseEntity<ProdutoModel> response = produtoController.putProduto(id, produtoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produto, response.getBody());
    }

    @Test
    void putProduto_InvalidId_ReturnsNotFound() {
        UUID id = UUID.randomUUID();
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO("Produto 1",
                "10.0", "Descricao produto 1", "10", "2");
        when(produtoService.updateProduto(id, produtoDTO)).thenReturn(null);

        ResponseEntity<ProdutoModel> response = produtoController.putProduto(id, produtoDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
