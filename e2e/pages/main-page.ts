import { BasePage } from "./base-page";
import { Page } from "@playwright/test";

export class MainPage extends BasePage {
  private readonly pageUrl: string = "http://localhost:8069/main";

  constructor(page: Page) {
    super(page);
  }

  get url(): string {
    return this.pageUrl;
  }
}