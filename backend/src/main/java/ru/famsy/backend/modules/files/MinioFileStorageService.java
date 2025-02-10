package ru.famsy.backend.modules.files;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.famsy.backend.modules.files.dto.FileUploadResponseDTO;

import java.util.UUID;

@Service
public class MinioFileStorageService {
  private final MinioClient minioClient;
  private final String bucketName;

  public MinioFileStorageService(
          MinioClient minioClient,
          @Value("${spring.minio.bucket.name}") String bucketName
  ) {
    this.minioClient = minioClient;
    this.bucketName = bucketName;
  }

  public FileUploadResponseDTO uploadFile(MultipartFile file, String folder) throws Exception {
    BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
    boolean exists = minioClient.bucketExists(bucketExistsArgs);

    if (!exists) {
      MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
      minioClient.makeBucket(makeBucketArgs);
    }

    String fileName = folder + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

    PutObjectArgs putObjectArgs = PutObjectArgs
            .builder()
            .bucket(bucketName)
            .object(fileName)
            .stream(file.getInputStream(), file.getSize(), -1)
            .contentType(file.getContentType())
            .build();

    minioClient.putObject(putObjectArgs);

    GetPresignedObjectUrlArgs presignedObjectUrlArgs = GetPresignedObjectUrlArgs
            .builder()
            .method(Method.GET)
            .bucket(bucketName)
            .object(fileName)
            .build();

    String url = minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);

    FileUploadResponseDTO fileUploadResponseDTO = new FileUploadResponseDTO();
    fileUploadResponseDTO.setFileName(fileName);
    fileUploadResponseDTO.setFileUrl(url);

    return fileUploadResponseDTO;
  }

  public byte[] getFile(String fileName, String folder) throws Exception {
    String objectPath = folder + "/" + fileName;
    GetObjectArgs getObjectArgs = GetObjectArgs
            .builder()
            .bucket(bucketName)
            .object(objectPath)
            .build();

    try (var stream = minioClient.getObject(getObjectArgs)) {
      return stream.readAllBytes();
    }
  }

  public void deleteFile(String fileName, String folder) throws Exception {
    String objectPath = folder + "/" + fileName;

    RemoveObjectArgs removeObjectArgs = RemoveObjectArgs
            .builder()
            .bucket(bucketName)
            .object(objectPath)
            .build();
    minioClient.removeObject(removeObjectArgs);
  }
}