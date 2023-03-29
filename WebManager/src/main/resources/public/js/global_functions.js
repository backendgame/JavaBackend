import { reloadTableData } from './apis/table.js';
import { getUserData } from './apis/user.js';
import { TABLES } from './CONFIG.js';
import { onLogout } from './login.js';
import {
    errorNotify,
    getDbsKeyFromQuery,
    hideGlobalLoading,
    redirectTo,
    showGlobalLoading,
    successNotify
} from './utils.js';
import {createLeaderboard} from "./apis/subTable.js";

$(document).ready(() => {
    reRenderTableListLeftBar()
        .then(() => {
            setActiveLeftbar();
        })
        .then(() => setEventForButtonCreateTile())
})

/**
 * This function show up the modal
 * 
 * @param {String} header The header of modal
 * @param {String} body The html code of body
 * @param {String} footer The html code of modal footer
 * @param {String} closeIconClass Close icon class
 * @param {String} actionIconId Id of action button
 * @param {Function} onActionClick Lamda function when user click on action button
 */

function vModal(header, body, footer, closeIconClass, actionIconId, onActionClick, onClose = null) {
    const modalWrapper = $(".modal");
    $(".modal-header-content").text(header);
    $(".modalWrapper-body").html(body);
    $(".modalWrapper-footer").html(footer);
    modalWrapper.css("display", "flex");
    const closeModalBtn = $("." + closeIconClass);
    closeModalBtn.each(function(index, value) {
        value.addEventListener("click", () => { modalWrapper.css("display", "none");  onClose ? onClose() : null;})
    });
    $(document).keyup(function(e) {
        if (e.key === "Escape") {      
            modalWrapper.css("display", "none");
            onClose ? onClose() : null;
        }
    });
    if(actionIconId) {
        $("#" + actionIconId).unbind("click");
        $("#" + actionIconId).click(async () => {
            onActionClick(() => {
                modalWrapper.css("display", "none");
                onClose ? onClose(true) : null;
            });
        })
    }
}

/**
 * This function with the purpose to rerender list table in left bar
 * If no database was fetch -> render no database
 */
async function reRenderTableListLeftBar() {
    let allDBs = $(".v-card-ul");
    let dbs = await getAllTables();
    if(Object.entries(dbs).length > 0) {
        dbs = Object.values(dbs).map(item =>
            ` <li class="v-icon-cursor d-flex justify-content-between">
                <a data='${JSON.stringify(item)}' class=" v-icon-cursor"
                href="/user/data?dbId=${"R"+item.regionId+"T"+item.tableId}">
                ${item.TableName}
                </a>
                <div>
                    <a href="/user/db-config?dbId=${"R"+item.regionId+"T"+item.tableId}">
                        <i data-content="Details ${item.TableName}" data='${JSON.stringify(item)}' class="mx-1 cog alternate icon v-tooltip v-icon-cursor"></i>
                    </a>
                    <span>
                        <i data='${JSON.stringify(item)}' class="plus mx-1 v-icon-cursor icon v-create-tiles"></i>
                    </span>
                </div>
            </li>
            <div class="v-leftbar-tile-table">
                ${item.listSubTable ? Object.entries(item.listSubTable).map(ii => {
                    return `<a data='${JSON.stringify({...ii[1],id: ii[0] })}' data-id='${ii[0]}'
                            href='/user/leaderboard?dbId=${"R"+item.regionId+"T"+item.tableId}&subId=${ii[0]}'>
                            <span style="font-weight: bold">Leaderboard: </span> ${ii[1].TableName}
                        </a>`
            }).join("") : ""}
            </div>
            `);
        allDBs.empty().append(dbs);
    }
    else {
        allDBs.empty().append(`<div class="py-2">No database...</div>`);
    }
}

async function getAllTables() {
    let dbss = await reloadTableData();
    if(!dbss.data) return [];
    return Object.entries(dbss.data.Tables).map(item => {
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
        return {...item[1], ...getRegionAndIdFromTableKey(item[0]), tableKey: item[0]};
    });
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear(),
        hour = d.getHours(),
        minutes = d.getMinutes();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('/') + " " + hour + ":" + minutes;
}

/**
 * Return database info from database key
 * @param {String} tableKey 
 * @returns 
 */

async function getTableDataFromTableKey(tableKey) {
    if(!tableKey) return null;
    let dbs = await getAllTables();
    let find = dbs.find(item => item.tableKey == tableKey);
    return find || null;
}

/**
 * Return subtable info from database key
 * @param {String} tableKey
 * @returns
 */

