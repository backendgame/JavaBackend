import { changeTableNameOrDes, configAccessToken, configReadToken, configRowStructure, configWriteToken, deleteDbTable, getTableInfo, updateLoginRules } from "./apis/table.js";
import { statusIcon } from "./CONFIG.js";
import { getQueryParams, getTableDataFromTableKey, useAuthentication, setEventForGeneralComponent, vModal, formatDate } from "./global_functions.js";
import {
    convertCamelCaseToNormal,
    copyToClipboard,
    downloadText,
    errorNotify,
    getByteFromDataType,
    getDataIcon,
    getDataTextByDataType,
    getDbsKeyFromQuery,
    getUserCounts,
    hideGlobalLoading,
    hideTokenCode,
    redirectTo,
    showGlobalLoading,
    successNotify
} from "./utils.js";
let currentDb;
let tableInfoData;

$(document).ready(() => {
    useAuthentication(true)
    .then(async data => {
        if(!data) return;
        setEventForGeneralComponent();
        let dbId = getQueryParams("dbId");
        if(!dbId || dbId.trim() == "" || dbId == "undefined") {
           $(".app-main").html("<div class='d-flex align-items-center h3' style='color: red; font-weight: bold'>Nothing to show!</div>");
           return;
        }
        currentDb = await getDbsKeyFromQuery(dbId);
        //Set title
        $("#dbName").text(`${getQueryParams("dbId") || ""}`);
        $("#vDatasetTable").click(() => {
            redirectTo(`/user/data?dbId=${currentDb.tableKey}`)
        });
        await getTableConfig()
            .then(() => setContentForTableConfig())
            .then(() => setEvChangeTableName())
            .then(() => setReloadAndCopyKey())
            .then(() => setTableKeyEvent())
            .then(() => setEventForTable());
        addUiEvent();
        $('.v-dropdown').dropdown();
        $('.v-tooltip').popup({
            on    : 'hover'
        });
    })
});

