import { changeTableNameOrDes, createTable, deleteDbTable, reloadTableData } from "./apis/table.js";
import {dataType, TABLES} from "./CONFIG.js";
import { adTableInfoLocal, deleteTableLocal, getAllTables, reRenderTableListLeftBar, useAuthentication, setEventForGeneralComponent, vModal, updateTableInfoLocal } from "./global_functions.js";
import {
    errorNotify,
    generateId,
    getDataTextByDataType,
    parseDataToSuitableType,
    setStateButton,
    successNotify
} from "./utils.js";
var dbTableData = [];

$(document).ready(() => {
    if(!useAuthentication(true)) {
        return;
    }
    // Set event for global page (header, footer)
    setEventForGeneralComponent();
    
    // First of all, setup all modal in this page
    setupModals();

    // Setup data for database's table
    setTableDatabases();

    // Then add event for every rows in database's table
    initDatabaseTableRow();

    // Init sort function in databse table
    initSortDatbaseFunction();

    // Add event for total current page
    initEventPage();

})

function getTableStructure() {
    let result = [];
    $("#tblBodyStructureCreate > tr").each((index,element) => {
        //If element has elementId -> data
        if($(element).attr("elementid")) {
            let jElement = $(element).find("td");
            let objs = {};
            jElement.each((index,element2) => {
                let jElement2 = $(element2);
                let type = null;
                let elementText = jElement2.text().trim().replace("\n","")
                let elementValue = jElement2.attr("eValue");
                if(jElement2.attr("elementid") == "Type") {
                    type = elementText;
                }
                if(jElement2.attr("elementid") == "DefaultValue") {
                    objs[`${jElement2.attr("elementid")}`] = parseDataToSuitableType(type, elementValue);
                } else if(jElement2.attr("elementid") == "Size") {
                    if(type == dataType.LIST || type == dataType.BYTE) {
                        objs[`Size`] = elementValue;
                    }
                } else {
                    if(jElement2.attr("elementid") && jElement2.attr("elementid") != undefined && typeof jElement2.attr("elementid") != undefined) {
                        objs[`${jElement2.attr("elementid")}`] = elementValue;
                    }
                }
            })
            result.push(objs);
        }
    });
    return result;
}

