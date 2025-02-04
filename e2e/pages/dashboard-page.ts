import { BasePage } from "./base-page";
import { Page } from "@playwright/test";

export class DashboardPage extends BasePage {
  private readonly baseUrl: string = "/Dashboard";

  constructor(page: Page) {
    super(page);
  }

  get url(): string {
    return this.baseUrl;
  }
}