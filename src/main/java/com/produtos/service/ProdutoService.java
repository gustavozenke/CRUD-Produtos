package com.produtos.service;

import com.produtos.controller.ProdutoController;
import com.produtos.model.DTO.ProdutoModelDTO;
import com.produtos.model.ProdutoModel;
import com.produtos.repository.ProdutosRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.util.StringUtils.capitalize;

@Service
public class ProdutoService {

    @Autowired
    ProdutosRepository produtosRepository;

    Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public ProdutoModel getProduto(UUID id) {
        try {
            logger.info("Obtendo produto com ID: " + id);
            Optional<ProdutoModel> produto = produtosRepository.findById(id);
            if (produto.isPresent()) {
                produto.get().add(WebMvcLinkBuilder.linkTo(methodOn(ProdutoController.class).getProdutos()).withRel("Lista de produtos"));
                return produto.get();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Falha ao obter o produto com ID: " + id, e);
            throw e;
        }
    }

    public List<ProdutoModel> getProdutos() {
        try {
            logger.info("Obtendo lista de produtos");
            List<ProdutoModel> listaProdutos = produtosRepository.findAll();
            for (ProdutoModel produto : listaProdutos) {
                UUID idProduto = produto.getId();
                produto.add(linkTo(methodOn(ProdutoController.class).getProduto(idProduto)).withSelfRel());
            }
            return listaProdutos;
        } catch (Exception e) {
            logger.error("Falha ao obter a lista de produtos", e);
            throw e;
        }
    }

    public ProdutoModel createProduto(ProdutoModelDTO produtoDTO) {
        try {
            logger.info("Criando produto");
            ProdutoModel produto = new ProdutoModel();
            BeanUtils.copyProperties(produtoDTO, produto);
            return produtosRepository.save(produto);
        } catch (Exception e) {
            logger.error("Falha ao criar o produto", e);
            throw e;
        }
    }

    public void processarArquivoCSV(MultipartFile file) throws CsvValidationException, IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        try {
            logger.info("Processando arquivo CSV");
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
                String[] header = csvReader.readNext();

                String[] linhaCsv;
                while ((linhaCsv = csvReader.readNext()) != null) {
                    var produtoModel = new ProdutoModel();
                    for (String nomeColuna : header) {
                        String valorColuna = linhaCsv[Arrays.asList(header).indexOf(nomeColuna)];
                        produtoModel.getClass().getMethod("set" + capitalize(nomeColuna),
                                String.class).invoke(produtoModel, valorColuna);
                    }
                    produtosRepository.save(produtoModel);
                }
            }
        } catch (Exception e) {
            logger.error("Falha ao processar o arquivo CSV", e);
            throw e;
        }
    }

    public boolean deleteProduto(UUID id) {
        try {
            logger.info("Deletando produto com ID: " + id);
            if (produtosRepository.existsById(id)) {
                produtosRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Falha ao deletar o produto com ID: " + id, e);
            throw e;
        }
    }

    public ProdutoModel updateProduto(UUID id, ProdutoModelDTO produtoDTO) {
        try {
            logger.info("Atualizando produto com ID: " + id);
            Optional<ProdutoModel> produtoOptional = produtosRepository.findById(id);
            if (produtoOptional.isPresent()) {
                ProdutoModel produto = produtoOptional.get();
                BeanUtils.copyProperties(produtoDTO, produto);
                return produtosRepository.save(produto);
            }
            return null;
        } catch (Exception e) {
            logger.error("Falha ao atualizar o produto com ID: " + id, e);
            throw e;
        }
    }
}
