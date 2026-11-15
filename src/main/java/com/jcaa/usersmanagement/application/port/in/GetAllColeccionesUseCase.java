package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import java.util.List;

public interface GetAllColeccionesUseCase {
  List<ColeccionModel> execute();
}