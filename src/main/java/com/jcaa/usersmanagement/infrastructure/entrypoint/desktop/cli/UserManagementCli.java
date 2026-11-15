package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli;

import com.jcaa.usersmanagement.application.port.in.CreateColeccionUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteColeccionUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllColeccionesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetColeccionByNumeroUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateColeccionUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.CreateColeccionHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.CreateUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.DeleteColeccionHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.DeleteUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.FindColeccionHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.FindUserByIdHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.ListColeccionesHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.ListUsersHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.LoginHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.UpdateColeccionHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.UpdateUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ColeccionResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu.MenuOption;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserManagementCli {

  private static final String BANNER =
      """
      ==========================================
           Users Management System
      ==========================================""";

  private static final String MENU_BORDER = "  ==========================================";

  private final UserController userController;
  private final CreateColeccionUseCase createColeccionUseCase;
  private final GetColeccionByNumeroUseCase getColeccionByNumeroUseCase;
  private final UpdateColeccionUseCase updateColeccionUseCase;
  private final DeleteColeccionUseCase deleteColeccionUseCase;
  private final GetAllColeccionesUseCase getAllColeccionesUseCase;
  private final ConsoleIO console;

  public void start() {
    console.println(BANNER);

    final UserResponsePrinter userPrinter = new UserResponsePrinter(console);
    final ColeccionResponsePrinter coleccionPrinter = new ColeccionResponsePrinter(console);

    runLoop(buildHandlers(userPrinter, coleccionPrinter));
  }

  private void runLoop(final Map<MenuOption, OperationHandler> handlers) {
    boolean running = true;
    while (running) {
      printMenu();
      final int choice = console.readInt("\n  Option: ");
      final Optional<MenuOption> option = MenuOption.fromNumber(choice);

      if (option.isEmpty()) {
        console.println("  Invalid option. Please try again.");
      } else if (option.get() == MenuOption.EXIT) {
        console.println("\n  Goodbye!\n");
        running = false;
      } else {
        executeHandler(handlers, option.get());
      }
    }
  }

  private void executeHandler(
      final Map<MenuOption, OperationHandler> handlers, final MenuOption option) {
    try {
      handlers.get(option).handle();
    } catch (final ConstraintViolationException exception) {
      console.println("  Validation errors:");
      exception.getConstraintViolations()
          .forEach(violation -> console.println("    - " + violation.getMessage()));
    } catch (final RuntimeException exception) {
      console.println("  Unexpected error: " + exception.getMessage());
    }
  }

  private Map<MenuOption, OperationHandler> buildHandlers(
      final UserResponsePrinter userPrinter,
      final ColeccionResponsePrinter coleccionPrinter) {

    return Map.ofEntries(
        Map.entry(MenuOption.LIST_USERS,
            new ListUsersHandler(userController, userPrinter)),
        Map.entry(MenuOption.FIND_USER,
            new FindUserByIdHandler(userController, console, userPrinter)),
        Map.entry(MenuOption.CREATE_USER,
            new CreateUserHandler(userController, console, userPrinter)),
        Map.entry(MenuOption.UPDATE_USER,
            new UpdateUserHandler(userController, console, userPrinter)),
        Map.entry(MenuOption.DELETE_USER,
            new DeleteUserHandler(userController, console)),
        Map.entry(MenuOption.LOGIN,
            new LoginHandler(userController, console, userPrinter)),

        Map.entry(MenuOption.LIST_COLECCIONES,
            new ListColeccionesHandler(getAllColeccionesUseCase, coleccionPrinter)),
        Map.entry(MenuOption.FIND_COLECCION,
            new FindColeccionHandler(
                getColeccionByNumeroUseCase, console, coleccionPrinter)),
        Map.entry(MenuOption.CREATE_COLECCION,
            new CreateColeccionHandler(
                createColeccionUseCase, console, coleccionPrinter)),
        Map.entry(MenuOption.UPDATE_COLECCION,
            new UpdateColeccionHandler(
                updateColeccionUseCase, console, coleccionPrinter)),
        Map.entry(MenuOption.DELETE_COLECCION,
            new DeleteColeccionHandler(deleteColeccionUseCase, console)));
  }

  private void printMenu() {
    console.println();
    console.println(MENU_BORDER);
    console.println("    Main Menu");
    console.println(MENU_BORDER);

    for (final MenuOption option : MenuOption.values()) {
      console.printf("    [%d] %s%n", option.getNumber(), option.getDescription());
    }

    console.println(MENU_BORDER);
  }
}
