import { addRowToDB, filterDataInTable, getTableData, updateRowInTableData } from "./apis/table.js";
import {avatarIcon, dataType, isDataString, permissionIcon, statusIcon, TABLE_OFFSET} from "./CONFIG.js";
import {
    getQueryParams,
    useAuthentication,
    setEventForGeneralComponent,
    vModal,
    formatDate,
    getLeaderboardDataFromKey
} from "./global_functions.js";
import {
    convertCamelCaseToNormal, copyToClipboard,
    errorNotify,
    getDatabaseIcon,
    getDataIcon, getDataTextByDataType,
    hideGlobalLoading, parseDataToSuitableType,
    showGlobalLoading,
    successNotify
} from "./utils.js";
import {
    filterDataInSubTable,
    getSubTableData,
    loadSubTableConfig,
    updateLeaderboard,
    updateLeaderBoard, updateSubTblAccessToken, updateSubTblReadToken, updateSubTblWriteToken
} from "./apis/subTable.js";
let currentLeaderboard;
let tableDataRow = {listDescribeTables: [], listUserId: []}
const visibleColsName = "leaderboardTableCols";
let sort = {}
let subTableConfig;

$(document).ready(() => {
    useAuthentication(true)
    .then(async (auth) => {
        if(!auth) return;
        setEventForGeneralComponent();
        //check params is appeared
        let dbId = getQueryParams("dbId");
        let leaderboardId = Number(getQueryParams("subId"));
        if(!dbId || dbId.trim() == "") {
            $(".app-main").html("<div class='d-flex align-items-center h3' style='color: red; font-weight: bold'>Nothing to show!</div>");
            return;
        }
        currentLeaderboard = await getLeaderboardFromKey(dbId,leaderboardId);
        if(currentLeaderboard) {
            $("#leaderboardName").text(currentLeaderboard.subTable.TableName || "")
        }
        //Get all table data
        getData()
            .then(() => setColumn())
            .then(() => setTableData())
            .then(() => setEventTable())
            .then(() => setVisibleColFeature())
            .then(() => setSortFeature())
            .then(() => setLeaderboardConfig())
            .then(() => setSubTableConfig())
        // Init modal create row
        initModalAddRow();
        //Init search input
        initSearchInput();
        initDropdownPlaceHolder();
    })
});

