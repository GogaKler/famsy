import {BasePage} from "./base-page";
import {Page, Locator} from "@playwright/test";

// @ts-ignore
export class LoginPage extends BasePage {
    private readonly loginInputID = "loginInput";
    private readonly passwordInputID = "passwordInput";
    private readonly loginButtonID = "loginButton";
    private readonly forgotPasswordButtonID = "forgotPasswordButton";
    private readonly rememberMeCheckboxID = "rememberMeCheckbox";
    private readonly registrationButtonID = "registrationButton";

    constructor(page: Page) {
        super(page);
    }
    get loginInput():Locator{
        return this.page.getByTestId(this.loginInputID);
    }
    get passwordInput(): Locator{
        return this.page.getByTestId(this.passwordInputID);
    }
    get loginButton(): Locator{
        return this.page.getByTestId(this.loginButtonID);
    }
    get forgotPasswordButton(): Locator{
        return this.page.getByTestId(this.forgotPasswordButtonID);
    }
    get rememberMeCheckbox(): Locator{
        return this.page.getByTestId(this.rememberMeCheckboxID);
    }
    get registrationButton(): Locator{
        return this.page.getByTestId(this.registrationButtonID);
    }

    async login(login: string, password: string) {
        await this.loginInput.fill(login);
        await this.passwordInput.fill(password);
        await this.loginButton.click();
    }


}