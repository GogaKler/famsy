package ru.famsy.backend.common.validator;

import java.util.List;

public final class FileValidationRules {
  private final Long maxSizeInBytes;
  private final List<String> allowedMimeTypes;
  private final boolean image;
  private final int minWidth;
  private final int minHeight;

  private FileValidationRules(Builder builder) {
    this.maxSizeInBytes = builder.maxSizeInBytes;
    this.allowedMimeTypes = builder.allowedMimeTypes;
    this.image = builder.image;
    this.minWidth = builder.minWidth;
    this.minHeight = builder.minHeight;
  }

  public Long getMaxSizeInBytes() {
    return maxSizeInBytes;
  }

  public List<String> getAllowedMimeTypes() {
    return allowedMimeTypes;
  }

  public boolean isImage() {
    return image;
  }

  public int getMinWidth() {
    return minWidth;
  }

  public int getMinHeight() {
    return minHeight;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long maxSizeInBytes;
    private List<String> allowedMimeTypes;
    private boolean image;
    private int minWidth;
    private int minHeight;

    public Builder maxSizeInBytes(Long maxSizeInBytes) {
      this.maxSizeInBytes = maxSizeInBytes;
      return this;
    }

    public Builder allowedMimeTypes(List<String> allowedMimeTypes) {
      this.allowedMimeTypes = allowedMimeTypes;
      return this;
    }

    public Builder image() {
      this.image = true;
      return this;
    }

    public Builder minWidth(int minWidth) {
      this.minWidth = minWidth;
      return this;
    }

    public Builder minHeight(int minHeight) {
      this.minHeight = minHeight;
      return this;
    }

    public FileValidationRules build() {
      return new FileValidationRules(this);
    }
  }
}