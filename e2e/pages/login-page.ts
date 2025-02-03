import { BasePage } from "./base-page";
import { type Page, type Locator } from "@playwright/test";

export class LoginPage extends BasePage {
  private readonly loginInputID = "loginInput";
  private readonly passwordInputID = "passwordInput";
  private readonly loginButtonID = "loginButton";
  private readonly emptyLoginInputWarningID= "#input_0-";
  private readonly emptyPasswordInputWarningID = "#input_1-";
  private readonly invalidCredsWarningID = "Неверный логин или пароль";

  constructor(page: Page) {
    super(page);
  }
  get loginInput(): Locator {
    return this.page.getByTestId(this.loginInputID);
  }
  get passwordInput(): Locator {
    return this.page.getByTestId(this.passwordInputID);
  }
  get loginButton(): Locator {
    return this.page.getByTestId(this.loginButtonID);
  }
  get emptyLoginInputWarning(): Locator {
    return this.page.locator(this.emptyLoginInputWarningID);
  }
  get emptyPasswordInputWarning(): Locator {
    return this.page.locator(this.emptyPasswordInputWarningID);
  }
  get invalidCredsWarning(): Locator {
    return this.page.getByText(this.invalidCredsWarningID);
  }

  async login(login: string, password: string) {
    await this.loginInput.fill(login);
    await this.passwordInput.fill(password);
    await this.loginButton.click({ timeout: 10000 });
  }
}