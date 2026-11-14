package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetColeccionByNumeroQuery;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;

public interface GetColeccionByNumeroUseCase {
  ColeccionModel execute(GetColeccionByNumeroQuery query);
}