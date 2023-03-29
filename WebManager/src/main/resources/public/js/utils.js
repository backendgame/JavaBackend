import {dataType, dataTypeSize, isDataString} from "./CONFIG.js";
import {getTableDataFromTableKey} from "./global_functions.js";

const TIME_OUT_LOADING = 10000;

function redirectTo(link) {
    window.location.href = link;
}

function isValidEmail(email) {
  return String(email)
      .toLowerCase()
      .match(
          /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
      );
}

function stringIsNullOrEmpty(str) {
    if(str == undefined) {
        return true;
    }
    if(str == null) {
        return true;
    }
    if(str.trim() == "") {
        return true;
    }
}

function hideTokenCode(token) {
    if (!token) return "";
    if(token.length <= 6) return "******";
    token = String(token);
    let first3char = token.slice(0, 3);
    let last3char = token.slice(token.length - 3, token.length);
    let remainTextCount = token.length - 6;
    let characters = "";
    for (let index = 0; index < remainTextCount; index++) {
        characters += "*";
    }
    return first3char + characters + last3char;
}

async function getDbsKeyFromQuery(dbId) {
    let respTable = null;
    if(dbId) {
        respTable = await getTableDataFromTableKey(dbId);
    }
    return respTable;
}

function getDatabaseIcon(dbId) {
    switch(dbId) {
        case 0:
            return `<svg width="20" height="20" viewBox="0 0 42 42" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="21" cy="21" r="21" fill="white"/>
                <g clip-path="url(#clip0_217_467)">
                <path fill-rule="evenodd" clip-rule="evenodd" d="M24.7498 30.75H17.2498C17.1845 30.747 17.168 30.7485 17.1035 30.7358C16.8868 30.6923 16.6925 30.5483 16.5883 30.3533C16.4848 30.159 16.472 29.9183 16.5568 29.7128C16.6595 29.4653 16.9033 29.2807 17.1763 29.2537C17.201 29.2515 17.225 29.2515 17.2498 29.25H18.1145L18.7603 25.377C18.7745 25.308 18.7933 25.2398 18.8233 25.176C18.9343 24.9443 19.1705 24.7777 19.4293 24.753C19.4533 24.7507 19.4765 24.7508 19.4998 24.75H22.4998C22.523 24.7508 22.5463 24.7522 22.5703 24.753C22.64 24.7635 22.709 24.7763 22.7743 24.8018C22.9925 24.888 23.1643 25.08 23.225 25.308C23.231 25.3305 23.2348 25.3538 23.2393 25.377L23.885 29.25H24.7498C24.7745 29.2515 24.7985 29.2522 24.8233 29.2537C24.8473 29.2575 24.872 29.2597 24.896 29.2642C25.1848 29.322 25.427 29.5598 25.4855 29.8538C25.5335 30.0945 25.454 30.357 25.28 30.5303C25.1758 30.6345 25.0408 30.7065 24.896 30.7358C24.8315 30.7485 24.815 30.747 24.7498 30.75V30.75Z" fill="#BDBDBD"/>
                <path fill-rule="evenodd" clip-rule="evenodd" d="M21 24.75H22.5C22.5232 24.7508 22.5465 24.7522 22.5705 24.753C22.6402 24.7635 22.7092 24.7763 22.7745 24.8018C22.9927 24.888 23.1645 25.08 23.2252 25.308C23.2312 25.3305 23.235 25.3538 23.2395 25.377L23.8853 29.25H24.75L24.8235 29.2537C24.8475 29.2575 24.8722 29.2597 24.8962 29.2642C25.185 29.322 25.4273 29.5598 25.4858 29.8538C25.5338 30.0945 25.4543 30.357 25.2803 30.5303C25.176 30.6345 25.041 30.7065 24.8962 30.7358C24.8317 30.7485 24.8152 30.747 24.75 30.75H21V24.75V24.75Z" fill="#9E9E9E"/>
                <path fill-rule="evenodd" clip-rule="evenodd" d="M30.0047 11.25C30.4667 11.2523 30.9257 11.4803 31.2047 11.856C31.3944 12.111 31.4987 12.4268 31.5002 12.7455C31.5062 16.7483 31.5062 20.7518 31.5002 24.7545C31.4979 25.2135 31.2752 25.6643 30.9092 25.9433C30.6512 26.1398 30.3309 26.2485 30.0047 26.25C24.0017 26.259 17.9987 26.259 11.9957 26.25C11.5277 26.2478 11.0649 26.016 10.7852 25.6298C10.6022 25.377 10.5017 25.0688 10.5002 24.7545C10.4934 20.7518 10.4934 16.7483 10.5002 12.7455C10.5017 12.366 10.6532 11.9895 10.9142 11.715C11.1917 11.424 11.5884 11.2523 11.9957 11.25C17.9987 11.241 24.0017 11.241 30.0047 11.25V11.25Z" fill="#E0E0E0"/>
                <path fill-rule="evenodd" clip-rule="evenodd" d="M21 11.2433C24.0015 11.2433 27.003 11.2455 30.0045 11.25C30.4665 11.2523 30.9255 11.4803 31.2045 11.856C31.3943 12.111 31.4985 12.4268 31.5 12.7455C31.506 16.7483 31.506 20.7518 31.5 24.7545C31.4977 25.2135 31.275 25.6643 30.909 25.9433C30.651 26.1398 30.3308 26.2485 30.0045 26.25C27.003 26.2545 24.0015 26.2568 21 26.2568V11.2433V11.2433Z" fill="#BDBDBD"/>
                <path d="M21 12.75H12V23.25H21V12.75Z" fill="#777777"/>
                <path d="M30 12.75H21V23.25H30V12.75Z" fill="#4E4E4E"/>
                </g>
                <defs>
                <clipPath id="clip0_217_467">
                <rect width="24" height="24" fill="white" transform="translate(9 9)"/>
                </clipPath>
                </defs>
            </svg>`
        case 1:
            return `<svg width="20" height="20" viewBox="0 0 42 42" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="21" cy="21" r="21" fill="white"/>
                <g clip-path="url(#clip0_217_462)">
                <path d="M33 9H9V33H33V9Z" fill="white" fill-opacity="0.01"/>
                <path d="M18 12H13C12.4477 12 12 12.4477 12 13V18C12 18.5523 12.4477 19 13 19H18C18.5523 19 19 18.5523 19 18V13C19 12.4477 18.5523 12 18 12Z" fill="#2F88FF" stroke="#1A5FBA" stroke-width="2" stroke-linejoin="round"/>
                <path d="M18 23H13C12.4477 23 12 23.4477 12 24V29C12 29.5523 12.4477 30 13 30H18C18.5523 30 19 29.5523 19 29V24C19 23.4477 18.5523 23 18 23Z" fill="#2F88FF" stroke="#1A5FBA" stroke-width="2" stroke-linejoin="round"/>
                <path d="M26.5 19C28.433 19 30 17.433 30 15.5C30 13.567 28.433 12 26.5 12C24.567 12 23 13.567 23 15.5C23 17.433 24.567 19 26.5 19Z" fill="#2F88FF" stroke="#1A5FBA" stroke-width="2" stroke-linejoin="round"/>
                <path d="M29 23H24C23.4477 23 23 23.4477 23 24V29C23 29.5523 23.4477 30 24 30H29C29.5523 30 30 29.5523 30 29V24C30 23.4477 29.5523 23 29 23Z" fill="#2F88FF" stroke="#1A5FBA" stroke-width="2" stroke-linejoin="round"/>
                </g>
                <defs>
                <clipPath id="clip0_217_462">
                <rect width="24" height="24" fill="white" transform="translate(9 9)"/>
                </clipPath>
                </defs>
            </svg>`
        case 2:
            return `<svg width="20" height="20" viewBox="0 0 42 42" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="21" cy="21" r="21" fill="white"/>
                <g clip-path="url(#clip0_211_401)">
                <path d="M33.0001 21.0735C33.0001 14.4045 27.6271 8.99854 21.0001 8.99854C14.3701 9.00004 8.99707 14.4045 8.99707 21.075C8.99707 27.1005 13.3861 32.0955 19.1221 33.0015V24.564H16.0771V21.075H19.1251V18.4125C19.1251 15.387 20.9176 13.716 23.6581 13.716C24.9721 13.716 26.3446 13.9515 26.3446 13.9515V16.9215H24.8311C23.3416 16.9215 22.8766 17.853 22.8766 18.8085V21.0735H26.2036L25.6726 24.5625H22.8751V33C28.6111 32.094 33.0001 27.099 33.0001 21.0735Z" fill="#059BE5"/>
                </g>
                <defs>
                <clipPath id="clip0_211_401">
                <rect x="9" y="9" width="24" height="24" rx="12" fill="white"/>
                </clipPath>
                </defs>
            </svg>`
        case 3:
            return `<svg width="20" height="20" viewBox="0 0 42 42" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="21" cy="21" r="21" fill="white"/>
                <path d="M10.6364 28.3613H14.4546V19.0886L9 14.9977V26.725C9 27.6291 9.73228 28.3614 10.6364 28.3614V28.3613Z" fill="#4285F4"/>
                <path d="M27.5454 28.3613H31.3636C32.2677 28.3613 33 27.629 33 26.725V14.9977L27.5454 19.0886V28.3613Z" fill="#34A853"/>
                <path d="M27.5454 11.9977V19.0886L33 14.9977V12.8159C33 10.7936 30.6914 9.63861 29.0727 10.8523L27.5454 11.9977Z" fill="#FBBC04"/>
                <path fill-rule="evenodd" clip-rule="evenodd" d="M14.4546 19.0886V11.9977L21 16.9068L27.5455 11.9977V19.0886L21 23.9977L14.4546 19.0886Z" fill="#EA4335"/>
                <path d="M9 12.8159V14.9977L14.4546 19.0886V11.9977L12.9273 10.8523C11.3086 9.63862 9 10.7936 9 12.8158V12.8159Z" fill="#C5221F"/>
            </svg>`
        case 4:
            return `
            <svg width="20" height="20" viewBox="0 0 42 42" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="21" cy="21" r="21" fill="white"/>
                <path d="M12 16.0347H10.5469C9.69361 16.0372 9.00248 16.7283 9 17.5816V22.7378C9.00248 23.5911 9.69361 24.2822 10.5469 24.2847H12C12 24.2847 12.0645 15.9702 12 16.0347Z" fill="#CCCCCC"/>
                <path d="M13.125 15.9473V24.405L16.317 25.0321L17.7942 29.9565H19.8566V25.7273L30 27.7197V12.6327L13.125 15.9473Z" fill="#E21B1B"/>
                <path d="M33 12.0435L31.125 12.4117V27.9405L33 28.3087V12.0435Z" fill="#CCCCCC"/>
            </svg>`
        case 5:
            return `<svg width="20" height="20" viewBox="0 0 42 42" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="21" cy="21" r="21" fill="white"/>
                <g clip-path="url(#clip0_211_413)">
                <path d="M30.792 27.7035C30.429 28.542 29.9994 29.3139 29.5016 30.0235C28.8231 30.9908 28.2676 31.6605 27.8395 32.0323C27.1758 32.6426 26.4647 32.9552 25.7032 32.973C25.1566 32.973 24.4973 32.8175 23.73 32.5019C22.9601 32.1878 22.2525 32.0323 21.6056 32.0323C20.9271 32.0323 20.1994 32.1878 19.4211 32.5019C18.6415 32.8175 18.0136 32.9819 17.5334 32.9982C16.8032 33.0293 16.0754 32.7078 15.3489 32.0323C14.8852 31.6279 14.3052 30.9345 13.6104 29.9524C12.865 28.9035 12.2521 27.6872 11.772 26.3006C11.2578 24.8029 11 23.3526 11 21.9484C11 20.3401 11.3475 18.9528 12.0437 17.7904C12.5908 16.8566 13.3186 16.12 14.2295 15.5793C15.1405 15.0386 16.1247 14.763 17.1847 14.7454C17.7647 14.7454 18.5252 14.9248 19.4704 15.2774C20.4129 15.6312 21.0181 15.8106 21.2834 15.8106C21.4817 15.8106 22.154 15.6008 23.2937 15.1826C24.3714 14.7947 25.281 14.6342 26.0262 14.6974C28.0454 14.8604 29.5624 15.6563 30.5712 17.0904C28.7654 18.1846 27.8721 19.7171 27.8898 21.6831C27.9061 23.2145 28.4617 24.4888 29.5535 25.5006C30.0483 25.9703 30.6009 26.3332 31.2156 26.591C31.0823 26.9776 30.9416 27.348 30.792 27.7035V27.7035ZM26.161 9.48038C26.161 10.6807 25.7225 11.8013 24.8484 12.8386C23.7937 14.0718 22.5179 14.7844 21.1343 14.6719C21.1167 14.5279 21.1065 14.3764 21.1065 14.2171C21.1065 13.0649 21.6081 11.8317 22.4989 10.8235C22.9436 10.3129 23.5092 9.88847 24.1951 9.54986C24.8796 9.2163 25.5269 9.03183 26.1358 9.00024C26.1536 9.1607 26.161 9.32117 26.161 9.48037V9.48038Z" fill="black"/>
                </g>
                <defs>
                <clipPath id="clip0_211_413">
                <rect width="24" height="24" fill="white" transform="translate(9 9)"/>
                </clipPath>
                </defs>
            </svg>`
        case 6:
            return `
            <svg width="20" height="20" viewBox="0 0 42 42" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="21" cy="21" r="21" fill="white"/>
                <g clip-path="url(#clip0_211_402)">
                <path d="M9.12646 32.836V19.425L21.0187 26.4563L9.12646 32.836Z" fill="#FCD751"/>
                <path d="M32.8595 32.836V19.425L20.9673 26.4563L32.8595 32.836Z" fill="#FCD751"/>
                <path d="M9.12646 32.836L20.9671 26.4563L32.8593 32.836H9.12646Z" fill="#FFA229"/>
                <path d="M20.9671 9.11255L9.12646 19.425L20.9671 26.4563L32.8593 19.425L20.9671 9.11255Z" fill="#FECA5C"/>
                <path d="M20.9907 26.4422L30.2579 20.9625V12.3H11.8267V21.0235L20.9907 26.4422Z" fill="#F2F2F2"/>
                <path d="M23.8781 15.9656H14.5781V16.3687H23.8781V15.9656Z" fill="#CDCCCA"/>
                <path d="M26.7892 15.3187H25.0923V17.0156H26.7892V15.3187Z" fill="white"/>
                <path d="M23.8781 18.5156H14.5781V18.9188H23.8781V18.5156Z" fill="#CDCCCA"/>
                <path d="M26.7892 17.8688H25.0923V19.5656H26.7892V17.8688Z" fill="white"/>
                <path d="M23.8781 20.7188H14.5781V21.1219H23.8781V20.7188Z" fill="#CDCCCA"/>
                <path d="M23.8781 21.4078H14.5781V21.811H23.8781V21.4078Z" fill="#CDCCCA"/>
                <path d="M26.7892 20.4141H25.0923V22.1109H26.7892V20.4141Z" fill="white"/>
                <path d="M25.8514 16.7625L25.2139 16.1625L25.3732 15.9937L25.8279 16.425L26.4607 15.6609L26.6342 15.8062L25.8514 16.7625Z" fill="#333D47"/>
                <path d="M25.8657 19.2656L25.2329 18.6656L25.3876 18.5016L25.847 18.9328L26.4751 18.1641L26.6532 18.3141L25.8657 19.2656Z" fill="#333D47"/>
                <path d="M26.7892 23.011V22.9641H25.0923V24.0141L26.7892 23.011Z" fill="white"/>
                <path d="M16.2046 23.611L16.889 24.0141H23.878V23.611H16.2046Z" fill="#CDCCCA"/>
                </g>
                <defs>
                <clipPath id="clip0_211_402">
                <rect width="24" height="24" fill="white" transform="translate(9 9)"/>
                </clipPath>
                </defs>
            </svg>`;
        default:
            return "Unknown";
            break;
    }
}


