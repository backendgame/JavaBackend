import { TOKEN_NAME } from "../CONFIG.js";
import { httpRequest } from "./base.js";
import { redirectTo } from '../utils.js';

const CREATE_TABLE = "/api/lobby/data/tables/Table-Create";
const CHANGE_TABLE_NAME = "/api/lobby/data/tables/Table-ChangeName";
const GET_TABLE_INFO = "/api/lobby/data/tables/Table-GetInfo"
const DELETE_TABLE_INFO = "/api/lobby/data/tables/Table-Delete"
const RELOAD_ALL_TABLE = "/api/lobby/home/Home-Reload";
const ADD_ROW_TO_DB = "/api/lobby/data/table_rows/Row-CreateAccount";
const GET_TABLE_ID = "/api/lobby/data/table_rows/Row-Config";
const FILTER_BY_CREDENTIAL = "/api/lobby/data/table_rows/BinaryRowQuerry-Credential";

async function createTable(tableName, description, regionId, tokenLifeTimeMili,describeTables) {
    const resp = await httpRequest.post(CREATE_TABLE, {
      tableName, 
      description,
      regionId: Number(regionId),
      tokenLifeTimeMili: Number(tokenLifeTimeMili),
      describeTables
    });
    return resp;
}

async function changeTableNameOrDes(tableName, description, regionId, tableId) {
  const resp = await httpRequest.post(CHANGE_TABLE_NAME, {
    tableName, 
    description, 
    regionId: Number(regionId), 
    tableId: Number(tableId)});
  return resp;
}

async function deleteDbTable(regionId, tableId) {
  const resp = await httpRequest.post(DELETE_TABLE_INFO, {
    regionId: Number(regionId), 
    tableId: Number(tableId)});
  return resp;
}

async function getTableInfo(regionId, tableId) {
  const resp = await httpRequest.post(GET_TABLE_INFO, {
    regionId: Number(regionId), 
    tableId: Number(tableId)});
  return resp;
}

async function reloadTableData() {
  const resp = await httpRequest.post(RELOAD_ALL_TABLE, {});
  return resp;
}

async function addRowToDB(payload) {
  const resp = await httpRequest.post(ADD_ROW_TO_DB, payload);
  return resp; 
}

async function getTableData(payload) {
  return filterByLatestAcc({
    "text": 10,
    "regionId": Number(payload.regionId),
    "tableId": Number(payload.tableId),
  });
}

async function updateLoginRules(payload, tableId, regionId) {
  const resp = await httpRequest.post("/api/lobby/data/tables/Config-LoginRule", {
    ...payload,
    "tableId": Number(tableId),
    "regionId": Number(regionId)
  });
  return resp; 
}

async function filterDataInTable(type, payload) {
  switch(type.trim()) {
    case 'User':
      let [begin, length] = payload.text.split("-");
      if(!begin || !length) {
        let arr = payload.text.split(",");
        if(typeof arr == "object") {
          return filterByListUser({...payload, listUserId: arr});
        }
        return {
          error: true,
          message: "Wrong format!"
        }
      }
      else {
        return filterByRangeUserId({...payload, userIdBegin: begin, length});
      }
    case 'Latest account':
      return filterByLatestAcc(payload);
    case 'Credential':
      return filterByCredential(payload);
  }
}

async function filterByCredential(data) {
  console.log(data)
  const resp = await httpRequest.post(FILTER_BY_CREDENTIAL, {
    "Credential": data.text,
    "regionId": Number(data.regionId),
    "tableId": Number(data.tableId),
  });
  return resp;
}

async function filterByLatestAcc(data) {
  const resp = await httpRequest.post("/api/lobby/data/table_rows/BinaryRowQuerry-LatestAccount", {
    "length": Number(data.text),
    "regionId": Number(data.regionId),
    "tableId": Number(data.tableId)
  });
  return resp;
}

async function filterByListUser(data) {
  const resp = await httpRequest.post("/api/lobby/data/table_rows/BinaryRowQuerry-ListUserId", {
    "listUserId": data.listUserId.map(i => Number(i)),
    "regionId": Number(data.regionId),
    "tableId": Number(data.tableId)
  });
  return resp;
}

async function updateRowInTableData(tableId, regionId, listUpdates) {
  const endpoint = "/api/lobby/data/table_rows/Update-UserData";
  const resp = await httpRequest.post(endpoint, {
      "listUpdate": listUpdates,
      "tableId": Number(tableId),
      "regionId": Number(regionId)
  });
  return resp;
}

async function filterByRangeUserId(data) {
  const resp = await httpRequest.post("/api/lobby/data/table_rows/BinaryRowQuerry-UserIdRange", {
    "userIdBegin": Number(data.userIdBegin),
    "userIdEnd": Number(data.length),
    "regionId": Number(data.regionId),
    "tableId": Number(data.tableId),
  });
  return resp;
}

async function configAccessToken(data) {
  const resp = await httpRequest.post("/api/lobby/data/tables/Config-AccessToken", data);
  return resp;
}

async function configReadToken(data) {
  const resp = await httpRequest.post("/api/lobby/data/tables/Config-ReadToken", data);
  return resp;
}

async function configWriteToken(data) {
  const resp = await httpRequest.post("/api/lobby/data/tables/Config-WriteToken", data);
  return resp;
}

async function configRowStructure(data) {
  const resp = await httpRequest.post("/api/lobby/data/tables/Config-DescribeTables", data);
  return resp;
}

async function configEmailAndaAppPassword(data) {
  const resp = await httpRequest.post("/api/lobby/data/tables/Config-MailService", data);
  return resp;
}

export {
  createTable,
  changeTableNameOrDes,
  deleteDbTable,
  getTableInfo,reloadTableData,
  addRowToDB, getTableData,filterDataInTable,
  configAccessToken,configReadToken, configWriteToken,
  configRowStructure,
  configEmailAndaAppPassword,
  updateRowInTableData, updateLoginRules
}