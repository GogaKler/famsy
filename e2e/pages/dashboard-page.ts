import { BasePage } from "./base-page";
import { Page } from "@playwright/test";

export class DashboardPage extends BasePage {
  static readonly baseUrl: string = "/dashboard";

  constructor(page: Page) {
    super(page);
  }

  async navigateTo(): Promise<void> {
    await this.page.goto(DashboardPage.baseUrl);
  }
}