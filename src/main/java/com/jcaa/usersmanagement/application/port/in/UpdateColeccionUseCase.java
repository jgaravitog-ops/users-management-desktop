package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateColeccionCommand;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateColeccionUseCase {

  ColeccionModel execute(@NotNull @Valid UpdateColeccionCommand command);

}
