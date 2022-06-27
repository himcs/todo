package io.github.himcs.todo.cli;

import java.io.File;

public class BootStrap {
    public static void main(String[] args) {
        new ObjectFactory().createCommandLine(getFile()).execute(args);
    }

    public static File getFile() {
        final File homeDirectory = new File(System.getProperty("user.home"));
        final File todoHome = new File(homeDirectory, ".todo");
        if (!todoHome.exists()) {
            todoHome.mkdirs();
        }
        return new File(todoHome, "repository.json");
    }
}
