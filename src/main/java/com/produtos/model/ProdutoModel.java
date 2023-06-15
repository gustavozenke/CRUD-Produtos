package com.produtos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import java.util.UUID;
@Table(name="Produtos")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoModel extends RepresentationModel<ProdutoModel> implements Comparable<ProdutoModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;

    private String valor;

    private String descricao;

    private String quantidade_estoque;

    private String peso;

    @Override
    public int compareTo(@NotNull ProdutoModel produto) {
        return this.nome.compareTo(produto.getNome());
    }
}