function setContentForTableConfig() {
    let keys = [
        "Permission",
        "AccessToken",
        "ReadToken",
        "WriteToken",
        "TokenLifeTime",
        "TotalUser"
    ]
    let tableDatabaseInfo = `<tr data-key="TableName">
            <td class="d-flex align-items-center">Database's Name</td>
            <td>
                <div class="d-flex justify-content-between">
                    ${currentDb.TableName}
                    <svg id="tblChangeTableName" class="v-icon-cursor"
                        width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M21.0304 2.96967C22.4279 4.36713 22.4279 6.63286 21.0304 8.03033L9.0621 19.9987C8.78522 20.2755 8.44089 20.4754 8.06312 20.5784L2.94743 21.9736C2.38756 22.1263 1.87383 21.6125 2.02652 21.0527L3.42171 15.937C3.52474 15.5592 3.72456 15.2149 4.00144 14.938L15.9698 2.96967C17.3672 1.5722 19.633 1.5722 21.0304 2.96967ZM15.0003 6.06057L5.0621 15.9987C4.96981 16.0909 4.9032 16.2057 4.86886 16.3316L3.81901 20.1811L7.66845 19.1312C7.79437 19.0969 7.90915 19.0303 8.00144 18.938L17.9393 8.99957L15.0003 6.06057ZM17.0304 4.03033L16.0603 4.99957L18.9993 7.93957L19.9698 6.96967C20.7814 6.15799 20.7814 4.842 19.9698 4.03033C19.1581 3.21865 17.8421 3.21865 17.0304 4.03033Z" fill="#3BC862"/>
                    </svg>
                </div>
                <div style="display: none" class="ui icon input">
                    <input type="text" placeholder="">
                    <span>
                        <svg id="saveTblName" width="24" height="24" class="v-icon-cursor clickableIcon icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="M3 5C3 3.89543 3.89543 3 5 3H15.5858C16.1162 3 16.6249 3.21071 17 3.58579L19.4142 6C19.7893 6.37507 20 6.88378 20 7.41421V19C20 20.1046 19.1046 21 18 21H5C3.89543 21 3 20.1046 3 19V5ZM8 5H5V19H7V14C7 12.8954 7.89543 12 9 12H14C15.1046 12 16 12.8954 16 14V19H18V7.41421L15.5858 5H15V7.5C15 8.32843 14.3284 9 13.5 9H9.5C8.67157 9 8 8.32843 8 7.5V5ZM10 5V7H13V5H10ZM14 19H9V14H14V19Z" fill="#00B2FF"/>
                        </svg>
                        <svg class="v-icon-cursor" id="cancelChangeName" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g clip-path="url(#clip0_232_2352)">
                            <path d="M12 2C17.5228 2 22 6.47715 22 12C22 17.5228 17.5228 22 12 22C6.47715 22 2 17.5228 2 12C2 6.47715 6.47715 2 12 2ZM12 4C7.58172 4 4 7.58172 4 12C4 16.4183 7.58172 20 12 20C16.4183 20 20 16.4183 20 12C20 7.58172 16.4183 4 12 4ZM9.87874 8.46443L12 10.5857L14.1213 8.46441C14.5118 8.07388 15.145 8.07388 15.5355 8.46441C15.926 8.85493 15.926 9.4881 15.5355 9.87862L13.4142 11.9999L15.5356 14.1213C15.9261 14.5118 15.9261 15.145 15.5356 15.5355C15.1451 15.926 14.5119 15.926 14.1214 15.5355L12 13.4141L9.87864 15.5355C9.48812 15.926 8.85496 15.926 8.46443 15.5355C8.07391 15.145 8.07391 14.5118 8.46443 14.1213L10.5858 11.9999L8.46452 9.87864C8.074 9.48812 8.074 8.85496 8.46452 8.46443C8.85505 8.07391 9.48821 8.07391 9.87874 8.46443Z" fill="#949494"/>
                            </g>
                            <defs>
                            <clipPath id="clip0_232_2352">
                            <rect width="24" height="24" fill="white"/>
                            </clipPath>
                            </defs>
                        </svg>

                    </span>
                </div>
            </td>
        </tr>`
    tableDatabaseInfo += keys.map(i => {
        if(i == "AccessToken" || i == "ReadToken" || i == "WriteToken") {
            return `
               <tr data-key="${i}">
                   <td class="d-flex align-items-center">${convertCamelCaseToNormal(i)}</td>
                   <td>
                    <div class="d-flex justify-content-between" real-value="${getRowContent(tableInfoData[`${i}`])}">
                        <span class="row-${i}">${hideTokenCode(tableInfoData[`${i}`])}</span>
                        <div>
                            <svg class="tblChangeConfig v-icon-cursor"
                                width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M21.0304 2.96967C22.4279 4.36713 22.4279 6.63286 21.0304 8.03033L9.0621 19.9987C8.78522 20.2755 8.44089 20.4754 8.06312 20.5784L2.94743 21.9736C2.38756 22.1263 1.87383 21.6125 2.02652 21.0527L3.42171 15.937C3.52474 15.5592 3.72456 15.2149 4.00144 14.938L15.9698 2.96967C17.3672 1.5722 19.633 1.5722 21.0304 2.96967ZM15.0003 6.06057L5.0621 15.9987C4.96981 16.0909 4.9032 16.2057 4.86886 16.3316L3.81901 20.1811L7.66845 19.1312C7.79437 19.0969 7.90915 19.0303 8.00144 18.938L17.9393 8.99957L15.0003 6.06057ZM17.0304 4.03033L16.0603 4.99957L18.9993 7.93957L19.9698 6.96967C20.7814 6.15799 20.7814 4.842 19.9698 4.03033C19.1581 3.21865 17.8421 3.21865 17.0304 4.03033Z" fill="#3BC862"/>
                            </svg>
                            <svg type="${i}" class="reloadKey v-icon-cursor" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g clip-path="url(#clip0_231_2340)">
                                <path d="M2.00003 12.0809C1.99378 11.2176 2.91031 10.7236 3.61774 11.1052L3.71281 11.1627L6.39066 12.967C7.36336 13.6224 6.76835 15.1101 5.65702 14.9737L5.54026 14.9537L4.4769 14.7204C5.33757 17.0968 7.30723 19.0268 9.93152 19.73C13.7626 20.7565 17.6914 18.8202 19.281 15.3252C19.5097 14.8225 20.1026 14.6003 20.6053 14.8289C21.1081 15.0576 21.3302 15.6505 21.1016 16.1532C19.1138 20.5236 14.2049 22.9456 9.41388 21.6619C5.41005 20.589 2.63004 17.2334 2.09423 13.3742C2.03513 12.9485 2.00329 12.5166 2.00003 12.0809ZM2.90283 7.8519C4.89059 3.48157 9.79956 1.05954 14.5905 2.34327C18.5944 3.4161 21.3744 6.7717 21.9102 10.6309C21.9693 11.0566 22.0011 11.4885 22.0043 11.9242C22.0106 12.7875 21.0941 13.2815 20.3866 12.8999L20.2916 12.8424L17.6136 11.0381C16.6409 10.3827 17.2359 8.89503 18.3471 9.03136L18.4639 9.05135L19.5274 9.28457C18.6667 6.90822 16.6971 4.97828 14.0729 4.27513C10.2419 3.2486 6.313 5.18495 4.72337 8.67994C4.49472 9.18267 3.90181 9.40485 3.39909 9.17619C2.89636 8.94754 2.67418 8.35463 2.90283 7.8519Z" fill="#FFA800"/>
                                </g>
                                <defs>
                                <clipPath id="clip0_231_2340">
                                <rect width="24" height="24" fill="white"/>
                                </clipPath>
                                </defs>
                            </svg>
                            <svg type="${i}" class="copyKey v-icon-cursor" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
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
                    <div style="display: none" class="ui icon input">
                        <input type="text" placeholder="" data-type="${i}">
                        <span>
                            <svg data-type="${i}" width="24" height="24" class="saveTblConfig v-icon-cursor clickableIcon icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" clip-rule="evenodd" d="M3 5C3 3.89543 3.89543 3 5 3H15.5858C16.1162 3 16.6249 3.21071 17 3.58579L19.4142 6C19.7893 6.37507 20 6.88378 20 7.41421V19C20 20.1046 19.1046 21 18 21H5C3.89543 21 3 20.1046 3 19V5ZM8 5H5V19H7V14C7 12.8954 7.89543 12 9 12H14C15.1046 12 16 12.8954 16 14V19H18V7.41421L15.5858 5H15V7.5C15 8.32843 14.3284 9 13.5 9H9.5C8.67157 9 8 8.32843 8 7.5V5ZM10 5V7H13V5H10ZM14 19H9V14H14V19Z" fill="#00B2FF"/>
                            </svg>
                            <svg class="v-icon-cursor cancelTblConfig" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g clip-path="url(#clip0_232_2352)">
                                <path d="M12 2C17.5228 2 22 6.47715 22 12C22 17.5228 17.5228 22 12 22C6.47715 22 2 17.5228 2 12C2 6.47715 6.47715 2 12 2ZM12 4C7.58172 4 4 7.58172 4 12C4 16.4183 7.58172 20 12 20C16.4183 20 20 16.4183 20 12C20 7.58172 16.4183 4 12 4ZM9.87874 8.46443L12 10.5857L14.1213 8.46441C14.5118 8.07388 15.145 8.07388 15.5355 8.46441C15.926 8.85493 15.926 9.4881 15.5355 9.87862L13.4142 11.9999L15.5356 14.1213C15.9261 14.5118 15.9261 15.145 15.5356 15.5355C15.1451 15.926 14.5119 15.926 14.1214 15.5355L12 13.4141L9.87864 15.5355C9.48812 15.926 8.85496 15.926 8.46443 15.5355C8.07391 15.145 8.07391 14.5118 8.46443 14.1213L10.5858 11.9999L8.46452 9.87864C8.074 9.48812 8.074 8.85496 8.46452 8.46443C8.85505 8.07391 9.48821 8.07391 9.87874 8.46443Z" fill="#949494"/>
                                </g>
                                <defs>
                                <clipPath id="clip0_232_2352">
                                <rect width="24" height="24" fill="white"/>
                                </clipPath>
                                </defs>
                            </svg>
                        </span>
                    </div>
                   </td>
               </tr>`
        }
        if(i == "TokenLifeTime") {
            return  `<tr data-key="TableName">
                    <td class="d-flex align-items-center">${convertCamelCaseToNormal(i)}</td>
                    <td>
                        <div class="d-flex justify-content-between" real-value="${getRowContent(tableInfoData[`${i}`])}">
                            ${getRowContent(tableInfoData[`${i}`])}
                            <div>
                                <svg class="tblChangeConfig v-icon-cursor"
                                    width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M21.0304 2.96967C22.4279 4.36713 22.4279 6.63286 21.0304 8.03033L9.0621 19.9987C8.78522 20.2755 8.44089 20.4754 8.06312 20.5784L2.94743 21.9736C2.38756 22.1263 1.87383 21.6125 2.02652 21.0527L3.42171 15.937C3.52474 15.5592 3.72456 15.2149 4.00144 14.938L15.9698 2.96967C17.3672 1.5722 19.633 1.5722 21.0304 2.96967ZM15.0003 6.06057L5.0621 15.9987C4.96981 16.0909 4.9032 16.2057 4.86886 16.3316L3.81901 20.1811L7.66845 19.1312C7.79437 19.0969 7.90915 19.0303 8.00144 18.938L17.9393 8.99957L15.0003 6.06057ZM17.0304 4.03033L16.0603 4.99957L18.9993 7.93957L19.9698 6.96967C20.7814 6.15799 20.7814 4.842 19.9698 4.03033C19.1581 3.21865 17.8421 3.21865 17.0304 4.03033Z" fill="#3BC862"/>
                                </svg>
                                <svg type="${i}" class="copyKey v-icon-cursor" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
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
                        <div style="display: none" class="ui icon input">
                            <input type="text" placeholder="" data-type="${i}">
                            <span>
                                <svg data-type="${i}" width="24" height="24" class="saveTblConfig v-icon-cursor clickableIcon icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" clip-rule="evenodd" d="M3 5C3 3.89543 3.89543 3 5 3H15.5858C16.1162 3 16.6249 3.21071 17 3.58579L19.4142 6C19.7893 6.37507 20 6.88378 20 7.41421V19C20 20.1046 19.1046 21 18 21H5C3.89543 21 3 20.1046 3 19V5ZM8 5H5V19H7V14C7 12.8954 7.89543 12 9 12H14C15.1046 12 16 12.8954 16 14V19H18V7.41421L15.5858 5H15V7.5C15 8.32843 14.3284 9 13.5 9H9.5C8.67157 9 8 8.32843 8 7.5V5ZM10 5V7H13V5H10ZM14 19H9V14H14V19Z" fill="#00B2FF"/>
                                </svg>
                                <svg class="v-icon-cursor cancelTblConfig" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <g clip-path="url(#clip0_232_2352)">
                                    <path d="M12 2C17.5228 2 22 6.47715 22 12C22 17.5228 17.5228 22 12 22C6.47715 22 2 17.5228 2 12C2 6.47715 6.47715 2 12 2ZM12 4C7.58172 4 4 7.58172 4 12C4 16.4183 7.58172 20 12 20C16.4183 20 20 16.4183 20 12C20 7.58172 16.4183 4 12 4ZM9.87874 8.46443L12 10.5857L14.1213 8.46441C14.5118 8.07388 15.145 8.07388 15.5355 8.46441C15.926 8.85493 15.926 9.4881 15.5355 9.87862L13.4142 11.9999L15.5356 14.1213C15.9261 14.5118 15.9261 15.145 15.5356 15.5355C15.1451 15.926 14.5119 15.926 14.1214 15.5355L12 13.4141L9.87864 15.5355C9.48812 15.926 8.85496 15.926 8.46443 15.5355C8.07391 15.145 8.07391 14.5118 8.46443 14.1213L10.5858 11.9999L8.46452 9.87864C8.074 9.48812 8.074 8.85496 8.46452 8.46443C8.85505 8.07391 9.48821 8.07391 9.87874 8.46443Z" fill="#949494"/>
                                    </g>
                                    <defs>
                                    <clipPath id="clip0_232_2352">
                                    <rect width="24" height="24" fill="white"/>
                                    </clipPath>
                                    </defs>
                                </svg>
                            </span>
                        </div>
                    </td>
                </tr>`
        }
        if(i == "Status") {
            return ` <tr data-key="${i}">
                    <td class="d-flex align-items-center">${convertCamelCaseToNormal(i)}</td>
                    <td>
                        ${getDataIcon(statusIcon)(tableInfoData[`${i}`]).icon}
                    </td>
                </tr>`
        }
        if(i == "Permission") {
            return ` <tr data-key="${i}">
                    <td class="d-flex align-items-center">${convertCamelCaseToNormal(i)}</td>
                    <td>
                    ${getRowContent(tableInfoData[`${i}`])}
                    ${getSuffChar(i)}
                    </td>
                </tr>`
        }
        return `
            <tr data-key="${i}">
                <td class="d-flex align-items-center">${convertCamelCaseToNormal(i)}</td>
                <td>
                ${getRowContent(tableInfoData[`${i}`])}
                ${getSuffChar(i)}
                </td>
            </tr>`
    }).join(" \n ");
    $("#tableConfigBody").empty().append(tableDatabaseInfo);
    
    //Set data for row structure table
    let describeContent = "";
    let DescribeTables = tableInfoData.DescribeTables;
    if(DescribeTables) {
        describeContent = DescribeTables.map(i => {
            return `<tr data-key="RowStructure">
                <td class="d-flex align-items-center">${convertCamelCaseToNormal(i.ColumnName)}</td>
                <td>
                    <div class="d-flex justify-content-between">
                        ${i.Type ? getDataTextByDataType(i.Type) : "<span style='color: red'>Unknown</span>"}
                    </div>
                </td>
                <td class="d-flex align-items-center">
                    ${i.Size + " bytes" || "<span style='color: red'>Unknown</span>"} 
                </td>
                <td class="">
                    ${i.DefaultValue} 
                </td>
            </tr> `
        })
    }
    $("#rowStructureTable").empty().html(describeContent);

    //Set table for login rules
    let loginRuleContent = "";
    if(tableInfoData.LoginRule) {
        let loginRule = tableInfoData.LoginRule;
        loginRuleContent += Object.entries(loginRule).map(i => {
            return `<tr data-key="RowStructure">
                    <td class="d-flex align-items-center">${convertCamelCaseToNormal(i[0])}</td>
                    <td>
                        <div class="ui checkbox" data='${JSON.stringify(i)}'>
                            <input type="checkbox" ${i[1] ? 'checked' : ''} style="opacity: 1 !important">
                        </div>
                    </td>
                </tr>
                `
        }).join(" \n ");
        $("#loginRuleTable").empty().html(loginRuleContent);
    }

    // Yesterday data table
    $("#yesterdayTable").empty().append(`
        <tr>
            <td>Request</td>
            <td>${tableInfoData.Yesterday.Request}</td>
        </tr>
        <tr>
            <td>Number user</td>
            <td>${tableInfoData.Yesterday.NumberUser}</td>
        </tr>
        <tr>
            <td>Data transfer</td>
            <td>${tableInfoData.Yesterday.DataTransfer}</td>
        </tr>
        `);
    // This month data table
    $("#thisMonthTable").empty().append(`
       <tr>
            <td>Request</td>
            <td>${tableInfoData.ThisMonth.Request}</td>
        </tr>
        <tr>
            <td>Number user</td>
            <td>${tableInfoData.ThisMonth.NumberUser}</td>
        </tr>
        <tr>
            <td>Data transfer</td>
            <td>${tableInfoData.ThisMonth.DataTransfer}</td>
        </tr>
        `)
    // All time data table
    $("#allTimeTable").empty().append(`
        <tr>
            <td>Data increase</td>
            <td>${tableInfoData.AllTime.Request}</td>
        </tr>
        <tr>
            <td>Data transfer</td>
            <td>${tableInfoData.AllTime.DataTransfer}</td>
        </tr>
        `)
}

