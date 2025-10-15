package com.github.group5.public_transport_project;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class QuayRepository {
      private static List<QuayLoader.Quay> quays = List.of(); 

    private QuayRepository() {}

    public static void init(Path jsonPath) throws IOException {
        ArrayList<QuayLoader.Quay> loaded = QuayLoader.load(jsonPath);
        
        quays = Collections.unmodifiableList(loaded);
    }

    public static List<QuayLoader.Quay> getQuays() {
        return quays;
    }
    
}

