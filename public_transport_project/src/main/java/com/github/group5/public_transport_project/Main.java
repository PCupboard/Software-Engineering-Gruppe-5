package com.github.group5.public_transport_project;

import com.github.group5.public_transport_project.model.User;
import com.github.group5.public_transport_project.Service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserService service = new UserService(); //Oppretter en instans av UserService
        Scanner scanner = new Scanner(System.in); //Scanner for å lese input

        while (true) {
            System.out.println("\nKollektiv App");
            System.out.println("1. Registrer bruker");
            System.out.println("2. Logg inn");
            System.out.println("3. Avslutt");
            System.out.print("Velg: ");

            String valg = scanner.nextLine(); //Leser input

            switch (valg) {
                case "1": //Registrering
                    System.out.print("Brukernavn: ");
                    String regUser = scanner.nextLine();
                    System.out.print("Passord: ");
                    String regPass = scanner.nextLine();
                    System.out.print("E-post: ");
                    String regEmail = scanner.nextLine();

                    boolean registrert = service.register(regUser, regPass, regEmail); //Registrerer brukeren
                    if (registrert) {
                        System.out.println("Registrering vellykket!");
                    } else {
                        System.out.println("Brukernavn finnes allerede!");
                    }
                    break;

                case "2": //Login
                    System.out.print("Brukernavn: ");
                    String logUser = scanner.nextLine();
                    System.out.print("Passord: ");
                    String logPass = scanner.nextLine();

                    User user = service.login(logUser, logPass);
                    if (user != null) {
                        System.out.println("Login vellykket! Velkommen, " + user.getUsername() + "!");
                    } else {
                        System.out.println("Feil brukernavn eller passord!");
                    }
                    break;

                case "3": //Avslutte
                    System.out.println("Avslutter programmet");
                    scanner.close();
                    return;

                default: //Ugyldig valg
                    System.out.println("Ugyldig valg. Prøv igjen.");
            }
        }
    }
}