function setEvChangeTableName() {
    $("#tblChangeTableName").unbind("click").click(e => {
        let currentTarget = $(e.currentTarget.parentNode);
        let inputElWrapper = $(e.currentTarget.parentNode.nextElementSibling);
        currentTarget.addClass("none-display");
        inputElWrapper.css("display", "block");
        let inputEl = inputElWrapper.find("input");
        inputEl.val(currentDb.TableName);
    });
    $("#cancelChangeName").unbind("click").click(e => {
        let currentTarget = $(e.currentTarget.parentNode.parentNode);
        let nameElWrapper = $(e.currentTarget.parentNode.parentNode.previousElementSibling);
        nameElWrapper.removeClass("none-display");
        currentTarget.css("display", "none");
    })
    $("#saveTblName").unbind("click").click(async (e) => {
        let input = $(e.currentTarget.parentNode.parentNode).find("input").val();
        const resp = await changeTableNameOrDes(input, currentDb.TableDescription, currentDb.regionId, currentDb.tableId);
        if(resp.requestStatus) {
            successNotify(null, "Change table name success!");
            setTimeout(() => {
                window.location.reload();
            }, 500);
        }
        else {
            errorNotify(null, "Error when change table name!")
        }
    });
}

function setReloadAndCopyKey() {
    $(".reloadKey").unbind("click").click(async e => {
        let type = $(e.currentTarget).attr("type");
        //Call api:
        let payload = {
            "regionId": Number(currentDb.regionId),
            "tableId": Number(currentDb.tableId)
        }
        let request = null;
        switch(type) {
            case 'AccessToken':
                payload = {...payload, accessToken: -1}
                request = configAccessToken;
                break;
            case 'ReadToken':
                payload = {...payload, readToken: -1}
                request = configReadToken;
                break;
            case 'WriteToken':
                payload = {...payload, writeToken: -1}
                request = configWriteToken;
                break;
        }
        showGlobalLoading();
        const resp = await request(payload);
        hideGlobalLoading();
        if(resp.requestStatus) {
            successNotify(null, "Reload token success!");
            $(`.row-${type}`).html(resp.data[`${type}`]);
            tableInfoData[`${type}`] = resp.data[`${type}`];
        }
        else {
            errorNotify(null, resp.message || "Some thing went wrong!")
        }
    })
    $(".copyKey").unbind("click").click(e => {
        copyToClipboard($(e.currentTarget.parentNode.parentNode).attr("real-value").trim());
        successNotify("Copied!")
    });
}

