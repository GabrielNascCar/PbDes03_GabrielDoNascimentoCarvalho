package org.compass.mseventmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

}