function setupModals() {
    // Modal when user click on "Add table"
    $("#open-modalWrapper").unbind('click').click(() => {
        // Callback function for handling
        let createTableAction = async (hideModal) => {
            const createTableForm = new FormData(document.getElementById("createTableForm"));
            let tableName = createTableForm.get("tableName");
            let description = createTableForm.get("description");
            let regionId = $("#regionIdCreateTable").val();
            let tokenLifeTimeMili = createTableForm.get("tokenLifeTimeMili");
            let describeTables = getTableStructure();
            setStateButton("create-db", "Creating...","loading");
            // TODO: ADD describeTables field
            const resp = await createTable(tableName, description, regionId, tokenLifeTimeMili, describeTables);
            setStateButton("create-db", "Create","no-loading");
            if(resp.requestStatus) {
                successNotify(null, "Create table successfully!");
                hideModal();
                await setTableDatabases();
                await reRenderTableListLeftBar();
            }
            else {
                errorNotify(null, resp.message || "Create table failed!");
            }
        }
        vModal("Create new table", 
            html_modalCreateTableBody, 
            html_modalCreateTableFooter,
            "vModalClose", 
            "create-db", 
            createTableAction);
        $("#addMoreTblStructure").unbind("click").click(e => {
            let objs = {};
            let formData = $(".tbl-structure-create");
            formData.each((i, element) => {
                let jElement = $(element);
                if(jElement.attr("name") == "DefaultValue") {
                    objs[`${jElement.attr("name")}`] = parseDataToSuitableType(objs['Type'], jElement.val())
                } else {
                    objs[`${jElement.attr("name")}`] = jElement.val();
                }
            });
            let id = generateId();
            let htmlContent = `
            <tr elementId="${id}">
              <td elementId="ColumnName" eValue="${objs.ColumnName}">
                ${objs.ColumnName || "Unknown"}
              </td>
              <td elementId="Type" eValue="${objs.Type}">
                ${getDataTextByDataType(objs.Type) || "Unknown"}
              </td>
               <td elementId="DefaultValue" eValue="${objs.DefaultValue}">
                ${objs.DefaultValue != null ? objs.DefaultValue : "Unknown"}
              </td>
               <td elementId="Size" eValue="${objs.Size}"> 
                ${objs.Size}
              </td>
              <td>
                 <svg class="v-icon-cursor modalDeleteRowStructure" elementId="${id}" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <g clip-path="url(#clip0_230_256)">
                    <path d="M14.2792 2C15.1401 2 15.9044 2.55086 16.1766 3.36754L16.7208 5H20C20.5523 5 21 5.44772 21 6C21 6.55227 20.5523 6.99998 20 7L19.9975 7.07125L19.1301 19.2137C19.018 20.7837 17.7117 22 16.1378 22H7.86224C6.28832 22 4.982 20.7837 4.86986 19.2137L4.00254 7.07125C4.00083 7.04735 3.99998 7.02359 3.99996 7C3.44769 6.99998 3 6.55227 3 6C3 5.44772 3.44772 5 4 5H7.27924L7.82339 3.36754C8.09562 2.55086 8.8599 2 9.72076 2H14.2792ZM17.9975 7H6.00255L6.86478 19.0712C6.90216 19.5946 7.3376 20 7.86224 20H16.1378C16.6624 20 17.0978 19.5946 17.1352 19.0712L17.9975 7ZM10 10C10.5128 10 10.9355 10.386 10.9933 10.8834L11 11V16C11 16.5523 10.5523 17 10 17C9.48716 17 9.06449 16.614 9.00673 16.1166L9 16V11C9 10.4477 9.44771 10 10 10ZM14 10C14.5523 10 15 10.4477 15 11V16C15 16.5523 14.5523 17 14 17C13.4477 17 13 16.5523 13 16V11C13 10.4477 13.4477 10 14 10ZM14.2792 4H9.72076L9.38743 5H14.6126L14.2792 4Z" fill="#A4A9B0"/>
                    </g>
                    <defs>
                    <clipPath id="clip0_230_256">
                    <rect width="24" height="24" fill="white"/>
                    </clipPath>
                    </defs>
                </svg>
              </td>
            </tr>
            `
            $("#tblBodyStructureCreate").append(htmlContent);
            $(".modalDeleteRowStructure").unbind("click").click(e => {
                let elementId = $(e.currentTarget).attr("elementid");
                $("#tblBodyStructureCreate > tr").each((index,element) => {
                    if($(element).attr("elementid") == elementId) {
                        $(element).remove();
                    }
                })
            })
        })
    });
    //--------------------------------------------------------//
}

/**
 * Setup modal when user click delete row in table database
 */
function setupModalDeleteDatabaseTableRow() {
    let deleteBtn = $(".v-db-delete");
    deleteBtn.click((e) => {
        let dataToDelete = JSON.parse($(e.currentTarget).attr("data") || "{}");
        let onDelDb = async (hideModal) => {
            const resp = await deleteDbTable(dataToDelete.regionId, dataToDelete.tableId);
            if(resp.requestStatus) {
                deleteTableLocal(dataToDelete.tableId, dataToDelete.regionId);
                initDatabaseTableRow();
                successNotify(null, "Delete table success");
                hideModal();
                await setTableDatabases();
                await reRenderTableListLeftBar();
            }
            else {
                errorNotify(null, resp.message || "Error when delete database");
            }
        }
        vModal("Confirm delete", html_modalDeleteTable(dataToDelete.TableName), 
        htnl_modalDeleteTableFooter(dataToDelete), "vModalClose", "del-db", onDelDb)
    });
}

