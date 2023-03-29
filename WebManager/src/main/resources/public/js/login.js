'use strict';

import { login } from "./apis/user.js";
import { TOKEN_NAME,TABLES } from "./CONFIG.js";
import { vModal } from "./global_functions.js";
import { errorNotify, redirectTo, resetErrorForm, setErrorInput, setStateButton, stringIsNullOrEmpty, successNotify, togglePwdVisibility } from "./utils.js";

$(document).ready(() => {
    $("#loginBtn").on("click", onLogin);

    //Set event
    let rememberData = localStorage.getItem("loginRememberAccount") 
    ? JSON.parse(localStorage.getItem("loginRememberAccount")) 
    : null;

    if(rememberData) {
        $('input[name="email"]').val(rememberData.email);
        $('input[name="password"]').val(rememberData.password);
    }

    //Toggle eye password
    $(".v-toggle-password").on("click", togglePwdVisibility)
})


async function onLogin() {
    resetErrorForm("inputUserName")
    resetErrorForm("inputPassword")

    const loginData = new FormData(document.getElementById("loginForm"));
    const password = loginData.get("password");
    const email = loginData.get("email");
    const rememberCheckbox = loginData.get("rememberCheckbox");

    if(stringIsNullOrEmpty(email)) {
        setErrorInput("inputUserName", "Email is required!");
        return;
    }

    if(stringIsNullOrEmpty(password)) {
        setErrorInput("inputPassword", "Password is required!");
        return;
    }

    if(rememberCheckbox != null) {
        localStorage.setItem("loginRememberAccount", JSON.stringify({email, password}));
    }
    else {
        localStorage.removeItem("loginRememberAccount");
    }
    setStateButton("loginBtn", "Logging...","loading");
    const res = await login({email, password});
    setStateButton("loginBtn", "Login","no-loading");
    if(!res.requestStatus) {
        errorNotify(null, res.message || "Login failed!");
        return;
    }
    else {
        localStorage.setItem(TOKEN_NAME, res.data.token);
        let tables = {};
        Object.entries(res.data.Tables).map(item => {
            let values = {};
            Object.entries(item[1]).map(iitem => {
                if(typeof iitem[1] == "object") {
                    values = {...values, [`${iitem[0]}`] : iitem[1]};
                    delete item[1][`${iitem[0]}`]
                }
            });
            if(Object.values(values).length > 0) {
                item[1][`listSubTable`] = values;
            }
            else {
                item[1][`listSubTable`] = null;
            }
            tables = {...tables, [`${item[0]}`]: item[1]};
        });
        localStorage.setItem(TABLES, JSON.stringify(tables || []))
        successNotify(null, "Login successfully!");
        setTimeout(() => {
            redirectTo("/user");
        }, 2000)
    }
}

function onLogout() {
    let onActionLogOut = (hideModal) => {
        localStorage.removeItem(TOKEN_NAME);
        hideModal();
        redirectTo("/login");
    }
    vModal("Confirm logout", 
    modalDeleteTable, modalDeleteTableFooter,"vModalClose", "onLogout", onActionLogOut);
}

export {
    onLogout
}

const modalDeleteTableFooter = `
    <div class="d-flex justify-content-end">
        <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Cancel</button>
        <button type="button" id="onLogout" class="btn btn-danger">Logout</button>
    </div>
    `
const modalDeleteTable = `
    <div>
        Are you sure to logout?
    </div>
    `