import { BasePage } from "./base-page";
import { type Locator, type Page } from "@playwright/test";

export class RegistrationPage extends BasePage {
  static readonly baseUrl: string = "/auth/register";

  private readonly userNameInputID: string = "userNameInput";
  private readonly emailInputID: string = "emailInput";
  private readonly passwordInputID: string = "passwordInput";
  private readonly confirmPasswordInputID: string = "confirmPasswordInput";
  private readonly registrationButtonID: string = "registrationButton";

  private readonly emptyUserNameInputWarningID: string = "Поле Имя пользователя обязательно для заполнения";
  private readonly emptyPasswordInputWarningID: string = "Поле Email обязательно для заполнения";
  private readonly emptyEmailInputWarningID: string = "Поле Пароль обязательно для заполнения";
  private readonly emptyConfirmPasswordInputWarningID: string = "Поле Подтверждение пароля обязательно для заполнения";

  private readonly emailValidationWarningID: string = "Пожалуйста, введите действительный электронный адрес.";
  private readonly passwordValidationWarningID: string = "Пароль должен содержать минимум 8 символов, включая буквы и цифры";
  private readonly differentPasswordWarningID: string = "Поле Подтверждение пароля не совпадает";
  private readonly userAlreadyExistsWarningID: string = "Пользователь с таким username уже существует";
  private readonly emailAlreadyExistsWarningID: string = "Пользователь с таким email уже существует";

  constructor(page: Page) {
    super(page);
  }

  get userNameInput():Locator {
    return this.page.getByTestId(this.userNameInputID);
  }
  get emailInput():Locator {
    return this.page.getByTestId(this.emailInputID);
  }
  get passwordInput():Locator {
    return this.page.getByTestId(this.passwordInputID);
  }
  get confirmPasswordInput():Locator {
    return this.page.getByTestId(this.confirmPasswordInputID);
  }
  get registrationButton():Locator {
    return this.page.getByTestId(this.registrationButtonID);
  }
  get emptyUserNameInputWarning():Locator {
    return this.page.getByText(this.emptyUserNameInputWarningID);
  }
  get emptyPasswordInputWarning(): Locator {
    return this.page.getByText(this.emptyPasswordInputWarningID);
  }
  get emptyConfirmPasswordInputWarning(): Locator {
    return this.page.getByText(this.emptyConfirmPasswordInputWarningID);
  }
  get emptyEmailInputWarning(): Locator {
    return this.page.getByText(this.emptyEmailInputWarningID);
  }
  get emailValidationWarning() :Locator {
    return this.page.getByText(this.emailValidationWarningID);
  }
  get passwordValidationWarning(): Locator {
    return this.page.getByText(this.passwordValidationWarningID);
  }
  get differentPasswordWarning(): Locator {
    return this.page.getByText(this.differentPasswordWarningID);
  }
  get userAlreadyExistsWarning(): Locator {
    return this.page.getByText(this.userAlreadyExistsWarningID);
  }
  get emailAlreadyExistsWarning(): Locator {
    return this.page.getByText(this.emailAlreadyExistsWarningID);
  }

  async registration(username: string, email: string, password: string, confirmPassword: string) {
    await this.userNameInput.fill(username);
    await this.emailInput.fill(email);
    await this.passwordInput.fill(password);
    await this.confirmPasswordInput.fill(confirmPassword);
    await this.registrationButton.click();
  }

  async triggerInput(element: Locator, text: string = ""): Promise<void> {
    if (text != "") {
      await element.fill(text);
    }
    else {
      await element.focus();
    }
    await element.blur();
  }

  async navigateTo(): Promise<void> {
    await this.page.goto(RegistrationPage.baseUrl);
  }
}