/**
 * Init page's event
 */

function initEventPage() {
    // Init event when user click search table
    $("#searchTableDB").bind("input propertychange", function (event) {
        // If it's the propertychange event, make sure it's the value that changed.
        if (window.event && event.type == "propertychange" && event.propertyName != "value")
            return;
        // Clear any previously set timer before setting a fresh one
        window.clearTimeout($(this).data("timeout"));
        $(this).data("timeout", setTimeout(async function () {
            let text = event.target.value;
            let data = await getAllTables();
            let filtered = Object.entries(data).filter(item => item[1].TableName.toLowerCase().indexOf(text) > -1);
            let objs = {};
            filtered.map(i => {
                objs[`${i[0]}`] = i[1];
            });
            await setTableDatabases(objs);
        }, 500));
    }); 
}

/**
 *  Set data for table database 
 * @param {Object} data 
 */
async function setTableDatabases(data = null) {
    if(data == null) {
        let data2 = await getAllTables();
        dbTableData = data2;
        let htmlelemet = data2.map(item => html_getTableDBrow(item)).join(" ")
        $("#dbDataBody").empty().append(htmlelemet);
    }
    else {
        let data2 = Object.values(data);
        dbTableData = data2;
        let htmlelemet = data2.map(item => html_getTableDBrow(item)).join(" ")
        $("#dbDataBody").empty().append(htmlelemet); 
    }
    initDatabaseTableRow();
}

/**
 * Init event for table database
 */
function initDatabaseTableRow() {
    setupModalDeleteDatabaseTableRow();
    $(".db-inner-edit").unbind('click').click((e) => {
        const wrapper = $(e.currentTarget.parentElement);
        const nextSibling = $(e.currentTarget.parentElement.nextElementSibling);
        wrapper.css("display", "none");
        nextSibling.css("display", "flex")
    });
    let checkIcons = $(".confirm-icon");
    if(checkIcons) {
        checkIcons.click(async (e) => {
            let wrapper = $(e.currentTarget.parentElement.parentElement);
            let inputData = $(e.currentTarget.parentElement).find("input");
            let replaceType = inputData.attr("data-type");
            let itemData = JSON.parse(wrapper.attr("data") || "{}");
            if(replaceType == "table-name") {
                itemData.TableName = inputData.val();
            }
            if(replaceType == "table-des") {
                itemData.TableDescription = inputData.val();
            }
            //action here
            let resp = await changeTableNameOrDes(itemData.TableName, itemData.TableDescription, itemData.regionId, itemData.tableId);
            if(resp.requestStatus) {
                await setTableDatabases();
                await reRenderTableListLeftBar();
            }
            else {
                errorNotify(null, resp.message || "Error when update table info");
            }
            wrapper.children()[0].style.display = "block";
            wrapper.children()[1].style.display = "none";
        })
    }
}

/**
 * Init sortable function in database table
 */
function initSortDatbaseFunction() {
    $("#tableClm .sortable").unbind("click").click(e => {
        let column = e.currentTarget.parentElement;
        let sortOrd = $(column).attr("sort-ord");
        let sortkey = $(column).attr("data");
        if(sortOrd == "desc") {
            dbTableData = dbTableData.sort((a, b) => {
                if(!isNaN(Number(b[`${sortkey}`]))) {
                    return Number(b[`${sortkey}`]) - Number(a[`${sortkey}`])
                }
                return  (a[`${sortkey}`]).localeCompare(b[`${sortkey}`]);
            });
            $(e.currentTarget).find("i").addClass("down");
            $(e.currentTarget).find("i").removeClass("up");
            $(column).attr("sort-ord", "asc");
        }
        else {
            dbTableData.sort((a,b) => {
                if(!isNaN(Number(b[`${sortkey}`]))) {
                    return Number(a[`${sortkey}`]) - Number(b[`${sortkey}`])
                }
                return  (b[`${sortkey}`]).localeCompare(a[`${sortkey}`]);
            });
            $(e.currentTarget).find("i").removeClass("down");
            $(e.currentTarget).find("i").addClass("up"); 
            $(column).attr("sort-ord", "desc");
        }
        setTableDatabases(dbTableData);
    })
}


