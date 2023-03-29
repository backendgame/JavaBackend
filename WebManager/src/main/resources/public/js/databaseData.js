import { addRowToDB, filterDataInTable, getTableData, updateRowInTableData } from "./apis/table.js";
import {avatarIcon, dataType, isDataString, permissionIcon, statusIcon, TABLE_OFFSET} from "./CONFIG.js";
import { getQueryParams, getTableDataFromTableKey, useAuthentication, setEventForGeneralComponent, vModal, formatDate } from "./global_functions.js";
import {
    convertCamelCaseToNormal,
    errorNotify,
    getDatabaseIcon,
    getDataIcon,
    hideGlobalLoading, parseDataToSuitableType,
    redirectTo,
    showGlobalLoading,
    successNotify
} from "./utils.js";
let currentData;
let tableDataRow = {listDescribeTables: [], listUserId: []}
let sort = {}

$(document).ready(() => {
    useAuthentication(true)
    .then(async (auth) => {
        if(!auth) return;
        setEventForGeneralComponent();

        //check params is appear
        let dbId = getQueryParams("dbId");
        if(!dbId || dbId.trim() == "") {
            $(".app-main").html("<div class='d-flex align-items-center h3' style='color: red; font-weight: bold'>Nothing to show!</div>");
            return;
        }

        currentData = await getDbsKeyFromQuery(dbId); 
        //Set event
        $("#vConfigTable").click(() => {
            redirectTo(`/user/db-config?dbId=${currentData.tableKey}`)
        })
        //Get all table data
        getData()
            .then(() => setColumn())
            .then(() => setTableData())
            .then(() => setEventTable())
            .then(() => setVisibleColFeature())
            .then(() => setSortFeature());
        // Init modal create row
        initModalAddRow();
        //Init search input
        initSearchInput();
        initDropdownPlaceHolder();
    })
});

function buildRow(rowData, describe) {
    if(describe.Type == dataType.STATUS) {
        return `<td class="${describe ? describe.ColumnName : ""}">${getDataIcon(statusIcon)(rowData).icon}</td> \n`
    }
    if(describe.Type == dataType.PERMISSION) {
        return `<td class="${describe ? describe.ColumnName : ""}">${getDataIcon(permissionIcon)(rowData).icon}</td> \n`
    }
    if(describe.Type == dataType.AVARTAR) {
        return `<td class="${describe ? describe.ColumnName : ""}">${getDataIcon(avatarIcon)(rowData).icon}</td> \n`
    }
    return `<td class="${describe ? describe.ColumnName : ""}">${rowData}</td> \n`
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
        const {tableId, regionId} = currentData;
        showGlobalLoading();
        const resp = await filterDataInTable(dropdownText.trim(), {tableId, regionId, text});
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
        // else if(sortKey == "Permission") {
        //     sortIndex = 4;
        // }
        else {
            let listDescribeTables = tableDataRow.listDescribeTables;
            sortIndex = listDescribeTables.findIndex(i => i.ColumnName == sortKey);
            sortIndex = sortIndex >= 0 ? sortIndex + TABLE_OFFSET : sortIndex;
        }
        if(sortOrd == "desc") {
            tableDataRow.listUserId.sort((a, b) => {
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
    let columnsState = JSON.parse(localStorage.getItem("DatabaseTableCols") || "{}");
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
    // After columns state chanegd -> update into local storage column which is visible and invisible
    let columnsState = JSON.parse(localStorage.getItem("DatabaseTableCols") || "{}");
    columnsState[`${colName}`] = isCheck;
    localStorage.setItem("DatabaseTableCols", JSON.stringify(columnsState));
}

async function getDbsKeyFromQuery(dbId) {
    let respTable = null;
    if(dbId) {
        respTable = await getTableDataFromTableKey(dbId);
    }
    return respTable;
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
            const regionId = currentData.regionId;
            const tableId = currentData.tableId;
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
                .then(() => setSortFeature());
            }
        }
        vModal("Create account for " + currentData.TableName, 
            modalAddRowBody,
            modalAddRowFooter,
            "vModalClose", 
            "addRowBtn", 
            onAddRow, onClosed);
    });
}

