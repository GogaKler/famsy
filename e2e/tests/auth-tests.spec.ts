import { test, expect } from "@playwright/test";
import { LoginPage } from "../pages/login-page";
import { DashboardPage } from "../pages/dashboard-page";

test.describe("Валидации формы логина", ()=>{
  let loginPage: LoginPage;

  test.beforeEach(async ({ page })=>{
    loginPage = new LoginPage(page);
    await loginPage.navigateTo();
  });

  test("Пользователь не может авторизоваться с некорретными кредами", async ()=>{
    await loginPage.login("1", "1");

    await expect(loginPage.invalidCredsWarning).toBeVisible();
  });

  test("Пользователь не может авторизоваться с некорретным логином", async ()=>{
    await loginPage.login("1", "test1234");

    await expect(loginPage.invalidCredsWarning).toBeVisible();
  });

  test("Пользователь не может авторизоваться с некорретным паролем", async ()=>{
    await loginPage.login("test", "1");

    await expect(loginPage.invalidCredsWarning).toBeVisible();
  });

  test("Кнопка входа заблокирована пока не заполнены все необходимые поля", async ()=>{
    await loginPage.loginInput.focus();
    await loginPage.loginInput.blur();

    await loginPage.passwordInput.focus();
    await loginPage.passwordInput.blur();

    await expect(loginPage.emptyLoginInputWarning).toBeVisible();
    await expect(loginPage.emptyPasswordInputWarning).toBeVisible();
    await expect(loginPage.loginButton).toBeDisabled();
  })
});

test.describe("Авторизованный пользователь", ()=>{
  let loginPage : LoginPage;

  test.beforeEach(async ({ page })=>{
    loginPage = new LoginPage(page);
    await loginPage.navigateTo();
  });

  test("Пользователь может авторизоваться с корректными кредами", async ({ page })=>{
    await loginPage.login("test", "test1234");

    await page.waitForURL(DashboardPage.baseUrl);
  });

  test("Авторизованный пользователь не может перейти /auth/login", async ({ page })=>{
    await loginPage.login("test", "test1234");
    await page.waitForURL(DashboardPage.baseUrl);

    await loginPage.navigateTo();
    await page.waitForURL(page.url());
  });
});