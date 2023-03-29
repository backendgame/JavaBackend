import { addRowToDB, filterDataInTable, getTableData, updateRowInTableData } from "./apis/table.js";
import { avatarIcon, dataType, isDataString, permissionIcon, statusIcon } from "./CONFIG.js";
import { getQueryParams, getTableDataFromTableKey, useAuthentication, setEventForGeneralComponent, vModal, formatDate } from "./global_functions.js";
import { capitalizeFirstLetter, convertCamelCaseToNormal, errorNotify, getDataIcon, getDataTextByDataType, hideGlobalLoading, redirectTo, showGlobalLoading, successNotify } from "./utils.js";

$(document).ready(() => {
    $('.v-dropdown').dropdown();
    useAuthentication(true)
    .then(async (auth) => {
        if(!auth) return;
        setEventForGeneralComponent();
    })
    .then(() => {
        initModalCreateNewTile();
    });
    $('.v-dropdown').dropdown();
});

function initModalCreateNewTile() {
    $("#addMoreTile").click(() => {
        let onAddTile = async (closeModal) => {
            alert("clicked!");
        }
        let onClosed = () => {
           
        }
        vModal("Create new tile", 
            modalAddTileBody(),
            modalAddTileFooter,
            "vModalClose", 
            "addTileButton", 
            onAddTile, onClosed);
    });
}

//-----------------------------------------------------------------------//

const modalAddTileBody = () => {
    return  `<form id="addRowTiles">
    <div class="form-group">
        <label>Type</label>
        <select class="custom-select" id="databaseTypeId">
            <option selected value="0">Leaderboard (Auto)</option>
            <option value="1">Leaderboard (Custom)</option>
            <option value="2">Tile Row</option>
            <option value="3">Tile Custom</option>
        </select>
    </div>
    <div class="form-group">
        <label for="name">Name</label>
        <input type="text" name="name" class="form-control">
    </div>
    <div class="form-group">
        <label for="desctiption">Description</label>
        <input type="text" name="desctiption" class="form-control">
    </div> 
</form>`;
}
const modalAddTileFooter = `
    <div class="d-flex justify-content-end">
        <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Cancel</button>
        <button type="button" id="addTileButton" class="btn btn-primary">Create</button>
    </div>
`;