function setTableKeyEvent() {
    $(".tblChangeConfig").unbind("click").click(e => {
        let currentTarget = $(e.currentTarget.parentNode.parentNode);
        let inputElWrapper = $(e.currentTarget.parentNode.parentNode.nextElementSibling);
        currentTarget.addClass("none-display");
        inputElWrapper.css("display", "block");
        let inputEl = inputElWrapper.find("input");
        let dataType = inputEl.attr("data-type");
        inputEl.val(tableInfoData[`${dataType}`]);
    })
    $(".saveTblConfig").unbind("click").click(async e => {
        let type = $(e.currentTarget).attr("data-type");
        let inputValue = $(e.currentTarget.parentNode.previousElementSibling).val();
        let request = null;
        let payload = {
            "regionId": Number(currentDb.regionId),
            "tableId": Number(currentDb.tableId)
        }
        switch(type) {
            case 'AccessToken':
                payload = {...payload, accessToken: inputValue}
                request = configAccessToken;
                break;
            case 'ReadToken':
                payload = {...payload, readToken: inputValue}
                request = configReadToken;
                break;
            case 'WriteToken':
                payload = {...payload, writeToken: inputValue}
                request = configWriteToken;
                break;
            default:
                errorNotify("Chưa có api!");
                break;
        };
        showGlobalLoading();
        const resp = await request(payload);
        hideGlobalLoading();
        if(resp.requestStatus) {
            successNotify("Update info successfully!");
            $(`.row-${type}`).html(resp.data[`${type}`]);
            tableInfoData[`${type}`] = resp.data[`${type}`];
            $(e.currentTarget.parentNode.parentNode.previousElementSibling).removeClass("none-display");
            $(e.currentTarget.parentNode.parentNode).css("display", "none");
        }
        else {
            errorNotify(null, resp.message || "Can not set config!");
        }
    })
    $(".cancelTblConfig").unbind("click").click(e => {
        let currentTarget = $(e.currentTarget.parentNode.parentNode);
        let inputElWrapper = $(e.currentTarget.parentNode.parentNode.previousElementSibling);
        inputElWrapper.removeClass("none-display");
        currentTarget.css("display", "none");
    })
}

