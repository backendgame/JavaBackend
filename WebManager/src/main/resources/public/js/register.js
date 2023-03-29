'use strict';

import { register, sendRegiterVerifyCode } from "./apis/user.js";
import { errorNotify,
    setErrorInput, setStateButton,
    stringIsNullOrEmpty,
    successNotify, togglePwdVisibility,
    resetErrorForm, 
    isValidEmail,
    redirectTo,
    showGlobalLoading,
    hideGlobalLoading} from "./utils.js";

const REGISTER_FORM = "registerForm";
const elemStepOne = document.querySelector(".signup__step-1");
const elemStepTwo = document.querySelector(".signup__step-2");
const progressItems = document.querySelectorAll(".signup__progress-item");

$(document).ready(() => {
    $("#registerBtn").on("click", onRegister);
    $(".v-toggle-password").on("click", togglePwdVisibility);
    $("#btn-sendCode").on("click", onSendVerifyCode);
    $("#registerbackIcon").click(e => {
        elemStepTwo.classList.add("hidden");
        elemStepOne.classList.remove("hidden");
        progressItems.forEach((item) =>
          item.classList.toggle("signup__progress-item--active")
        ); 
    })
    hideGlobalLoading();

    //Register
    // const formSignUp = document.querySelector(".signup__form");
    // const elemStepOne = document.querySelector(".signup__step-1");
    // const elemStepTwo = document.querySelector(".signup__step-2");
    // const inlineInputs = document.querySelectorAll(".signup__inline-input");
    // const progressItems = document.querySelectorAll(".signup__progress-item");
  
    // formSignUp.addEventListener("submit", (event) => {
    //   event.preventDefault();
    //   elemStepOne.classList.add("hidden");
    //   elemStepTwo.classList.remove("hidden");
    //   progressItems.forEach((item) =>
    //     item.classList.toggle("signup__progress-item--active")
    //   );
    // });
  
    // inlineInputs.forEach((inlineInput, index) => {
    //   if (index < inlineInputs.length - 1) {
    //     inlineInput.addEventListener("keyup", (event) => {
    //       if (event.key.match(/^[0-9a-zA-Z]$/)) {
    //         inlineInputs[index + 1].focus();
    //       }
    //     });
    //   }
    // });
})


async function onRegister() {
   console.log("hihii")
    let codes = $("#registerCode > input");
    let code = [];
    codes.each((i, e) => {
        let value = $(e).val();
        code.push(value)
    });
    let userData = JSON.parse(localStorage.getItem("register_credential") || "{}");
    let {email, password} = userData;
    let userId = localStorage.getItem("userId");
    let verifyCode = code.join("").trim(); 
    showGlobalLoading();
    const res = await register({userId, password, verifyCode});
    hideGlobalLoading();

    if(!res.requestStatus) {
        errorNotify(null, res.message || "Error when register!");
        return;
    }
    else {
        successNotify(null, "Register successfully!");
        setTimeout(() => {
            redirectTo("/login");
        }, 2000);
    }
}

async function onSendVerifyCode(e) {
    e.preventDefault();
    resetErrorForm("inputUserName");
    resetErrorForm("inputPassword"); 
    
    const registerData = new FormData(document.getElementById(REGISTER_FORM));
    const email = registerData.get("email");
    const password = registerData.get("password");

    if(stringIsNullOrEmpty(email)) {
        setErrorInput("inputUserName", "Email is required!");
        return;
    }

    if(!isValidEmail(email)) {
        setErrorInput("inputUserName", "Email invalid format!");
        return;
    }

    if(stringIsNullOrEmpty(password)) {
        setErrorInput("inputPassword", "Password is required!");
        return;
    }

    showGlobalLoading();
    let res = await sendRegiterVerifyCode({email: email.trim()});
    hideGlobalLoading();

    if(!res.requestStatus) {
        errorNotify(null, res.message || "Error when sending code!");
        return;
    }
    else {
        if(res.data && res.data.userId) {
            localStorage.setItem("userId", res.data.userId);
            localStorage.setItem("register_credential", JSON.stringify({email,password}));
        }
        $("#emailAddr").text(email)
        elemStepOne.classList.add("hidden");
        elemStepTwo.classList.remove("hidden");
        progressItems.forEach((item) =>
          item.classList.toggle("signup__progress-item--active")
        );
        successNotify(null, "Please check your mail to get verification code!");
    }
}