function setSubTableConfig() {
    $("#subTableConfig").unbind("click").click(e => {
        const onClosed = (isUpdatedData = false) => {
            getData()
                .then(() => setColumn())
                .then(() => setTableData())
                .then(() => setEventTable())
                .then(() => setVisibleColFeature())
                .then(() => setSortFeature())
                .then(() => setSubTableConfig())
        };
        vModal("Config leaderboard",
            getBodyConfigLeaderboard(),
            modalConfigLeaderboardFooter,
            "vModalClose",
            "addBtn",
            () => {}, onClosed);
        $(".saveConfigData").unbind("click").click(async e => {
            let label = $(e.currentTarget).attr("data-label") || null;
            let inputVal = $(e.currentTarget.parentNode.previousElementSibling).val();
            let payload = {
                subTableID: Number(getQueryParams("subId")),
                tableId: Number(currentLeaderboard.tableData.tableId),
                regionId: Number(currentLeaderboard.tableData.regionId)
            };
            let resp = null;
            switch (label) {
                case 'ColumnName':
                    payload = {
                        ...payload,
                        ...subTableConfig,
                        ColumnName: inputVal,
                        NumberRow: Number(subTableConfig.NumberRow),
                        "DataType": Number(currentLeaderboard.subTable.DataType),
                    };
                    showGlobalLoading();
                    resp = await updateLeaderboard(payload);
                    hideGlobalLoading();
                    if(resp.requestStatus) {
                        currentLeaderboard.subTable.ColumnName = inputVal;
                    }
                    break;
                case 'NumberRow':
                    payload = {
                        ...payload,
                        ...subTableConfig,
                        NumberRow: Number(inputVal),
                        "DataType": Number(currentLeaderboard.subTable.DataType)
                    }
                    showGlobalLoading();
                    resp = await updateLeaderboard(payload);
                    hideGlobalLoading();
                    if(resp.requestStatus) {
                        currentLeaderboard.subTable.NumberRow = inputVal;
                    }
                    break;
                case 'DataType':
                    payload = {
                        ...payload,
                        ...subTableConfig,
                        DataType: Number(inputVal),
                        NumberRow: Number(subTableConfig.NumberRow)
                    }
                    showGlobalLoading();
                    resp = await updateLeaderboard(payload);
                    hideGlobalLoading();
                    if(resp.requestStatus) {
                        currentLeaderboard.subTable.DataType = inputVal;
                    }
                    break;
                case 'AccessToken':
                    payload = {
                        ...payload,
                        ...subTableConfig,
                        accessToken: inputVal,
                    }
                    showGlobalLoading();
                    resp = await updateSubTblAccessToken(payload);
                    hideGlobalLoading();
                    break;
                case 'WriteToken':
                    payload = {
                        ...payload,
                        ...subTableConfig,
                        writeKey: inputVal,
                    }
                    showGlobalLoading();
                    resp = await updateSubTblWriteToken(payload);
                    hideGlobalLoading();
                    break;
                case 'ReadToken':
                    payload = {
                        ...payload,
                        ...subTableConfig,
                        readToken: inputVal,
                    };
                    showGlobalLoading();
                    resp = await updateSubTblReadToken(payload);
                    hideGlobalLoading();
                    break
            };
            if(resp && resp.requestStatus) {
                successNotify("Update config success!");
                await setLeaderboardConfig();
            }
            else {
                errorNotify(resp.message || "Something went wrong!");
            }
        });
        $(".copyKey").unbind("click").click(e => {
            copyToClipboard($(e.currentTarget).attr("value") || "");
            successNotify("Copied!");
        })
    })
}

async function setLeaderboardConfig() {
    const resp = await loadSubTableConfig({
        subTableID: Number(getQueryParams("subId")),
        tableId: Number(currentLeaderboard.tableData.tableId),
        regionId: Number(currentLeaderboard.tableData.regionId)
    });
    subTableConfig = resp.data;
}


function buildRow(rowData, describe) {
    if(describe && describe.Type == dataType.STATUS) {
        return `<td class="${describe ? describe.ColumnName : ""}">${getDataIcon(statusIcon)(rowData).icon}</td> \n`
    }
    if(describe && describe.Type == dataType.PERMISSION) {
        return `<td class="${describe ? describe.ColumnName : ""}">${getDataIcon(permissionIcon)(rowData).icon}</td> \n`
    }
    if(describe && describe.Type == dataType.AVARTAR) {
        return `<td class="${describe ? describe.ColumnName : ""}">${getDataIcon(avatarIcon)(rowData).icon}</td> \n`
    }
    return `
    <td class="${describe ? describe.ColumnName : ""}">
        ${rowData != null && typeof rowData != undefined ? rowData : '-'}
    </td> \n`
}


function initDropdownPlaceHolder() {
    $("#searchDropdown .item").click(e => {
        let type = $(e.currentTarget).attr("value")
        switch(type) {
            case 'UserLatest':
                $("#searchInputId").attr("placeholder", "Ex: 100")
                break;
            case 'User':
                $("#searchInputId").attr("placeholder", "Ex: 1,2,3,4 or 1-10")
                break;
            default:
                $("#searchInputId").attr("placeholder", "User credential...")
                break;
        }
    })
}

