package ru.famsy.backend.modules.files.dto;

public class FileUploadResponseDTO {
  private String fileName;
  private String fileUrl;

  public String getFileName() {
    return fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileUrl() {
    return fileUrl;
  }
  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }
}
