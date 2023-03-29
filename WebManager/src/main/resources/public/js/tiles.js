import { useAuthentication, setEventForGeneralComponent } from "./global_functions.js";

$(document).ready(() => {
    useAuthentication(true).then( async(auth) => {
        if(!auth) return;
        // Set event for global page (header, footer)
        setEventForGeneralComponent();
    })
 
})