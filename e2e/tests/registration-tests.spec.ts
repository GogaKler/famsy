import { test, expect } from "@playwright/test";
import { RegistrationPage } from "../pages/registration-page";
import * as dotenv from "dotenv";
dotenv.config();

test.describe("Тесты валидации формы", ()=>{
  let registrationPage: RegistrationPage;

  test.beforeEach(async ({ page })=>{
    registrationPage = new RegistrationPage(page);
    registrationPage.navigateTo();
  });

  test("Кнопка регистрации заблокирована, пока все поля не заполнены", async ()=>{
    await registrationPage.triggerInput(registrationPage.userNameInput);
    await registrationPage.triggerInput(registrationPage.emailInput);
    await registrationPage.triggerInput(registrationPage.passwordInput);
    await registrationPage.triggerInput(registrationPage.confirmPasswordInput);

    await expect(registrationPage.emptyUserNameInputWarning).toBeVisible();
    await expect(registrationPage.emptyEmailInputWarning).toBeVisible();
    await expect(registrationPage.emptyPasswordInputWarning).toBeVisible();
    await expect(registrationPage.emptyConfirmPasswordInputWarning).toBeVisible();

    await expect(registrationPage.registrationButton).toBeDisabled();
  });

  test("Пользователь не может зарегистрироваться с некорреткным Email", async ()=>{
    const incorrectEmail: string = "test_gmao.t"

    await registrationPage.triggerInput(registrationPage.emailInput, incorrectEmail);

    await expect(registrationPage.emailValidationWarning).toBeVisible();
  });

  test("Пользователь не может зарегистрироваться  с некорретным паролем", async () =>{

    await registrationPage.userNameInput.fill("tester");
    await registrationPage.emailInput.fill("tester@gmail.com");

    /*The password must:
      -Have 8 or more characters
      -Contain both numbers and letters*/
    const incorrectPasswords: string[] = [
      "test", // only letters && lenght < 8
      "1234", // only numbers && lenght < 8
      "test123", // lenght < 8
      "testtest", // only letters && lenght >= 8
      "12345678", //only numbers && lenght >= 8
    ];

    for (const password of incorrectPasswords) {
      await registrationPage.passwordInput.fill(password);
      await registrationPage.confirmPasswordInput.fill(password)
      await registrationPage.registrationButton.click();
      await expect(registrationPage.passwordValidationWarning).toBeVisible();
    }
  });

  test("Пользователь не может зарегистрироваться, если пароли не совпадают", async ()=>{
    await registrationPage.userNameInput.fill("tester");
    await registrationPage.emailInput.fill("test@gmail.com");

    await registrationPage.passwordInput.fill("test1234");
    await registrationPage.triggerInput(registrationPage.confirmPasswordInput, "test12345");//blur after entering the password

    await expect(registrationPage.differentPasswordWarning).toBeVisible();
  });

  test("Кнопка регистрации активна, если все поля заполнены корректно", async ()=>{
    await registrationPage.userNameInput.fill("tester");
    await registrationPage.emailInput.fill("test@gmail.com");
    await registrationPage.passwordInput.fill("test1234");
    await registrationPage.confirmPasswordInput.fill("test1234");

    await expect(registrationPage.registrationButton).toBeEnabled();
  });

  test("Нельзя зарегистрировать пользователя, если пользователь с таким username уже существует", async ()=>{
    const username: string = process.env.USER_NAME;
    await registrationPage.registration(username, "test1@gmail.com", "test1234", "test1234");

    await expect(registrationPage.userAlreadyExistsWarning).toBeVisible();
  });

  test("Нельзя зарегистрировать пользователя, если пользователь с таким email уже существует", async ()=>{
    const email: string = process.env.USER_EMAIL;
    await registrationPage.registration("test01", email, "test1234", "test1234");

    await expect(registrationPage.emailAlreadyExistsWarning).toBeVisible();
  });
});