function setEventForTable() {
    //Add event for table
    $("#tblUpdateDescribe").unbind("click").click(e => {
        vModal("Config describe table",
            modalRowStructure(),
            modalRowStructureFooter,
            "vModalClose",
            "modalStructureConfirm",
            async (close) => {
                const structure = tableInfoData.DescribeTables;
                const payload = {
                    "describeTables": structure,
                    "regionId": currentDb.regionId,
                    "tableId": currentDb.tableId
                }
                console.log(payload)
                const resp = await configRowStructure(payload);
                if(resp.requestStatus) {
                    successNotify("Update row structure");
                    window.location.reload();
                }
                else {
                    errorNotify(null, resp.message || "Error when update row structure")
                }
                close();
            }, () => {
                console.log("Closed");
            });
        $('.v-tooltip').popup({
            on    : 'hover',
            inline: true
        });
        //Add event
        $("#modalAddDescribe").unbind("click").click(e => {
            tableInfoData.DescribeTables.push({
                ColumnName: "",
                Type: "1",
                Size: 1,
                DefaultValue: null
            });
            const content = tableInfoData.DescribeTables.map((i, index) => {
                return buildDescribeTable({...i, index: index});
            }).join(" ");
            $("#modalTableStructure").empty().html(content);
            addEvAutoSetColumnSize();
            setEvDescribeName();
            setEvForBtnDeleteAndUpDownArrow();
            addEventForInputInByteRow();
        });
        setEvDescribeName();
        addEvAutoSetColumnSize();
        setEvForBtnDeleteAndUpDownArrow();
        addEventForInputInByteRow();
    });

    $("#loginRuleTable input").unbind("change").change(async e => {
        let data = JSON.parse($(e.currentTarget.parentNode).attr("data") || "[]");
        let currentLoginRules = tableInfoData.LoginRule;
        let key = data[0];
        let value = data[1];
        currentLoginRules[`${key}`] = !value;
        showGlobalLoading();
        const resp = await updateLoginRules(currentLoginRules, currentDb.tableId, currentDb.regionId);
        hideGlobalLoading();
        if(resp.requestStatus) {
            tableInfoData.LoginRule = currentLoginRules;
            $(e.currentTarget.parentNode).attr("data", JSON.stringify([key, !value]));
            successNotify("Login rule was updated sucessfully!");
        } else {
            errorNotify(null, resp.message || "Can not update login rule");
        }
    })
}

