package com.produtos.service;

import com.produtos.model.DTO.ProdutoModelDTO;
import com.produtos.model.ProdutoModel;
import com.produtos.repository.ProdutosRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @Mock
    private ProdutosRepository produtosRepository;

    @InjectMocks
    private ProdutoService produtoService;

    public ProdutoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProdutoExists() {
        UUID id = UUID.randomUUID();
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setId(id);
        produtoModel.setNome("Produto 1");
        produtoModel.setValor("10.0");

        when(produtosRepository.findById(id)).thenReturn(Optional.of(produtoModel));
        
        ProdutoModel result = produtoService.getProduto(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Produto 1", result.getNome());
        assertEquals("10.0", result.getValor());

        verify(produtosRepository).findById(id);
    }

    @Test
    void testGetProdutoNotExists() {
        UUID id = UUID.randomUUID();
        when(produtosRepository.findById(id)).thenReturn(Optional.empty());

        ProdutoModel result = produtoService.getProduto(id);
        assertNull(result);

        verify(produtosRepository).findById(id);
    }

    @Test
    void testGetProdutos() {
        List<ProdutoModel> listaProdutos = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(id1);
        produto1.setNome("Produto 1");
        produto1.setValor("10.0");

        ProdutoModel produto2 = new ProdutoModel();
        produto2.setId(id2);
        produto2.setNome("Produto 2");
        produto2.setValor("20.0");

        listaProdutos.add(produto1);
        listaProdutos.add(produto2);

        when(produtosRepository.findAll()).thenReturn(listaProdutos);

        List<ProdutoModel> result = produtoService.getProdutos();

        assertNotNull(result);
        assertEquals(2, result.size());

        ProdutoModel resultProduto1 = result.get(0);
        assertEquals(id1, resultProduto1.getId());
        assertEquals("Produto 1", resultProduto1.getNome());
        assertEquals("10.0", resultProduto1.getValor());

        ProdutoModel resultProduto2 = result.get(1);
        assertEquals(id2, resultProduto2.getId());
        assertEquals("Produto 2", resultProduto2.getNome());
        assertEquals("20.0", resultProduto2.getValor());

        verify(produtosRepository).findAll();
    }

    @Test
    void testDeleteProdutoExists() {

        UUID id = UUID.randomUUID();

        when(produtosRepository.existsById(id)).thenReturn(true);


        boolean result = produtoService.deleteProduto(id);


        assertTrue(result);

        verify(produtosRepository).existsById(id);
        verify(produtosRepository).deleteById(id);
    }

    @Test
    void testDeleteProdutoNotExists() {

        UUID id = UUID.randomUUID();

        when(produtosRepository.existsById(id)).thenReturn(false);


        boolean result = produtoService.deleteProduto(id);


        assertFalse(result);

        verify(produtosRepository).existsById(id);
        verify(produtosRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateProdutoExists() {

        UUID id = UUID.randomUUID();
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO("Produto Atualizado",
                "50.0", "Produto Atualizado", "10", "50");

        ProdutoModel produtoExistente = new ProdutoModel();
        produtoExistente.setId(id);
        produtoExistente.setNome("Produto Antigo");
        produtoExistente.setValor("20.0");
        produtoExistente.setDescricao("Descricao do produto antigo");
        produtoExistente.setQuantidade_estoque("2");
        produtoExistente.setPeso("1");

        ProdutoModel produtoAtualizado = new ProdutoModel();
        produtoAtualizado.setId(id);
        produtoAtualizado.setNome("Produto Atualizado");
        produtoAtualizado.setValor("50.0");
        produtoExistente.setDescricao("Descricao do produto atualizado");
        produtoExistente.setQuantidade_estoque("5");
        produtoExistente.setPeso("1");

        when(produtosRepository.findById(id)).thenReturn(Optional.of(produtoExistente));
        when(produtosRepository.save(any(ProdutoModel.class))).thenReturn(produtoAtualizado);


        ProdutoModel result = produtoService.updateProduto(id, produtoDTO);


        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Produto Atualizado", result.getNome());
        assertEquals("50.0", result.getValor());

        verify(produtosRepository).findById(id);
        verify(produtosRepository).save(any(ProdutoModel.class));
    }

    @Test
    void testUpdateProdutoNotExists() {

        UUID id = UUID.randomUUID();
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO("Produto Update",
                "10.0", "Update", "10", "20");

        when(produtosRepository.findById(id)).thenReturn(Optional.empty());

        ProdutoModel result = produtoService.updateProduto(id, produtoDTO);

        assertNull(result);

        verify(produtosRepository).findById(id);
        verify(produtosRepository, never()).save(any(ProdutoModel.class));
    }
}
