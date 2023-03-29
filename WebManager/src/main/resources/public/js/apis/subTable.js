import {httpRequest} from "./base.js";

async function createLeaderboard(data) {
    const resp = await httpRequest.post("/api/lobby/data/sub_table/Create-Leaderboard", data);
    return resp;
}

async function getSubTableData(payload) {
    return filterByLatestAcc({
        "text": 10,
        "regionId": Number(payload.regionId),
        "tableId": Number(payload.tableId),
        "subTableID": Number(payload.subTableID)
    });
}

async function filterByLatestAcc(data) {
    const resp = await httpRequest.post("/api/lobby/data/leaderboard/QuerryFull-Latest", {
        "length": Number(data.text),
        "regionId": Number(data.regionId),
        "tableId": Number(data.tableId),
        "subTableID": Number(data.subTableID)
    });
    return resp;
}

async function filterDataInSubTable(type, payload) {
    console.log(type, type == "User Id")
    switch(type.trim()) {
        case 'User Id':
            let [begin, length] = payload.text.split("-");
            if(!begin || !length) {
                let arr = payload.text.split(",");
                if(typeof arr == "object") {
                    return filterByListUser({...payload, listIndex: arr});
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
    }
}

async function filterByListUser(data) {
    const resp = await httpRequest.post("/api/lobby/data/leaderboard/QuerryFull-Index", {
        "listIndex": data.listIndex.map(i => Number(i)),
        "regionId": Number(data.regionId),
        "tableId": Number(data.tableId),
        "subTableID": Number(data.subTableID)
    });
    return resp;
}

async function filterByRangeUserId(data) {
    const resp = await httpRequest.post("/api/lobby/data/leaderboard/QuerryFull-Range", {
        "indexBegin": Number(data.userIdBegin),
        "indexEnd": Number(data.length),
        "regionId": Number(data.regionId),
        "tableId": Number(data.tableId),
        "subTableID": Number(data.subTableID)
    });
    return resp;
}

async function updateLeaderBoard(updatedData) {
    const endpoint = "/api/lobby/data/leaderboard/UpdateData";
    const resp = await httpRequest.post(endpoint, updatedData);
    return resp;
}

async function loadSubTableConfig(data) {
    const endpoint = "/api/lobby/data/leaderboard/LoadConfig";
    const resp = await httpRequest.post(endpoint, data);
    return resp;
}

async function updateLeaderboard(data) {
    const endpoint = "/api/lobby/data/leaderboard/Config";
    const resp = await httpRequest.post(endpoint, data);
    return resp;
}

async function updateSubTblAccessToken(data) {
    const endpoint = "/api/lobby/data/sub_table/Config-AccessToken";
    const resp = await httpRequest.post(endpoint, data);
    return resp;
}
async function updateSubTblReadToken(data) {
    const endpoint = "/api/lobby/data/sub_table/Config-ReadToken";
    const resp = await httpRequest.post(endpoint, data);
    return resp;
}

async function updateSubTblWriteToken(data) {
    const endpoint = "/api/lobby/data/sub_table/Config-WriteToken";
    const resp = await httpRequest.post(endpoint, data);
    return resp;
}


export {
    createLeaderboard,
    filterDataInSubTable,
    getSubTableData,
    updateLeaderBoard,
    loadSubTableConfig,
    updateLeaderboard,
    updateSubTblAccessToken, updateSubTblReadToken, updateSubTblWriteToken
}