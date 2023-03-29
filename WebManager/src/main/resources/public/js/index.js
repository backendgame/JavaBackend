import { useAuthentication, setEventForGeneralComponent } from './global_functions.js';
import { onLogout } from './login.js';
import { successNotify } from './utils.js';

$(document).ready(function(){
  useAuthentication(false);
  setEventForGeneralComponent();

  //Setup event
  // ----------------------------------------------------------------------------------------

});
