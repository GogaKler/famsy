import { Page } from "@playwright/test";

export abstract class BasePage {
  protected constructor(protected page: Page) {
    this.page = page;
  }

  async navigateTo(url: string) {
    await this.page.goto(url);
  }

}
