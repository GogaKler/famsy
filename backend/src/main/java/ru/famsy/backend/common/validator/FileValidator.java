package ru.famsy.backend.common.validator;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.famsy.backend.common.validator.exception.FileValidationException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileValidator {
  private final Tika tika = new Tika();

  public void validateFile(MultipartFile file, FileValidationRules rules) {
    if (file == null || file.isEmpty()) {
      throw new FileValidationException("Файл не может быть пустым.");
    }

    // Проверка размера файла
    if (rules.getMaxSizeInBytes() != null && file.getSize() > rules.getMaxSizeInBytes()) {
      throw new FileValidationException("Размер файла превышает допустимый лимит " + rules.getMaxSizeInBytes() + " байт.");
    }

    String detectedMimeType;
    try (InputStream is = file.getInputStream()) {
      detectedMimeType = tika.detect(is);
    } catch (IOException e) {
      throw new FileValidationException("Невозможно определить MIME-тип файла.", e);
    }

    if (rules.getAllowedMimeTypes() != null && !rules.getAllowedMimeTypes().isEmpty()) {
      boolean allowed = rules
              .getAllowedMimeTypes()
              .stream()
              .anyMatch(type -> type.equalsIgnoreCase(detectedMimeType));

      if (!allowed) {
        throw new FileValidationException("Недопустимый MIME-тип файла: " + detectedMimeType);
      }
    }

    if (rules.isImage()) {
      try (InputStream is = file.getInputStream()) {
        BufferedImage image = ImageIO.read(is);
        if (image == null) {
          throw new FileValidationException("Невозможно прочитать изображение для валидации.");
        }

        if (rules.getMinWidth() != null && image.getWidth() < rules.getMinWidth()) {
          throw new FileValidationException("Ширина изображения должна быть не меньше " + rules.getMinWidth() + " пикселей.");
        }

        if (rules.getMinHeight() != null && image.getHeight() < rules.getMinHeight()) {
          throw new FileValidationException("Высота изображения должна быть не меньше " + rules.getMinHeight() + " пикселей.");
        }

      } catch (IOException e) {
        throw new FileValidationException("Ошибка при валидации изображения.", e);
      }
    }
  }
}