//--------------------------------- THIS ARTICLE IS USED TO GET HTML CONTENTS-------------------------------------//
function html_getTableDBrow(data) {
    return `
    <tr>
        <td>
            <div>
                ${data.tableId}
            </div>
        </td>
        <td class="db-name TableName" data='${JSON.stringify(data)}'>
            <div style="display: block">
                ${data.TableName}
                <svg class="v-tooltip db-inner-edit v-icon-cursor" data-content="Details ${data.TableName}" 
                    data='${JSON.stringify(data)}' width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M21.0304 2.96973C22.4279 4.36719 22.4279 6.63293 21.0304 8.03039L9.0621 19.9987C8.78522 20.2756 8.44089 20.4754 8.06312 20.5784L2.94743 21.9736C2.38756 22.1263 1.87383 21.6126 2.02652 21.0527L3.42171 15.937C3.52474 15.5593 3.72456 15.2149 4.00144 14.9381L15.9698 2.96973C17.3672 1.57227 19.633 1.57227 21.0304 2.96973ZM15.0003 6.06063L5.0621 15.9987C4.96981 16.091 4.9032 16.2058 4.86886 16.3317L3.81901 20.1811L7.66845 19.1313C7.79437 19.097 7.90915 19.0303 8.00144 18.9381L17.9393 8.99963L15.0003 6.06063ZM17.0304 4.03039L16.0603 4.99963L18.9993 7.93963L19.9698 6.96973C20.7814 6.15805 20.7814 4.84206 19.9698 4.03039C19.1581 3.21871 17.8421 3.21871 17.0304 4.03039Z" fill="#3BC862"/>
                </svg>
            </div>
            <div style="display: none;" class="ui icon input">
                <input type="text" class="db-inner-edit-input" data-type="table-name" value="${data.TableName}" placeholder="Enter table name...">
                <i class="check green icon selectable confirm-icon" style="cursor: pointer; pointer-events: auto"></i>
            </div>
        </td>
        <td class="db-des TableDescription" data='${JSON.stringify(data)}'>
            <div style="display: block">
                ${data.TableDescription}
                <svg class="v-tooltip v-icon-cursor db-inner-edit" data-content="Details ${data.TableName}" 
                    data='${JSON.stringify(data)}' width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M21.0304 2.96973C22.4279 4.36719 22.4279 6.63293 21.0304 8.03039L9.0621 19.9987C8.78522 20.2756 8.44089 20.4754 8.06312 20.5784L2.94743 21.9736C2.38756 22.1263 1.87383 21.6126 2.02652 21.0527L3.42171 15.937C3.52474 15.5593 3.72456 15.2149 4.00144 14.9381L15.9698 2.96973C17.3672 1.57227 19.633 1.57227 21.0304 2.96973ZM15.0003 6.06063L5.0621 15.9987C4.96981 16.091 4.9032 16.2058 4.86886 16.3317L3.81901 20.1811L7.66845 19.1313C7.79437 19.097 7.90915 19.0303 8.00144 18.9381L17.9393 8.99963L15.0003 6.06063ZM17.0304 4.03039L16.0603 4.99963L18.9993 7.93963L19.9698 6.96973C20.7814 6.15805 20.7814 4.84206 19.9698 4.03039C19.1581 3.21871 17.8421 3.21871 17.0304 4.03039Z" fill="#3BC862"/>
                </svg>
            </div>
            <div style="display: none" class="ui icon input">
                <input data-type="table-des" class="db-inner-edit-input" type="text" value="${data.TableDescription}" placeholder="Enter table description...">
                <i class="check green icon selectable confirm-icon" style="cursor: pointer; pointer-events: auto"></i>
            </div>
        </td>
        <td>
            <a href="/user/db-config?dbId=${"R"+data.regionId+"T"+data.tableId}">
                <i  data-content="Details ${data.TableName}" data='${JSON.stringify(data)}'
                class="mx-1 cog alternate icon large v-tooltip v-tooltip v-icon-cursor"></i>
            </a>
            <a href="/user/data?dbId=${"R"+data.regionId+"T"+data.tableId}">
                <i data-content="Datasets ${data.TableName}"
                class="mx-1 database alternate icon large v-tooltip v-icon-cursor"></i>
            </a>
            <i class="red trash icon large v-db-delete v-tooltip v-icon-cursor" data-content="Delete ${data.TableName}" data='${JSON.stringify(data)}'></i>
        </td>
    </tr>`
}

