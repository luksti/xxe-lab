package xxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
public class Launcher extends SpringBootServletInitializer implements WebApplicationInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Launcher.class);
  }

  public static void main(String[] args) {
    for (int i = 1; i <= 5; i++) {
      byte[] data = "0".getBytes();
      File file = new File("../../obj" + i);
      if(!file.exists() && !file.isDirectory()) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
          fos.write(data, 0, data.length);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    SpringApplication.run(Launcher.class, args);
  }

}
