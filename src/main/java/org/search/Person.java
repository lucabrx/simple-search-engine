package org.search;

import java.util.Arrays;

public class Person {
    private final String name;
    private final String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static Person parseFromInput(String input) {
        String[] parts = input.split(" ");
        String email = "";
        String name;

        if (parts[parts.length - 1].contains("@")) {
            email = parts[parts.length - 1];
            name = String.join(" ", Arrays.copyOf(parts, parts.length - 1));
        } else {
            name = input;
        }
        return new Person(name.trim(), email);
    }


    @Override
    public String toString() {
        return name + (email.isEmpty() ? "" : " " + email);
    }
}