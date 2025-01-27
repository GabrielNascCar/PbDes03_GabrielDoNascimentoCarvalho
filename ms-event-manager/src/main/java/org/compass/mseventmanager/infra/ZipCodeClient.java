package org.compass.mseventmanager.infra;

import org.compass.mseventmanager.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "zipcode")
public interface ZipCodeClient {

    @GetMapping("{cep}/json/")
    Address getAddress(@PathVariable("cep") String cep);
}