async function getLeaderboardDataFromKey(tableKey, subTableId) {
    if(tableKey == null) return null;
    let dbs = await getAllTables();
    let find = dbs.find(item => item.tableKey == tableKey);
    if(find.listSubTable && find.listSubTable[`${subTableId}`]) {
        console.log({
            subTable: find.listSubTable[`${subTableId}`],
            tableData: find
        })
        return {
            subTable: find.listSubTable[`${subTableId}`],
            tableData: find
        }
    }
    return null;
}

/**
 *  
 * Return current url's parameter
 * 
 * @param {String || Number} key 
 * @returns 
 */

function getQueryParams(key) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(key);
}

/**
 * Return the user's state is logged or not
 * @param {Boolean} isRedirect 
 * @returns
 */
async function useAuthentication(isRedirect = true) {
    let authResp = await getUserData();
    let btnLogin = $(".header-login-btn");
    let btnUser = $(".header-account-btn");
    if(!authResp) {
        hideGlobalLoading();
        if(isRedirect) {
            // redirectTo("/forbidden");
        }
        btnLogin.css("display", "flex");
        btnUser.css("display", "none");
        return false;
    }
    hideGlobalLoading();
    btnLogin.css("display", "none");
    return true;
}

/**
 * UI's function to make a toggle database in leftbar
 */
function toggleTreeMenu() {
    $("#DatabasesTree .tree").toggle(200);
    let vToggleMenu = $("#DatabasesTree #vToggleMenu");
    if(vToggleMenu) {
        let transform = vToggleMenu.attr("style");
        if(transform == null || !transform.includes("180")) {
            vToggleMenu.attr("style", "transform: rotate(180deg)");
        }
        else {
            vToggleMenu.attr("style", "transform: rotate(0deg)");
        }
    }
}

/**
 * The UI's function trigger the leftbar to make it close or open
 * After that resize the table inside body
 */
function toggleLeftIcon() {
    let appWrapper = $(".app-wrapper");
    let checkContain = appWrapper.hasClass("sidebar-closed");
    appWrapper.toggleClass("sidebar-closed");
    if(checkContain) {
        $("#BackIcon").css("transform", "rotate(0deg)");
    }
    else {
        $("#BackIcon").css("transform", "rotate(180deg)");
    }
    resizeTable();
}

/**
 * This function resize table after leftbar's state changed
 */
function resizeTable() {
    let appWrapper = $(".app-wrapper");
    let checkContain = appWrapper.hasClass("sidebar-closed"); 
    if(checkContain) {
        $(".data-table").css("width", `calc(100vw - calc(var(--app-container-p-lr) * 2))`)
    }
    else {
        $(".data-table").css("width", `calc(100vw - var(--sidebar-width) - calc(var(--app-container-p-lr) * 2))`)
    } 
}

/**
 * Return tableId and regionId from tableKey
 * @param {String} tablekey -> Table key, example: R1T15
 */
function getRegionAndIdFromTableKey(tablekey) {
    let regionId = tablekey.split("T")[0].slice(1, tablekey.split("T")[0].length);
    let tableId = tablekey.split("T")[1];
    return {
        regionId, tableId
    }
}

/**
 * Set the currect active tab in leftbar base on current url
 * @returns void
 */
function setActiveLeftbar() {
    let url = window.location.href;
    $(".cards-wrapper .card").map((i, element) => {
        $(element).removeClass("card-active");
    });

    if(url.includes("data") || url.includes("leaderboard") || url.includes("db-config")) {
        $("#DatabasesTreeBtn").addClass("card-active");
        let currentTableKey = getQueryParams("dbId");
        if(currentTableKey) {
            $(".v-card-ul > li").each((i, value) => {
                let data = JSON.parse($(value).find("a").attr("data"));
                if(data.tableKey == currentTableKey) {
                    $(value).css("background-color", "#ebf7ff");
                    $(value).css("border-radius", "0px 45px 45px 0px");
                    $(value.nextElementSibling).find("a").css("background-color", "#ebf7ff");
                    $(value.nextElementSibling).find("a").css("border-radius", "0px 45px 45px 0px");
                }
            })
        }
        return;
    }

    if(url.includes("tiles")) {
        $("#TilesPage").addClass("card-active");
        return;
    }

    $("#Home").addClass("card-active");
}

//Those function will be opitimized later
function adTableInfoLocal(tableId, regionId, tableData) {
    let dbs = JSON.parse(localStorage.getItem(TABLES) || "{}");
    let tableKey = "R"+regionId+"T"+tableId;
    dbs = {...dbs, [`${tableKey}`]: tableData};
    localStorage.setItem(TABLES, JSON.stringify(dbs));
}

function updateTableInfoLocal(tableId, regionId, key, value) {
    let dbs = JSON.parse(localStorage.getItem(TABLES) || "{}");
    let tableKey = "R"+regionId+"T"+tableId;
    dbs[`${tableKey}`] = {...dbs[`${tableKey}`], [`${key}`]: value};
    localStorage.setItem(TABLES, JSON.stringify(dbs));
}