async function getTableConfig() {
    showGlobalLoading();
    const resp = await getTableInfo(currentDb.regionId, currentDb.tableId);
    hideGlobalLoading();
    if(resp.requestStatus) {
        tableInfoData = resp.data;
    }
    else {
        errorNotify(null, resp.message || "Error when get table info!");
    }
}

function addEvAutoSetColumnSize() {
    $(".modalTableSelect").unbind("change").on('change', function() {
        let currentRowIndex = $(this).attr("data");
        tableInfoData.DescribeTables = tableInfoData.DescribeTables.map((item, index) => {
            if(index == currentRowIndex) {
                item.Size = getByteFromDataType(this.value);
                item.Type = this.value;
            }
            return item;
        });
        const content = tableInfoData.DescribeTables.map((i, index) => {
            return buildDescribeTable({...i, index: index});
        }).join(" ");
        $("#modalTableStructure").empty().html(content);

        setEvDescribeName();
        addEvAutoSetColumnSize();
        setEvForBtnDeleteAndUpDownArrow();
        addEventForInputInByteRow();
    });
}

function addEventForInputInByteRow() {
    $(".v-number-of-byte").unbind("change").change(e => {
        let indexx = $(e.currentTarget).attr("data");
        let val = $(e.currentTarget).val();
        if(isNaN(Number(val))) {
            $(e.currentTarget).val(0);
            return;
        }
        tableInfoData.DescribeTables = tableInfoData.DescribeTables.map((item, index) => {
            if(index == indexx) {
                item.dataSize = val;
            }
            return item;
        });
    })
    console.log("hihihi")
    $(".v-default-value").unbind("change").change(e => {
        let indexx = $(e.currentTarget).attr("data");
        let val = $(e.currentTarget).val();
        tableInfoData.DescribeTables = tableInfoData.DescribeTables.map((item, index) => {
            if(index == indexx) {
                item.DefaultValue = val;
            }
            return item;
        });
    })
}