function initSearchInput() {
    $("#searchButtonId").unbind("click").click(async e => {
        let text = $("#searchInputId").val();
        let dropdownText = $("#dropdownTypeId").text();
        if(dropdownText.trim() == "Choose type") {
            errorNotify(null, "Filter type is required!");
            return;
        };
        const {tableId, regionId} = currentLeaderboard.tableData;
        showGlobalLoading();
        const resp = await filterDataInSubTable(dropdownText.trim(), {tableId, regionId, text, subTableID: Number(getQueryParams("subId"))});
        hideGlobalLoading();
        let data = resp ? resp.data : null;
        if(data) {
            tableDataRow = resp.data;
            await setColumn()
            await setTableData()
            await setEventTable()
            await setVisibleColFeature()
            await setSortFeature();
        }
        else {
            $("#tableData tbody").empty().append(`<div class="m-4" style="color:red; font-weight: bold">No data was found!</div>`);
        }
    })
}


/**
 * Init tableSortButton function in database table
 */
 function setSortFeature() {
    $(".tableSortButton").unbind("click").click(async e => {
        let sortKey = $(e.currentTarget).attr("sortKey");
        let sortOrd = sort[`${sortKey}`] || "asc";
        let sortIndex = null;
        let classToRemove = "";
        let classToAdd = "";
        if(sortKey == "UserId") {
            sortIndex = 3;
        } 
        else if(sortKey == "DatabaseId") {
            sortIndex = 1;
        }
        else if(sortKey == "Credential") {
            sortIndex = 0;
        }
        else if(sortKey == "TimeCreated") {
            sortIndex = 2;
        }
        else if(sortKey == "SubValue") {
            sortIndex = 5;
        }
        else {
            let listDescribeTables = tableDataRow.listDescribeTables;
            sortIndex = listDescribeTables.findIndex(i => i.ColumnName == sortKey);
            sortIndex = sortIndex >= 0 ? sortIndex + TABLE_OFFSET + 2 : sortIndex;
        }
        if(sortOrd == "desc") {
            tableDataRow.listUserId.sort((a, b) => {
                if(!a || !b) {
                    classToRemove = "tableSortDesc";
                    classToAdd = "tableSortAsc";
                    sort[`${sortKey}`] = "asc";
                    return 1;
                }
                if(!isNaN(Number(b[sortIndex]))) {
                    return Number(b[sortIndex]) - Number(a[sortIndex])
                }
                if(!isNaN(Date.parse(a[sortIndex])) && !isNaN(Date.parse(b[sortIndex]))) {
                    return Date.parse(a[sortIndex]) - Date.parse(b[sortIndex])
                }
                return  (a[sortIndex]).localeCompare(b[sortIndex]);
            });
            classToRemove = "tableSortDesc";
            classToAdd = "tableSortAsc";
            sort[`${sortKey}`] = "asc";
        }
        else {
            tableDataRow.listUserId.sort((a,b) => {
                if(!a || !b) {
                    classToAdd = "tableSortDesc";
                    classToRemove = "tableSortAsc";
                    sort[`${sortKey}`] = "desc";
                    return -1;
                }
                if(!isNaN(Number(b[sortIndex]))) {
                    return Number(a[sortIndex]) - Number(b[sortIndex])
                }
                if(!isNaN(Date.parse(a[sortIndex])) && !isNaN(Date.parse(b[sortIndex]))) {
                    return Date.parse(b[sortIndex]) - Date.parse(a[sortIndex])
                }
                return  (b[sortIndex]).localeCompare(a[sortIndex]);
            });
            classToAdd = "tableSortDesc";
            classToRemove = "tableSortAsc";
            sort[`${sortKey}`] = "desc";
        }
        if(tableDataRow && tableDataRow.listUserId.length) {
            let callback = () => {
                $(e.currentTarget).find(".tableSortTitle").addClass(classToAdd);
                $(e.currentTarget).find(".tableSortTitle").removeClass(classToRemove);
            }
            await setColumn(callback)
            await setTableData()
            await setEventTable()
            await setVisibleColFeature()
            await setSortFeature();
        }
    })
}