function getDataTextByDataType(dataTypeId) {
    let rawData = Object.entries(dataType).map(item => {
        return {name: item[0] ? capitalizeFirstLetter(item[0]) : "", value: item[1]}
    });
    let find = rawData.find(i => i.value == dataTypeId);
    if(find) {
        return find.name;
    }
    return "Unknown";
}

function setStateButton(btnId, text, state) {
    let btn = document.getElementById(btnId);
    if(!btn) return;
    if(state == "loading") {
        btn.disabled = true;
        btn.innerHTML = "<i class='fa fa-spinner fa-spin'></i> " + text;
    }
    if(state == "no-loading") {
        btn.disabled = false;
        btn.innerHTML = text;
    }
}

function convertCamelCaseToNormal(text) {
    if(text == null) return "";
    if(text == "Row") {
        return "Data Row";
    }
    text = text.replaceAll("_","");
    let result = text
        .replace(/([A-Z])/g, ' $1')
        .replace(/^./, function (str) {
            return str.toUpperCase();
        });
    if(result.trim() == "") {
        return "Unnamed"
    }
    return result;
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

function getDataIcon(iconLists) {
    return (dataId) => {
        try {
            return iconLists[dataId] ? iconLists[dataId] : {
                icon: "Unknown",
                description: ""
            };
        }
        catch(e) {
            return {
                icon: "Unknown",
                description: ""
            }
        }
    }
}

function getUserCounts(all, computedData) {
    try {
        return all - computedData;
    }
    catch(e) {
        return 0;
    }
}

function getByteFromDataType(dataTypeId) {
    try {
        return dataTypeSize[dataTypeId]
    }
    catch(e) {
        return 0;
    }
}

function successNotify(title, msg) {
    var polipop = new Polipop('mypolipop', {
        layout: 'popups',
        position: 'top-right',
        theme: 'default',
        icons: true,
        insert: 'before',
        pool: 0,
        sticky: false,
        pauseOnHover: true,
        life: 2000,
        progressbar: true,
        effect: 'slide',
        easing: 'ease-in-out',
        closer: false
    });
    polipop.add({
        content: msg,
        title,
        type: 'success',
    });
}

function errorNotify(title, msg) {
    var polipop = new Polipop('mypolipop', {
        layout: 'popups',
        position: 'top-right',
        theme: 'default',
        icons: true,
        insert: 'before',
        pool: 0,
        sticky: false,
        pauseOnHover: true,
        life: 2000,
        progressbar: true,
        effect: 'slide',
        easing: 'ease-in-out',
        closer: false
    })
    polipop.add({
        content: msg,
        title,
        type: 'error',
    });
}

function addStyleWithElement(element, attrName, value) {
    if(element) {
        element.style[attrName] = value;
    }
}

function showGlobalLoading() {
    $(".loading").css("display", "block");
    setTimeout(() => {
        let loading = $(".loading");
        let isDisplay = loading ? loading.css("display") : null;
        if(isDisplay) {
            $("#loadingCancel").text("Cancel...");
            $("#loadingCancel").addClass("cancel-loading");
            $("#loadingCancel").click(() => {
                hideGlobalLoading();
            })
        }
    }, TIME_OUT_LOADING);
}

function hideGlobalLoading() {
    $(".loading").css("display", "none")
}

/**
 * Just store data in localstorage and add created time;
 * @param {String} name
 * @param {object} data 
 */
function setLocalStorage(name, data) {
    let obj = {
        [name]: data,
        createdTime: new Date().getTime()
    }
    localStorage.setItem(name,JSON.parse(obj))
}

/**
 * Return data stored before and this data is expired
 * @param {String} name 
 * @param {Number} maxExpiredTime 
 * @returns 
 */
function getLocalData(name, maxExpiredTime) {
    let data = localStorage.getItem(name);
    if(!data) {
        return null;
    }
    return {
        data, 
        isExpired: parseInt(Math.abs(new Date().getTime() - (data.createdTime || 0)) / (1000 * 60) % 60) <= maxExpiredTime
    }
}

function copyToClipboard(text) {
    navigator.clipboard.writeText(text);
}

function setErrorInput(groupId, text) {
    let element = $("#" + groupId);
    if(element) {
        //Set error text
        let errField = element.children(".invalid-feedback");
        if(errField) {
            errField.text(text);
            errField.css("display", "inline");
        }
        //Set error color
        let input =  $("#" + groupId + " input");
        if(input) {
            input.toggleClass("is-invalid");
        }
    }
}

function togglePwdVisibility() {
    let element = $(".v-toggle-password");
    let parentElement = element.parent();
    if(parentElement) {
        let inputField = parentElement.children("input");
        if(inputField) {
            const isShowPassword = element.hasClass("fa-eye");
            element.toggleClass("fa-eye");
            element.toggleClass("fa-eye-slash");
            if(isShowPassword) {
                inputField.prop("type", "password");
            }
            else {
                inputField.prop("type", "text");
            }
        }
    }
}

function resetErrorForm(groupId) {
    let element = $("#" + groupId);
    if(element) {
        //Set error text
        let errField = element.children(".invalid-feedback");
        if(errField) {
            errField.textContent = "";
            errField.css("display", "none");
        }
        //Set error color
        let errColor = $("#" + groupId + " input");
        if(errColor) {
            errColor.removeClass("is-invalid");
        }
    }
}

function mathRandom(max) {
    return Math.floor(Math.random() * max) + 1;
}

function downloadText(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);
  
    element.style.display = 'none';
    document.body.appendChild(element);
  
    element.click();
  
    document.body.removeChild(element);
}

