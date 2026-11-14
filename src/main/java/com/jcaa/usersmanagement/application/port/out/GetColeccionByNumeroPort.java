package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import java.util.Optional;

public interface GetColeccionByNumeroPort {
  Optional<ColeccionModel> getByNumero(Integer numeroColeccion);
}

