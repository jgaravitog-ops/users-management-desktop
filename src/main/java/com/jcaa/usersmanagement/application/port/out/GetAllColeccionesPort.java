package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import java.util.List;

public interface GetAllColeccionesPort {
  List<ColeccionModel> getAll();
}