package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.port.in.GetAllColeccionesUseCase;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ColeccionResponsePrinter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListColeccionesHandler implements OperationHandler {

  private final GetAllColeccionesUseCase getAllColeccionesUseCase;
  private final ColeccionResponsePrinter printer;

  @Override
  public void handle() {
    final List<ColeccionModel> colecciones = getAllColeccionesUseCase.execute();
    printer.printList(colecciones);
  }
}