function setEvDescribeName() {
    $(".inputModalName").unbind("change").change(e => {
        let currentIndexRow = $(e.currentTarget).attr("data");
        let value = $(e.currentTarget).val();
        tableInfoData.DescribeTables = tableInfoData.DescribeTables.map((item, index) => {
            if(index == currentIndexRow) {
                item.ColumnName = value;
            }
            return item;
        });
    })
}

function setEvForBtnDeleteAndUpDownArrow() {
    $(".modalDeleteRow").unbind("click").click(e => {
        let indexx = $(e.currentTarget).attr("data");
        tableInfoData.DescribeTables = tableInfoData.DescribeTables.filter((item, index) => {
            if(index == indexx) return false;
            return true;
        });
        const content = tableInfoData.DescribeTables.map((i, index) => {
            return buildDescribeTable({...i, index});
        }).join(" ");
        $("#modalTableStructure").empty().html(content); 
        addEvAutoSetColumnSize();
        setEvForBtnDeleteAndUpDownArrow();
    });
    // $(".moveUpIconRowStr").unbind("click").click(e => {
    //     let indexx = $(e.currentTarget).attr("data");
    //     if(indexx <= 0) return;
    //     tableInfoData.DescribeTables = tableInfoData.DescribeTables.filter((item, index) => {
    //         if(index == indexx) return false;
    //         return true;
    //     });
    //     let temp = tableInfoData.DescribeTables[indexx - 1];
    //     tableInfoData.DescribeTables[indexx - 1] = tableInfoData.DescribeTables[indexx];
    //     tableInfoData.DescribeTables[indexx] = temp;
    //     let bodyContent = tableInfoData.DescribeTables.map((i, index) => {
    //         return buildDescribeTable({...i, index});
    //     }).join(" ");
    //     $("#modalTableStructure").empty().html(bodyContent);
    //     addEvAutoSetColumnSize();
    //     setEvForBtnDeleteAndUpDownArrow();
    // })
    // $(".moveDownIconRowStr").unbind("click").click(e => {
    //     let indexx = $(e.currentTarget).attr("data");
    //     if(indexx <= 0) return;
    //     tableInfoData.DescribeTables = tableInfoData.DescribeTables.filter((item, index) => {
    //         if(index == indexx) return false;
    //         return true;
    //     });
    //     let temp = tableInfoData.DescribeTables[indexx + 1];
    //     tableInfoData.DescribeTables[indexx + 1] = tableInfoData.DescribeTables[indexx];
    //     tableInfoData.DescribeTables[indexx] = temp;
    //     let bodyContent = tableInfoData.DescribeTables.map((i, index) => {
    //         return buildDescribeTable({...i, index});
    //     }).join(" ");
    //     $("#modalTableStructure").empty().html(bodyContent);
    //     addEvAutoSetColumnSize();
    //     setEvForBtnDeleteAndUpDownArrow();
    // })
}

function getSuffChar(i) {
    if(i == "RowLength")
        return " bytes";
    return "";
}

function getRowContent(text) {
    if(text == null) {
        return "<span style='color: red'>Unknown</span>";
    }
    if(typeof text == "string" && text.trim() == "") {
       return "<span style='color: red'>Unknown</span>"; 
    }
    if(typeof text == "string" && text.includes("+07:00")) {
        return formatDate(text);
    }
    return text;
}