/**
 * Init step for show/hide columns database table
 */
 function setVisibleColFeature() {
    // At first, we must get all the visible columns in the locals' storage -> then hide the columns invisible in storage
    let columnsState = JSON.parse(localStorage.getItem(visibleColsName) || "{}");
    if(columnsState) {
        $(".menuDropDownItemChoose").each((i, element) => {
            let name = $(element).attr("data");
            if(columnsState[`${name}`] != undefined && columnsState[`${name}`] == false) {
                hideShowDatabaseTableCol(name, false);
                $(element).prop('checked', false);
            }
            else {
                $(element).prop('checked', true);
            }
        })
    }

    // Add event for function toggle visible table column
    $("#menuDropdownTb > .item").unbind("click").click(e => e.stopPropagation());
    $(".menuDropDownItemChoose").unbind("change").change(e => {
        let colName = $(e.currentTarget).attr("data");
        let isCheck = $(e.currentTarget).is(':checked'); 
        hideShowDatabaseTableCol(colName, isCheck);
    });
}

/**
 * Show/Hide column in databse table
 * @param {String} colName 
 * @param {Boolean} isCheck 
 */
function hideShowDatabaseTableCol(colName, isCheck) {
    $(`.${colName}`).each( (i, cell) => {
        $(cell).css("display", (isCheck) ? 'table-cell' : 'none');
    });
    // After columns state changed -> update into local storage column which is visible and invisible
    let columnsState = JSON.parse(localStorage.getItem(visibleColsName) || "{}");
    columnsState[`${colName}`] = isCheck;
    localStorage.setItem(visibleColsName, JSON.stringify(columnsState));
}

async function getLeaderboardFromKey(dbId, leaderboardId) {
    if(dbId != null && leaderboardId != null) {
        return getLeaderboardDataFromKey(dbId, leaderboardId);
    }
    return null;
}

function initModalAddRow() {
    $("#addMoreRow").click(() => {
        let onAddRow = async (closeModal) => {
            const formData = new FormData(document.getElementById("addRowForm"));
            const credential = formData.get("credentialRow");
            let password = formData.get("passwordRow"); 
            const databaseId = $("#databaseTypeId").val();
            if(databaseId == 1 || databaseId == 6) {
               password = null; 
            }
            const regionId = currentLeaderboard.tableData.regionId;
            const tableId = currentLeaderboard.tableData.tableId;
            let payload = {credential, password, databaseId, regionId, tableId};
            showGlobalLoading();
            const resp = await addRowToDB(payload);
            hideGlobalLoading();
            if(resp.requestStatus) {
                successNotify("Create new row successfully!");
                closeModal();
            }
            else {
                errorNotify(null, resp.message || "Error when add new row");
            }
        }
        let onClosed = (isUpdatedData = false) => {
            if(isUpdatedData) {
                getData()
                .then(() => setColumn())
                .then(() => setTableData())
                .then(() => setEventTable())
                .then(() => setVisibleColFeature())
                .then(() => setSortFeature())
                .then(() => setSubTableConfig())
            }
        }
        vModal("Create account for " + currentLeaderboard.tableData.TableName,
            modalAddRowBody,
            modalAddRowFooter,
            "vModalClose", 
            "addRowBtn", 
            onAddRow, onClosed);
    });
}

async function getData() {
    const {tableId, regionId} = currentLeaderboard.tableData;
    showGlobalLoading();
    const resp = await getSubTableData({tableId, regionId, subTableID: Number(getQueryParams("subId"))});
    hideGlobalLoading();
    if(resp.requestStatus) {
        if(resp.data.ColumnName) {
            currentLeaderboard.subTable.ColumnName = resp.data.ColumnName
        }
        if(resp.data.DataType) {
            currentLeaderboard.subTable.DataType = resp.data.DataType
        }
        if(resp.data.NumberRow) {
            currentLeaderboard.subTable.NumberRow = resp.data.NumberRow
        }
        tableDataRow = resp.data;
    }
    else {
        errorNotify(null, resp.message || "Error when get all rows"); 
    }
}

