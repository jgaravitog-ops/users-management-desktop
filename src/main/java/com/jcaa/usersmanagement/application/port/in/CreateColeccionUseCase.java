package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateColeccionCommand;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;

public interface CreateColeccionUseCase {

  ColeccionModel execute(CreateColeccionCommand command);

}
