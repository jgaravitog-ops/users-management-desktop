package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateColeccionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateColeccionCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetColeccionByNumeroQuery;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ColeccionApplicationMapper {

  public ColeccionModel fromCreateCommandToModel(
      final CreateColeccionCommand command) {
    return ColeccionModel.create(
        command.claveObra(),
        command.promotor(),
        command.pvp(),
        command.existencias(),
        command.stockMinimo());
  }

  public Integer fromGetColeccionByNumeroQueryToNumero(
      final GetColeccionByNumeroQuery query) {
    return query.numeroColeccion();
  }

  public Integer fromUpdateCommandToNumero(
      final UpdateColeccionCommand command) {
    return command.numeroColeccion();
  }

  public ColeccionModel fromUpdateCommandToModel(
      final UpdateColeccionCommand command) {
    return ColeccionModel.create(
          command.claveObra(),
          command.promotor(),
          command.pvp(),
          command.existencias(),
          command.stockMinimo())
      .assignNumeroColeccion(command.numeroColeccion());
  }
}
