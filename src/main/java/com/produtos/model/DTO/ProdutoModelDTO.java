package com.produtos.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public record ProdutoModelDTO(String nome, String valor, String descricao, String quantidade_estoque, String peso) {
}
