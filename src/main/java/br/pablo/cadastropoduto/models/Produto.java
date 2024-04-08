package br.pablo.cadastropoduto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private Integer id;
    private String nome;
    private Double quantidade;

}
