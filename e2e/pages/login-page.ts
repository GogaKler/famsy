import {BasePage} from "./base-page";
import {Page} from "@playwright/test";

export class LoginPage extends BasePage{
    constructor(page: Page) {
        super(page);

    }
}