async function getData() {
    const {tableId, regionId} = currentData;
    const length = 100;
    const userIdBegin = 1;
    showGlobalLoading();
    const resp = await getTableData({tableId, regionId, length, userIdBegin});
    hideGlobalLoading();
    if(resp.requestStatus) {
        tableDataRow = resp.data;
    }
    else {
        errorNotify(null, resp.message || "Error when get all rows"); 
    }
}

function getStartTableColumnData(row) {
    if(!row) return null;
    return `
        <tr>
            <td class="UserId tableAction" data='${JSON.stringify(row)}'>
                <div>
                    ${row[3]} 
                    <i class="edit outline icon"></i>
                </div>
            </td>
            <td class="Credential">${row[0].length > 20 ? row[0].slice(0, 20) + "..." : row[0]}</td>
            <td class="DatabaseId">${getDatabaseIcon(row[1])}</td>
            <td class="TimeCreated">${formatDate(row[2])}</td>
            <td class="Permission">
            <div class="ui selection dropdown v-dropdown">
              <input type="hidden" name="gender">
              <i class="dropdown icon"></i>
              <div class="default text">${row[4]}</div>
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
    for (const item of tableDataRow.listUserId) {
        content += getStartTableColumnData(item).replace("</tr>", "");
        let temp = "";
        for (let i = TABLE_OFFSET; i < item.length; i++) {
            temp += buildRow(item[i], listDescribeTables[i - TABLE_OFFSET]);
        }
        content += temp + "</tr>";
        temp = "";
    }
    $("#tableData tbody").empty().append(content);
    $('.v-dropdown').dropdown();
}

function setEventTable() {
    //Add event for action column
    $(".tableAction").unbind("click").click(e => {
        const onCancel = () => {};
        const onClick = async (close) => {
            let form = $("#updateRowForm");
            let listDescribeTables = tableDataRow.listDescribeTables;
            let listUpdates = [];
            form.find("input").each((i,e) => {
                let inputValue = $(e).val();
                let columnName = $(e).attr("name");
                let userId = $(e).attr("data-userid");
                let column = listDescribeTables.find(i => i.ColumnName == columnName);
                listUpdates.push({
                    ...column,
                    "ColumnIndex": i,
                    "Operator": 1, //Default
                    "Value": parseDataToSuitableType(column.Type, inputValue),
                    "userId": Number(userId)
                });
            });
            showGlobalLoading();
            const resp = await updateRowInTableData(currentData.tableId, currentData.regionId, listUpdates);
            hideGlobalLoading();
            if(resp.requestStatus) {
                successNotify("Update row successfully!");
                getData()
                    .then(() => setColumn())
                    .then(() => setTableData())
                    .then(() => setEventTable())
                    .then(() => setVisibleColFeature());
                close();
            }
            else {
                errorNotify(resp.message || "Error when update row");
            }
        };
        let data = JSON.parse($(e.currentTarget).attr("data") || "[]");
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
    let htmlContent = '';
    for (let index = TABLE_OFFSET; index < data.length; index++) {
        const element = data[index];
        let listDescribeTables = tableDataRow.listDescribeTables;
        htmlContent += `
            <div class="form-group">
                <label for="${listDescribeTables[index - TABLE_OFFSET].ColumnName}">
                    ${convertCamelCaseToNormal(listDescribeTables[index - TABLE_OFFSET].ColumnName)}
                </label>
                <input type="text" data-index=${index - TABLE_OFFSET} data-userId="${data[3]}"
                name="${listDescribeTables[index - TABLE_OFFSET].ColumnName}" value="${element}"
                class="form-control">
            </div>`;
    };
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