function getStartTableColumnData(row, i) {
    if(row == null) return `<tr>
            <td class="UserId tableAction" data='${JSON.stringify({RowIndex: i})}'>
                <div>
                    N/A
                    <i class="edit outline icon"></i>
                </div>
            </td>
            <td class="Credential">-</td>
            <td class="DatabaseId">-</td>
            <td class="TimeCreated">-</td>
            <td class="SubTableValue" style="color: red">-</td>
            <td class="Permission">-</td>
        </tr>`;
    return `
        <tr rowIndex="${row[0]}">
            <td class="UserId tableAction" data='${JSON.stringify({...row, RowIndex: row[0]})}'>
                <div>
                    ${row[4]} 
                    <i class="edit outline icon"></i>
                </div>
            </td>
            <td class="Credential">${row[1].length > 20 ? row[1].slice(0, 20) + "..." : row[1]}</td>
            <td class="DatabaseId">${getDatabaseIcon(row[2])}</td>
            <td class="TimeCreated">${formatDate(row[3])}</td>
            <td class="SubTableValue" style="color: red">${row[5]} </td>
            <td class="Permission">
            <div class="ui selection dropdown v-dropdown disabled">
              <input type="hidden" name="gender">
              <i class="dropdown icon"></i>
              <div class="default text">${row[6]}</div>
              <div class="menu">
                <div class="item" data-value="1">1</div>
                <div class="item" data-value="0">0</div>
              </div>
            </div>
            </td>
        </tr>`
}

function setTableData() {
    //Render data
    let content = '';
    let listDescribeTables = tableDataRow.listDescribeTables;
    let index = tableDataRow.listUserId ? tableDataRow.listUserId.length : 0;
    for (const item of tableDataRow.listUserId) {
        content += getStartTableColumnData(item, index).replace("</tr>", "");
        let temp = "";
        if(item && item.length) {
            for (let i = TABLE_OFFSET + 2; i < item.length; i++) {
                temp += buildRow(item[i], listDescribeTables[i - TABLE_OFFSET - 2]);
            }
        }
        else {
            for (let i = 0; i < listDescribeTables.length; i++) {
                temp += buildRow(null, null);
            }
        }
        content += temp + "</tr>";
        temp = "";
        index --;
    }
    $("#tableData tbody").empty().append(content);
    $('.v-dropdown').dropdown();
}

function setEventTable() {
    //Add event for action column
    $(".tableAction").unbind("click").click(e => {
        let dataJson = JSON.parse($(e.currentTarget).attr("data")) || {};
        let userId = null;
        if(dataJson[`4`]) {
            userId = dataJson[`4`];
        }
        const onCancel = () => {};
        const onClick = async (close) => {
            let form = $("#updateRowForm");
            let SubValue;
            let SubId;
            form.find("input").each((i,e) => {
                let columnName = $(e).attr("name");
                let inputValue = $(e).val();
                console.log(columnName, inputValue)
                if(columnName == "SubValue") {
                    SubValue = inputValue;
                }
                if(columnName == "SubId") {
                    SubId = inputValue;
                }
            });
            let data = JSON.parse($(e.currentTarget).attr("data")) || {};
            let updatedData = {
                "DataType": Number(currentLeaderboard.subTable.DataType),
                "RowIndex": Number(data.RowIndex) || 0,
                "UserId": Number(SubId),
                "regionId": Number(currentLeaderboard.tableData.regionId),
                "subTableID": Number(getQueryParams("subId")),
                "tableId": Number(currentLeaderboard.tableData.tableId),
                "value": parseDataToSuitableType(currentLeaderboard.subTable.TableType, SubValue)
            };

            showGlobalLoading();
            const resp = await updateLeaderBoard(updatedData);
            hideGlobalLoading();
            if(resp.requestStatus) {
                successNotify("Update row successfully!");
                getData()
                    .then(() => setColumn())
                    .then(() => setTableData())
                    .then(() => setEventTable())
                    .then(() => setVisibleColFeature())
                    .then(() => setSubTableConfig());
                close();
            }
            else {
                errorNotify(resp.message || "Error when update row");
            }
        };
        let data = JSON.parse($(e.currentTarget).attr("data") || "[]");
        data.userId = userId;
        vModal("Update row",
            modalAddTableRow(data),
            modalAddTableRowFooter(),
            "vModalClose",
            "modalActionEditRow",
            onClick, onCancel);
    })
}