function deleteTableLocal(tableId, regionId) {
    let dbs = JSON.parse(localStorage.getItem(TABLES) || "{}");
    let tableKey = "R"+regionId+"T"+tableId;
    delete dbs[`${tableKey}`];
    localStorage.setItem(TABLES, JSON.stringify(dbs));
}

/**
 * All this those function will be set for leftbar
 */
function setEventForLeftBar() {
    //Home tab
    $("#Home").click(() => {
        redirectTo("/user");
    })
    //Tiles page
    $("#TilesPage").click(() => {
        redirectTo("/tiles")
    })
}

function setEventForButtonCreateTile() {
    $(".v-create-tiles").unbind("click").click(e => {
        const onClick = async (closeDialog) => {
           let f = new FormData(document.getElementById("addRowTiles"));
            let NumberTop = f.get("NumberRow");
            let DataType = f.get("dataType");
            let ColumnName = f.get("name");
            let Description = f.get("Description")
            let SubName = f.get("SubName")
            let currentDb = JSON.parse($(e.currentTarget).attr("data") || "{}");
            let payload = {
                ColumnName,
                DataType: Number(DataType),
                SubName,
                Description,
                NumberTop: Number(NumberTop),
                regionId: Number(currentDb.regionId) || null,
                tableId: Number(currentDb.tableId) || null,
            };
            showGlobalLoading();
            const resp = await createLeaderboard(payload);
            hideGlobalLoading();
            if(resp.requestStatus) {
                successNotify("Create new leaderboard success!");
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
            else {
                errorNotify(null, resp.message || "Unknown!")
            }
        };
        const onCancel = () => {

        };
        vModal("Create Sub Table",
        bodyModalCreateTile(), 
        footerModalCreateTile(),
        "vModalClose",
        "create-tile",
        onClick, onCancel); 
    })
}

/**
 * The most important function
 * Set event and initital display when user come into a page or reload a page
 * Use for all page
 */

function setEventForGeneralComponent() {
    resizeTable();
    $(".header-login-btn").on("click", () => { redirectTo("/login") });
    $("#accountLink").on("click", () => { redirectTo("/user") });
    $("#topUp").on("click", () => { redirectTo("/top-up") });
    $("#logoWebsite").on("click", () => { redirectTo("/") });
    $("#DatabasesTreeBtn").click(toggleTreeMenu);
    $("#BackIcon").click(toggleLeftIcon);
    window.onscroll = function() {
        if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
            $(".app-header").css("padding", "10px 10px")
        } else {
            $(".app-header").css("padding", "22px 10px")
        }
    };
    setEventForLeftBar();
    setEventForButtonCreateTile();
    $("#logOut").on("click", onLogout);
    $('.v-dropdown').dropdown({useLabels: false})
}

export {
    useAuthentication,
    setEventForGeneralComponent,
    getAllTables,
    reRenderTableListLeftBar,
    updateTableInfoLocal,
    deleteTableLocal,
    getRegionAndIdFromTableKey,
    getTableDataFromTableKey,
    getLeaderboardDataFromKey,
    getQueryParams,
    vModal,
    adTableInfoLocal,formatDate,
}

const bodyModalCreateTile = () => {
    return  `<form id="addRowTiles">
        
        <div class="row">
            <div class="form-group col-6">
                <label>Type</label>
                <select class="custom-select" id="databaseTypeId" name="createTileOpts">
                    <option selected value="0">Leaderboard (Auto)</option>
                </select>
            </div>
        <div class="form-group">
            <label for="name">Sub name</label>
            <input type="text" name="SubName" class="form-control">
        </div>
        </div>
         <div class="form-group">
            <label for="name">Description</label>
            <input type="text" name="Description" class="form-control">
        </div>
         <div class="form-group">
            <label for="name">Column Name</label>
            <input type="text" name="name" class="form-control">
        </div>
        <div class="row">
            <div class="form-group col-6">
                <label for="NumberRow">Number Top</label>
                <input type="text" name="NumberRow" class="form-control">
            </div>
            <div class="form-group col-6">
                <label>Data Type</label>
                <select class="custom-select" id="databaseTypeId" name="dataType">
                    <option value="10">Byte</option>
                    <option value="20">Short</option>
                    <option value="40">Integer</option>
                    <option value="41">Float</option>
                    <option value="80">Long</option>
                    <option value="81">Double</option>
                </select>
            </div>
        </div>
    </form>`;
}

const footerModalCreateTile = () => {
    return ` <div class="d-flex justify-content-end">
        <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Cancel</button>
        <button type="button" id="create-tile" class="btn btn-primary">Create</button>
    </div>
    `;
}
