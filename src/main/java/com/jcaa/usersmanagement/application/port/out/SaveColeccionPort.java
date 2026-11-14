package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ColeccionModel;

public interface SaveColeccionPort {

  ColeccionModel save(ColeccionModel coleccion);

}