function addUiEvent() {
    $("#deleteTable").unbind("click").click(() => {
        let onDelete = async () => {
            const resp = await deleteDbTable(currentDb.regionId, currentDb.tableId);
            if(resp.requestStatus) {
                successNotify(null, "Delete table successfully!")
                setTimeout(() => {
                    redirectTo("/user");
                }, 2000);
            }
            else {
                errorNotify(null, resp.message || "Error when delete table");
            }
        }
        vModal("Confirm delete", modalDeleteTable(currentDb.TableName), modalDeleteTableFooter,"vModalClose", "del-db", onDelete);
    });
}


const modalDeleteTableFooter =`
    <div class="d-flex justify-content-end">
        <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Cancel</button>
        <button type="button" id="del-db" class="btn btn-danger">Delete</button>
    </div>
    `

const modalDeleteTable = (tableName) => {
    return `
    <div>
        Are you sure to delete database ${tableName}?
    </div>
    `
}

const modalRowStructure = () => {
    let DescribeTables = tableInfoData.DescribeTables;
    let body;
    if(DescribeTables) {
        body = DescribeTables.map((item, index) => {
            return buildDescribeTable({...item, index});
        }).join(" ")
    }
    return `
    <div style="max-height: 50vh; overflow: auto">
        <table class="ui celled table">
            <thead class="full-width" style="position: sticky; top: 0; z-index: 999999">
                <tr>
                    <th>Column name</th>
                    <th>Data type</th>
                    <th>Data size</th>
                    <th>Default value</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody id="modalTableStructure">
                ${body}
            </tbody>
            <tfoot class="full-width" class="full-width" style="position: sticky; bottom: 0; z-index: 9">
                <tr>
                <th colspan="5">
                    <div  class="d-flex justify-content-end">
                        <button type="button" id="modalAddDescribe" class="btn btn-primary">Add row</button>
                    </div>
                </th>
                </tr>
            </tfoot>
        </table>
    </div>
    `
}

const modalRowStructureFooter =`
    <div class="d-flex justify-content-end">
        <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Close</button>
        <button type="button" id="modalStructureConfirm" class="btn btn-primary ml-2">Confirm</button>
    </div>
`

const buildDescribeTable = ({ColumnName, Type, Size, index, DefaultValue}) => {
    console.log(DefaultValue)
    return `<tr>
        <td>
            <div class="ui input">
                <input class="inputModalName" data="${index}" type="text" value="${ColumnName}" placeholder="ColumnName ...">
            </div>
        </td>
        <td>
            <select class="custom-select modalTableSelect" data="${index}">
                <option ${Type == '1' ? "selected" :""} value="1">Boolean</option>
                <option ${Type == '10' ? "selected" :""} value="10">Byte</option>
                <option ${Type == '11' ? "selected" :""} value="11">Status</option>
                <option ${Type == '12' ? "selected" :""} value="12">Account Status</option>
                <option ${Type == '13' ? "selected" :""} value="13">Avatar</option>
                <option ${Type == '20' ? "selected" :""} value="20">Short</option>
                <option ${Type == '40' ? "selected" :""} value="40">Integer</option>
                <option ${Type == '41' ? "selected" :""} value="41">Float</option>
                <option ${Type == '80' ? "selected" :""} value="80">Long</option>
                <option ${Type == '81' ? "selected" :""} value="81">Double</option>
                <option ${Type == '82' ? "selected" :""} value="82">String</option>
                <option ${Type == '83' ? "selected" :""} value="83">Time mili</option>
                <option ${Type == '100' ? "selected" :""} value="100">Binary</option>
                <option ${Type == '101' ? "selected" :""} value="101">List</option>
            </select>
        </td>
        <td class="byte-quantity">
            <div class="byte-form-2 ui input right labeled">
                <input type="text" value="${Size || 0}" class="v-number-of-byte" data="${index}"
                placeholder="Enter number of byte...">
                <div class="ui basic label">
                    bytes
                </div>
            </div>
        </td>
        <td class="byte-quantity">
            <div class="byte-form-2 ui input right labeled ">
                <input type="text" value="${DefaultValue || ''}" class="v-default-value" data="${index}">
            </div>
        </td>
        <td>
            <svg class="v-icon-cursor modalDeleteRow" data="${index}" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
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
    </tr>`
    //     < i
    // data = "${index}"
    // className = "mx-2 arrow grey up large icon v-icon-cursor v-tooltip moveUpIconRowStr"
    // data - content = "Move row up" > < /i>
    // <i data="${index}" className="arrow grey down large icon v-icon-cursor v-tooltip moveDownIconRowStr"
    //    data-content="Move row down"></i>
}