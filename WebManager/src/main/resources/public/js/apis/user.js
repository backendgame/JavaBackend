import { httpRequest } from "./base.js";

const RELOAD_USER = "/api/lobby/home/Home-Reload";
const LOGIN_USER = "/api/login_screen/Login-with-email-password";
const REGISTER_USER = "/api/login_screen/Complete-VerifyCode";
const SEND_VERIFY_CODE = "/api/login_screen/Request-VerifyCode";

async function getUserData() {
    const resp = await httpRequest.post(RELOAD_USER, {});
    if(resp.requestStatus) {
      return true;
    }
    return false;
}

async function login(data) {
  const resp = await httpRequest.post(LOGIN_USER, data, false);
  return resp;
}

async function sendRegiterVerifyCode(data) {
  const resp = await httpRequest.post(SEND_VERIFY_CODE, data, false);
  return resp;
}

async function register(data) {
  const resp = await httpRequest.post(REGISTER_USER, data, false);
  return resp;
}


export {
  getUserData,
  login,
  sendRegiterVerifyCode,
  register
}