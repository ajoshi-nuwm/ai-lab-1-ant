package edu.arturjoshi.lab1ant;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.arturjoshi.lab1ant.ant.Ant;
import edu.arturjoshi.lab1ant.domain.Region;
import edu.arturjoshi.lab1ant.utils.FileHelper;

@SpringBootApplication
public class Lab1AntApplication {

    @Value("${in-folder}")
    private String inFolder;

    @Value("${out-folder}")
    private String outFolder;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return strings -> {
            List<String> files = FileHelper.getFileNamesInFolder(inFolder);

            for (String fileName : files) {
                List<String> data = FileHelper.readFromFile(fileName);
                Region region = new Region(data.get(0), data.subList(1, data.size()));

                int distance = 0;
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < 10000; i++) {
                    Ant ant = new Ant(region.getRandomCity());
                    while (ant.visitCity()) {
                    }

                    if (ant.getVisited().size() == region.getCities().size()) {
                        if (distance == 0 || ant.getDistance() < distance) {
                            distance = ant.getDistance();
                            output.append(ant.getVisited())
                                    .append("\n")
                                    .append(ant.getDistance())
                                    .append("\n");
                        }
                    }
                }
                FileHelper.writeToFile(outFolder, fileName, output.toString());
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab1AntApplication.class, args);
    }
}