function parseDataToSuitableType(dataTypeId, data) {
    try {
        if(dataTypeId == dataType.BOOLEAN) {
            if(data == 1 || data.trim() == "true") return 1;
            return 0;
        }
        if(dataTypeId == dataType.BYTE || dataTypeId == dataType.LIST || dataTypeId == dataType.BINARY) {
            let utf8Encode = new TextEncoder();
            return utf8Encode.encode(String(data));
        }
        if(isDataString[dataTypeId]) {
            return String(data);
        }
        return Number(data);
    }
    catch(e) {
        return data;
    }
}

function generateId() {
    return Date.now();
}
export {
    generateId,
    getDatabaseIcon,
    parseDataToSuitableType,
  redirectTo,
  isValidEmail,
  stringIsNullOrEmpty,
  setStateButton,
  errorNotify,
  successNotify,
  setErrorInput,
  togglePwdVisibility,
  resetErrorForm,
  mathRandom,
  showGlobalLoading, hideGlobalLoading,
  convertCamelCaseToNormal,
  getLocalData, setLocalStorage,
  copyToClipboard,
  downloadText,capitalizeFirstLetter,
  getDataTextByDataType,
  getByteFromDataType,
  getDataIcon,getDbsKeyFromQuery,
  hideTokenCode,getUserCounts
}