function setColumn(callback = null) {
    try {
        let startColumns = `
            <th class="UserId">
                <div class="d-flex justify-content-center tableSortButton" sortKey="UserId">
                    <span class="tableSortTitle ${sort.UserId == "asc" ? " tableSortDesc" : " tableSortAsc"}">User</span>
                </div>
            </th>
            <th class="Credential">
                <div class="d-flex justify-content-center tableSortButton" sortKey="Credential">
                    <span class="tableSortTitle ${sort.Credential == "asc" ? " tableSortDesc" : " tableSortAsc"}">Credential</span>
                </div>
            </th>
            <th class="DatabaseId">
                <div class="tableSortButton" sortKey="DatabaseId">
                    <span class="tableSortTitle ${sort.DatabaseId == "asc" ? " tableSortDesc" : " tableSortAsc"}">Database</span>
                </div>
            </th>
            <th class="TimeCreated">
                <div class="d-flex justify-content-center tableSortButton" sortKey="TimeCreated">
                    <span class="tableSortTitle ${sort.TimeCreated == "asc" ? " tableSortDesc" : " tableSortAsc"}">Time Created</span>
                </div>
            </th>
           <th class="SubValue">
                <div class="d-flex justify-content-center tableSortButton" sortKey="SubValue">
                    <span id="subTableValue" style="color: red"
                    class="tableSortTitle ${sort.SubValue == "asc" ? " tableSortDesc" : " tableSortAsc"}">
                    ${currentLeaderboard.subTable.ColumnName || "Value"}
                    </span>
                </div>
            </th>
             <th class="Permission">
                <div class="d-flex justify-content-center">
                    <span>Account Status</span>
                </div>
            </th>
        `;
        let startVisibleCols = `
            <div class="item">
                <div class="ui checked checkbox">
                    <input data="Credential" type="checkbox" class="menuDropDownItemChoose" checked="">
                    <label style="margin-bottom: 0">${convertCamelCaseToNormal("Credential")}</label>
                </div>
            </div>
            <div class="item">
                <div class="ui checked checkbox">
                    <input data="DatabaseId" type="checkbox" class="menuDropDownItemChoose" checked="">
                    <label style="margin-bottom: 0">${convertCamelCaseToNormal("Database")}</label>
                </div>
            </div>
            <div class="item">
                <div class="ui checked checkbox">
                    <input data="TimeCreated" type="checkbox" class="menuDropDownItemChoose" checked="">
                    <label style="margin-bottom: 0">${convertCamelCaseToNormal("TimeCreated")}</label>
                </div>
            </div>
            <div class="item">
                <div class="ui checked checkbox">
                    <input data="Permission" type="checkbox" class="menuDropDownItemChoose" checked="">
                    <label style="margin-bottom: 0">${convertCamelCaseToNormal("Account Status")}</label>
                </div>
            </div>`;
        let restVisibleCols = '';
        let tableDescribe = tableDataRow.listDescribeTables;
        let restColumns = tableDescribe.map(i => {
            let columnName = i.ColumnName && i.ColumnName != "" ? i.ColumnName : "Unnamed";
            restVisibleCols += `
            <div class="item">
                <div class="ui checked checkbox">
                    <input data="${columnName}" type="checkbox" class="menuDropDownItemChoose" checked="">
                    <label style="margin-bottom: 0">${convertCamelCaseToNormal(columnName)}</label>
                </div>
            </div>
            `
            return `<th class="${columnName}">
                <div class="tableSortButton" sortKey="${columnName}">
                    <span class="tableSortTitle ${sort[`${columnName}`] == "asc" ? " tableSortDesc" : " tableSortAsc"}">${convertCamelCaseToNormal(columnName)}</span>
                </div>
            </th>`
        }).join("");
        $("#tableDataHeaderColumns").empty().html(startColumns + restColumns);
        $("#menuDropdownTb").empty().html(startVisibleCols + restVisibleCols);
    }
    catch(e) {
        console.log(e)
    }
}

