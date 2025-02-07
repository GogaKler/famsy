import { BasePage } from "./base-page";
import { type Page, type Locator } from "@playwright/test";

export class LoginPage extends BasePage {
  static readonly baseUrl: string = "/login";

  private readonly loginInputID: string = "loginInput";
  private readonly passwordInputID: string = "passwordInput";
  private readonly loginButtonID: string = "loginButton";
  private readonly emptyLoginInputWarningID: string= "Поле Логин обязательно для заполнения";
  private readonly emptyPasswordInputWarningID: string = "Поле Пароль обязательно для заполнения";
  private readonly invalidCredsWarningID: string = "Неверный логин или пароль";

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
    return this.page.getByText(this.emptyLoginInputWarningID);
  }
  get emptyPasswordInputWarning(): Locator {
    return this.page.getByText(this.emptyPasswordInputWarningID);
  }
  get invalidCredsWarning(): Locator {
    return this.page.getByText(this.invalidCredsWarningID);
  }

  async login(login: string, password: string) {
    await this.loginInput.fill(login);
    await this.passwordInput.fill(password);
    await this.loginButton.click();
  }

  async navigateTo(): Promise<void> {
    await this.page.goto(LoginPage.baseUrl);
  }
}