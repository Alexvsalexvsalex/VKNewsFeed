package ru.shishkin.runner;

import ru.shishkin.config.ClientConfiguration;
import ru.shishkin.vk.VKClient;
import ru.shishkin.vk.VKManager;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClientConfiguration clientConfiguration;
        try {
            clientConfiguration = new ClientConfiguration();
        } catch (IOException e) {
            System.err.println("Cannot read properties");
            return;
        }
        try (Scanner scanner = new Scanner(System.in)) {
            VKClient client = new VKClient(clientConfiguration);
            VKManager manager = new VKManager(client);
            System.out.println("Type '<tag> <number of hours>'");
            String query = scanner.next();
            int hours = scanner.nextInt();
            manager.getPostsStatistics(query, hours).forEach(System.out::println);
        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("Error string format");
        }
    }
}