//-----------------------------------------------------------------------//

const modalAddTableRow = (data) => {
    let htmlContent = `
        <div class="form-group">
            <label for="SubId">
                User Id
            </label>
            <input type="text" data-index="0" data-userId="0"
            value='${data.userId || ''}'
            name="SubId"
            class="form-control">
        </div>
        <div class="form-group">
            <label for="SubValue">
                Value
            </label>
           <input type="text" data-index="0" data-userId="0"
            name="SubValue"
            class="form-control">
        </div>`;
    return `<form id="updateRowForm">
        ${htmlContent}
    </form>`;
}

const modalAddTableRowFooter = () => {
   return `
   <div class="d-flex justify-content-end d-flex align-items-center">
        <div>
            <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Cancel</button>
            <button type="button" id="modalActionEditRow" class="btn btn-primary">Update</button>
        </div>
    </div>`;
}

const modalAddRowBody = `
    <form id="addRowForm">
        <div class="form-group">
            <label>Account Type</label>
            <select class="custom-select" id="databaseTypeId">
                <option selected value="0">Device</option>
                <option value="1">System Account</option>
                <option value="2">Facebook</option>
                <option value="3">Google</option>
                <option value="4">Ads ID</option>
                <option value="5">Apple</option>
                <option value="6">Email Code</option>
            </select>
        </div>
        <div class="form-group">
            <label for="inputAddress" id="inputAddressModal">Credential</label>
            <input type="text" name="credentialRow" class="form-control" id="credentialRow">
        </div>
        <div class="form-group" style="display: none" id="modalPwd">
            <label for="descriptionCreateTable">Password</label>
            <input type="text" name="passwordRow" class="form-control" id="passwordRow">
        </div> 
    </form>`;
const modalAddRowFooter = `
    <div class="d-flex justify-content-end d-flex align-items-center">
        <div>
            <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Cancel</button>
            <button type="button" id="addRowBtn" class="btn btn-primary">Add Row</button>
        </div>
    </div>
`;

const modalConfigLeaderboardFooter = `
    <div class="d-flex justify-content-end d-flex align-items-center">
        <div>
            <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Done</button>
        </div>
    </div>
`;