const html_modalCreateTableBody = `
    <form id="createTableForm">
        <div class="row">
            <div class="form-group col">
                <label for="inputAddress">Table name</label>
                <input type="text" name="tableName" class="form-control" id="tableName">
            </div>
            <div class="form-group col">
                <label for="descriptionCreateTable">Description</label>
                <input type="text" name="description" class="form-control" id="descriptionCreateTable">
            </div> 
        </div>
        <div class="row">
            <div class="form-group col">
                <label for="tokenLifeTimeMiliCreateTable">Token Life Time 
                    <span style="color: #919191">(optional)
                    </span>
                </label>
                <input type="text" name="tokenLifeTimeMili" placeholder="Time in hour" class="form-control" id="tokenLifeTimeMili">
            </div>
            <div class="form-group col">
                <label>Region</label>
                <select class="custom-select" id="regionIdCreateTable">
                    <option selected>Select region</option>
                    <option value="1">Việt Nam</option>
                </select>
            </div>
        </div>
        <div class="form-group">
         <label>Table Architecture</label>
         <table style="margin-top: 0px" class="ui very table">
          <thead>
            <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Default value</th>
                <th>Size</th>
                <th>Action</th>
            </tr>
          </thead>
          <tbody id="tblBodyStructureCreate">
            <tr>
              <td>
                <input type="text" name="ColumnName" class="form-control tbl-structure-create">
              </td>
               <td>
                  <select name="Type" class="custom-select modalTableSelect tbl-structure-create">
                    <option value="1">Boolean</option>
                    <option value="10">Byte</option>
                    <option value="11">Status</option>
                    <option value="12">Permission</option>
                    <option value="13">Avatar</option>
                    <option value="20">Short</option>
                    <option value="40">Integer</option>
                    <option value="41">Float</option>
                    <option value="80">Long</option>
                    <option value="81">Double</option>
                    <option value="82">String</option>
                    <option value="83">Time mili</option>
                    <option value="100">Binary</option>
                    <option value="101">List</option>
                </select>
               </td>
              <td>
                <input type="text" name="DefaultValue" class="form-control tbl-structure-create">
              </td>
               <td>
                <input type="text" placeholder="For binary or list.." name="Size" class="form-control tbl-structure-create">
              </td>
              <td>   
                <i class="plus green v-icon-cursor large circle icon" id="addMoreTblStructure"></i> 
              </td>
            </tr>
          </tbody>
        </table>
        </div>
    </form>
`

const html_modalCreateTableFooter = `
    <div class="d-flex justify-content-end">
        <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Cancel</button>
        <button type="button" id="create-db" class="btn btn-success">Create</button>
    </div>
`
const htnl_modalDeleteTableFooter = (data) => {
    return ` <div class="d-flex justify-content-end">
        <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Cancel</button>
        <button type="button" id="del-db" data='${JSON.stringify(data)}' class="btn btn-danger">Delete</button>
    </div>
    `
}
const html_modalDeleteTable = (tableName) => {
    return `
    <div>
        Are you sure to delete database ${tableName}?
    </div>`
}