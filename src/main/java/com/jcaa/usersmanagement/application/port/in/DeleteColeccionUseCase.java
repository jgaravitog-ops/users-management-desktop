package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteColeccionCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteColeccionUseCase {
  void execute(@NotNull @Valid DeleteColeccionCommand command);
}
