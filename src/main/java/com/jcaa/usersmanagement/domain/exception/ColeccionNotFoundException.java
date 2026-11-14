package com.jcaa.usersmanagement.domain.exception;

public final class ColeccionNotFoundException extends DomainException {

  private static final String MESSAGE_BY_NUMBER =
      "The collection with number '%s' was not found.";

  private ColeccionNotFoundException(final String message) {
    super(message);
  }

  public static ColeccionNotFoundException becauseNumberWasNotFound(
      final Integer numeroColeccion) {
    return new ColeccionNotFoundException(
        String.format(MESSAGE_BY_NUMBER, numeroColeccion));
  }
}