const getBodyConfigLeaderboard = () => {
    if(subTableConfig) {
        let content = Object.entries(subTableConfig).map(item => {
            if(item[0] == 'DataType') {
                return `
                    <tr>
                        <td>${item[0]}</td>
                        <td class="d-flex justify-content-between">
                           <select class="custom-select" id="databaseTypeId" name="dataType">
                                <option ${item[1] == '10' ? "selected" :""} value="10">Byte</option>
                                <option ${item[1] == '20' ? "selected" :""} value="20">Short</option>
                                <option ${item[1] == '40' ? "selected" :""} value="40">Integer</option>
                                <option ${item[1] == '41' ? "selected" :""} value="41">Float</option>
                                <option ${item[1] == '80' ? "selected" :""} value="80">Long</option>
                                <option ${item[1] == '81' ? "selected" :""} value="81">Double</option>
                            </select>
                            <div class="d-flex align-items-center">
                                <svg width="24" height="24" data-label="${item[0]}"
                                 class="v-icon-cursor clickableIcon icon saveConfigData" viewBox="0 0 24 24" fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" clip-rule="evenodd"
                                      d="M3 5C3 3.89543 3.89543 3 5 3H15.5858C16.1162 3 16.6249 3.21071 17 3.58579L19.4142 6C19.7893 6.37507 20 6.88378 20 7.41421V19C20 20.1046 19.1046 21 18 21H5C3.89543 21 3 20.1046 3 19V5ZM8 5H5V19H7V14C7 12.8954 7.89543 12 9 12H14C15.1046 12 16 12.8954 16 14V19H18V7.41421L15.5858 5H15V7.5C15 8.32843 14.3284 9 13.5 9H9.5C8.67157 9 8 8.32843 8 7.5V5ZM10 5V7H13V5H10ZM14 19H9V14H14V19Z"
                                      fill="#00B2FF"/>
                                </svg>
                            </div>
                        </td>
                    </tr>`;
            }
            if(item[0] == 'Version' || item[0] == 'TimeCreate') {
                return `
                <tr>
                    <td>${item[0]}</td>
                    <td>
                        <div class="d-flex justify-content-between">
                            ${item[1]}
                            <div>
                                <svg type="${item[0]}" value="${item[1]}"
                                    class="copyKey v-icon-cursor" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <g clip-path="url(#clip0_231_2346)">
                                    <path fill-rule="evenodd" clip-rule="evenodd" d="M9 2C7.89543 2 7 2.89543 7 4V6H9V4H20V15H18V17H20C21.1046 17 22 16.1046 22 15V4C22 2.89543 21.1046 2 20 2H9ZM4 7C2.89543 7 2 7.89543 2 9V20C2 21.1046 2.89543 22 4 22H15C16.1046 22 17 21.1046 17 20V9C17 7.89543 16.1046 7 15 7H4ZM4 9H15V20H4V9Z" fill="#569AFE"/>
                                    </g>
                                    <defs>
                                    <clipPath id="clip0_231_2346">
                                    <rect width="24" height="24" fill="white"/>
                                    </clipPath>
                                    </defs>
                                </svg>
                            </div>
                        </div>
                    </td>
                </tr>
                `;
            }
            return `<tr>
                <td>${item[0]}</td>
                <td>
                    <div class="ui icon input d-flex justify-content-between align-item-center">
                        <input type="text" placeholder="" value="${item[1]}">
                        <span class="d-flex align-items-center">
                            <svg width="24" height="24" data-label="${item[0]}"
                                 class="v-icon-cursor clickableIcon icon saveConfigData" viewBox="0 0 24 24" fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" clip-rule="evenodd"
                                      d="M3 5C3 3.89543 3.89543 3 5 3H15.5858C16.1162 3 16.6249 3.21071 17 3.58579L19.4142 6C19.7893 6.37507 20 6.88378 20 7.41421V19C20 20.1046 19.1046 21 18 21H5C3.89543 21 3 20.1046 3 19V5ZM8 5H5V19H7V14C7 12.8954 7.89543 12 9 12H14C15.1046 12 16 12.8954 16 14V19H18V7.41421L15.5858 5H15V7.5C15 8.32843 14.3284 9 13.5 9H9.5C8.67157 9 8 8.32843 8 7.5V5ZM10 5V7H13V5H10ZM14 19H9V14H14V19Z"
                                      fill="#00B2FF"/>
                            </svg>
                        </span>
                    </div>
                </td>
            </tr>`;
        }).join(" ");
        return `
            <table class="ui celled table">
                <thead>
                <tr><th>Attribute</th>
                    <th>Value</th>
                </tr></thead>
                <tbody>
                ${content}
                </tbody>
            </table>
            `;
    }
    else {
        return "";
    }
}
