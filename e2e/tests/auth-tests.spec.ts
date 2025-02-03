import { test, expect } from "@playwright/test";
import { LoginPage } from "../pages/login-page";
import { MainPage } from "../pages/main-page";

test.describe("Тесты авторизации", ()=>{
  let loginPage: LoginPage;
  test.beforeEach(async ({ page })=>{
    loginPage = new LoginPage(page);
    await loginPage.navigateTo("http://localhost:8069/auth/login");
  });

  test("Пользователь не может авторизоваться без кред", async ()=>{
    await loginPage.loginButton.click();

    await expect(loginPage.emptyLoginInputWarning).toBeVisible();
    await expect(loginPage.emptyPasswordInputWarning).toBeVisible();
  });

  test("Пользователь не может авторизоваться с некорретными кредами", async ()=>{
    await loginPage.login("1", "1");

    await expect(loginPage.invalidCredsWarning).toBeVisible();
  });

  test("Пользователь может авторизоваться с корректными кредами", async ({ page })=>{
    const mainPage = new MainPage(page);

    await loginPage.login("test", "test1234");

    await page.waitForURL(mainPage.url);
  });

});
