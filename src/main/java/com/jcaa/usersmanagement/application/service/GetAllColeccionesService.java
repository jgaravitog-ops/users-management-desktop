package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllColeccionesUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllColeccionesPort;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class GetAllColeccionesService implements GetAllColeccionesUseCase {

  private final GetAllColeccionesPort getAllColeccionesPort;

  @Override
  public List<ColeccionModel> execute() {
    return getAllColeccionesPort.getAll();
  }
}